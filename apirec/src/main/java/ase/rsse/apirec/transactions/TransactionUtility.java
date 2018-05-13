package ase.rsse.apirec.transactions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.simmetrics.metrics.JaccardSimilarity;

import ase.rsse.apirec.transactions.changecontext.AtomicChange;
import ase.rsse.apirec.transactions.changecontext.ChangeContext;
import ase.rsse.apirec.transactions.codecontext.CodeContext;
import ase.rsse.utilities.IoUtility;
import ase.rsse.utilities.JsonUtility;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.blocks.ICaseBlock;
import cc.kave.commons.model.ssts.blocks.ICatchBlock;
import cc.kave.commons.model.ssts.blocks.IDoLoop;
import cc.kave.commons.model.ssts.blocks.IForEachLoop;
import cc.kave.commons.model.ssts.blocks.IForLoop;
import cc.kave.commons.model.ssts.blocks.IIfElseBlock;
import cc.kave.commons.model.ssts.blocks.ILockBlock;
import cc.kave.commons.model.ssts.blocks.ISwitchBlock;
import cc.kave.commons.model.ssts.blocks.ITryBlock;
import cc.kave.commons.model.ssts.blocks.IUncheckedBlock;
import cc.kave.commons.model.ssts.blocks.IUnsafeBlock;
import cc.kave.commons.model.ssts.blocks.IUsingBlock;
import cc.kave.commons.model.ssts.blocks.IWhileLoop;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.expressions.IAssignableExpression;
import cc.kave.commons.model.ssts.expressions.ISimpleExpression;
import cc.kave.commons.model.ssts.expressions.assignable.IBinaryExpression;
import cc.kave.commons.model.ssts.expressions.assignable.ICastExpression;
import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.model.ssts.expressions.assignable.IComposedExpression;
import cc.kave.commons.model.ssts.expressions.assignable.IIfElseExpression;
import cc.kave.commons.model.ssts.expressions.assignable.IIndexAccessExpression;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import cc.kave.commons.model.ssts.expressions.assignable.ILambdaExpression;
import cc.kave.commons.model.ssts.expressions.assignable.ITypeCheckExpression;
import cc.kave.commons.model.ssts.expressions.assignable.IUnaryExpression;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.impl.statements.Assignment;
import cc.kave.commons.model.ssts.references.IVariableReference;
import cc.kave.commons.model.ssts.statements.IAssignment;
import cc.kave.commons.model.ssts.statements.IBreakStatement;
import cc.kave.commons.model.ssts.statements.IContinueStatement;
import cc.kave.commons.model.ssts.statements.IEventSubscriptionStatement;
import cc.kave.commons.model.ssts.statements.IExpressionStatement;
import cc.kave.commons.model.ssts.statements.IGotoStatement;
import cc.kave.commons.model.ssts.statements.ILabelledStatement;
import cc.kave.commons.model.ssts.statements.IReturnStatement;
import cc.kave.commons.model.ssts.statements.IThrowStatement;
import cc.kave.commons.model.ssts.statements.IUnknownStatement;
import cc.kave.commons.model.ssts.statements.IVariableDeclaration;
import cc.kave.commons.utils.ToStringUtils;

public final class TransactionUtility {

	public static float DELETE_SIMILARITY_THRESHOLD = 0.2f;
	public static float UPDATE_SIMILARITY_THRESHOLD = 0.99f;
	private static SST OLD_SST;
	private static SST NEW_SST;

	public static void createTransaction(CompletionEvent oldEvent, CompletionEvent newEvent, int number) {
		// initialize SSTs
		OLD_SST = (SST) oldEvent.getContext().getSST();
		NEW_SST = (SST) newEvent.getContext().getSST();
		
		// matching -> match old and new methods
		ArrayList<MethodMatch> matchedMethods = matchMethods(OLD_SST.getMethods(), NEW_SST.getMethods());

		// prepare change and code context
		ChangeContext chctx = new ChangeContext();
		CodeContext coctx = new CodeContext();
		
		// filling -> fill matches into change an code Context
		fillTransactionWithMethods(matchedMethods, chctx, coctx);

		// create transaction with filled change and code context
		Transaction transaction = new Transaction()
				.withFileName(NEW_SST.getEnclosingType().getFullName())
				.withChangeContext(chctx)
				.withCodeContext(coctx);
		
		// writing
		if (transaction.getCodeContext().getTokens().size() > 0 && transaction.getChangeContex().getAtomicChanges().size() > 0) {
			try {
				String transactionName = clean(NEW_SST.getEnclosingType().getFullName()) + number;
				IoUtility.writeTransactionToFile(transactionName, JsonUtility.toJson(transaction));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<MethodMatch> matchMethods(Set<IMethodDeclaration> oldMethods,
			Set<IMethodDeclaration> newMethods) {
		ArrayList<MethodMatch> methodMethodMatches = new ArrayList<>();
		// find best MethodMatch for old methods
		for (IMethodDeclaration oldMethod : oldMethods) {
			float maxSimilarity = 0;
			HashMap<Float, IMethodDeclaration> similarityOfNewMethods = new HashMap<>();
			for (IMethodDeclaration newMethod : newMethods) {
				float similarity = calculateStringSimilarity(oldMethod.getBody(), newMethod.getBody());
				similarityOfNewMethods.put(similarity, newMethod);
				if (similarity > maxSimilarity) {
					maxSimilarity = similarity;
				}
			}
			methodMethodMatches.add(new MethodMatch(oldMethod, similarityOfNewMethods.get(maxSimilarity), maxSimilarity));
		}
		return methodMethodMatches;
	}

	public static void fillTransactionWithMethods(ArrayList<MethodMatch> methodMethodMatches, ChangeContext chctx, CodeContext coctx) {
		for (MethodMatch mm : methodMethodMatches) {
			// handle update, delete & nop
			Operation operation = getOperation(mm.getSimilarity());
			if (operation != Operation.NOP) {
				if (mm.getNewMethodDecl() != null) {
					for (IStatement stmt : mm.getNewMethodDecl().getBody()) {
						NodeType nodeType = getNodeType(stmt);
						String label = getLabel(nodeType, stmt);
						AtomicChange ac = new AtomicChange()
								.withNodeType(nodeType)
								.withLabel(label)
								.withOperation(operation);
						chctx.addAtomicChange(ac);
						coctx.addTokens(getTokens(nodeType, stmt));
					}
				}
			}
		}
		// handle add
		List<IMethodDeclaration> matchedNewNodes = methodMethodMatches.stream()
				.map(MethodMatch::getNewMethodDecl)
				.collect(Collectors.toList());
		List<IMethodDeclaration> newMethodDecls = NEW_SST.getMethods().stream()
				.filter(m -> !matchedNewNodes.contains(m))
				.collect(Collectors.toList());
		for (IMethodDeclaration md: newMethodDecls) {
			for (IStatement stmt: md.getBody()) {
				NodeType nodeType = getNodeType(stmt);
				String label = getLabel(nodeType, stmt);
				AtomicChange ac = new AtomicChange()
						.withNodeType(nodeType)
						.withLabel(label)
						.withOperation(Operation.ADD);
				chctx.addAtomicChange(ac);
					coctx.addTokens(getTokens(nodeType, stmt));
			}
		}
	}
	
	public static HashSet<String> getTokens(NodeType nodeType, IStatement statement) {
		HashSet<String> tokens = new HashSet<>();
		switch (nodeType) {
		case Assignment:
			IAssignableExpression assignblExpr = ((Assignment) statement).getExpression();
			tokens.addAll(getTokens(assignblExpr));
			break;
		case BreakStatement:
			tokens.addAll(convertToStringSet(statement.toString()));
			break;
		case ContinueStatement:
			tokens.addAll(convertToStringSet(statement.toString()));
			break;
		case DoLoop:
			IDoLoop doExpr = (IDoLoop) statement;
			tokens.addAll(convertToStringSet(doExpr.getCondition().toString()));
			for (IStatement stmt: doExpr.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			break;
		case EventSubscriptionStatement:
			IEventSubscriptionStatement evtStmt = (IEventSubscriptionStatement) statement;
			tokens.addAll(getTokens(evtStmt.getExpression()));
			tokens.addAll(convertToStringSet(evtStmt.getOperation().name()));
			tokens.addAll(convertToStringSet(evtStmt.getReference().toString()));
			break;
		case ExpressionStatement:
			IExpressionStatement exprStmt = (IExpressionStatement) statement;
			IAssignableExpression asgblExpr = exprStmt.getExpression();
			tokens.addAll(getTokens(asgblExpr));
			break;
		case ForEachLoop:
			IForEachLoop forEachExpr = (IForEachLoop) statement;
			tokens.addAll(convertToStringSet(forEachExpr.getDeclaration().getReference().getIdentifier()));
			tokens.addAll(convertToStringSet(forEachExpr.getDeclaration().getType().getName()));
			for (IStatement stmt: forEachExpr.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			break;
		case ForLoop:
			IForLoop forExpr = (IForLoop) statement;
			for (IStatement stmt: forExpr.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			for (IStatement stmt: forExpr.getStep()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			for (IStatement stmt: forExpr.getInit()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			tokens.addAll(convertToStringSet(forExpr.getCondition().toString()));
			break;
		case GotoStatement:
			IGotoStatement gtExpr = (IGotoStatement) statement;
			tokens.addAll(convertToStringSet(gtExpr.getLabel()));
			break;
		case IfElseBlock:
			IIfElseBlock ifElseStmt = (IIfElseBlock) statement;
			tokens.addAll(convertToStringSet(ifElseStmt.getCondition().toString()));
			for (IStatement stmt: ifElseStmt.getElse()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			for (IStatement stmt: ifElseStmt.getThen()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			break;
		case LabelledStatement:
			ILabelledStatement lbldStmt = (ILabelledStatement) statement;
			tokens.addAll(convertToStringSet(lbldStmt.getLabel()));
			tokens.addAll(getTokens(getNodeType(lbldStmt.getStatement()), lbldStmt.getStatement()));
			break;
		case LockBlock:
			ILockBlock lockStmt = (ILockBlock) statement;
			tokens.addAll(convertToStringSet(lockStmt.getReference().getIdentifier()));
			for (IStatement stmt: lockStmt.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			break;
		case ReturnStatement:
			IReturnStatement returnStmt = (IReturnStatement) statement;
			tokens.addAll(getTokens(returnStmt.getExpression()));
			break;
		case SwitchBlock:
			ISwitchBlock switchStmt = (ISwitchBlock) statement;
			tokens.addAll(convertToStringSet(switchStmt.getReference().getIdentifier()));
			for (ICaseBlock block: switchStmt.getSections()) {
				tokens.addAll(convertToStringSet(block.getLabel().toString()));
				for (IStatement stmt: block.getBody()) {
					tokens.addAll(getTokens(getNodeType(stmt), stmt));
				}
			}
			break;
		case ThrowStatement:
			IThrowStatement throwStmt = (IThrowStatement) statement;
			tokens.addAll(convertToStringSet(throwStmt.getReference().getIdentifier()));
			break;
		case TryBlock:
			ITryBlock tryBlock = (ITryBlock) statement;
			for (IStatement stmt: tryBlock.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			for (IStatement stmt: tryBlock.getFinally()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			for (ICatchBlock block: tryBlock.getCatchBlocks()) {
				tokens.addAll(convertToStringSet(block.getParameter().getName()));
				for (IStatement stmt: block.getBody()) {
					tokens.addAll(getTokens(getNodeType(stmt), stmt));
				}
			}
			break;
		case UncheckedBlock:
			IUncheckedBlock uncheckedBlock = (IUncheckedBlock) statement;
			for (IStatement stmt: uncheckedBlock.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			break;
		case UnsafeBlock:
			IUnsafeBlock unsafeBlock = (IUnsafeBlock) statement;
			tokens.addAll(convertToStringSet(unsafeBlock.toString()));
			break;
		case UsingBlock:
			IUsingBlock usingBlock = (IUsingBlock) statement;
			for (IStatement stmt: usingBlock.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			for (IStatement stmt: usingBlock.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			break;
		case VariableDeclaration:
			IVariableDeclaration varDeclStmt = (IVariableDeclaration) statement;
			tokens.addAll(convertToStringSet(varDeclStmt.getReference().getIdentifier()));
			tokens.addAll(convertToStringSet(varDeclStmt.getType().getName()));
			break;
		case WhileLoop:
			IWhileLoop whileStmt = (IWhileLoop) statement;
			tokens.addAll(convertToStringSet(whileStmt.getCondition().toString()));
			for (IStatement stmt: whileStmt.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			break;
		case UnknownStatement:
			break;
		default:
			break;
		}
		return tokens;
	}
	
	public static HashSet<String> getTokens(IAssignableExpression assignableExpr) {
		HashSet<String> tokens = new HashSet<>();
		if (assignableExpr instanceof IBinaryExpression) {
			IBinaryExpression binExpr = (IBinaryExpression) assignableExpr;
			tokens.addAll(convertToStringSet(binExpr.getLeftOperand().toString()));
			tokens.addAll(convertToStringSet(binExpr.getRightOperand().toString()));
		} else if (assignableExpr instanceof ICastExpression) {
			ICastExpression castExpr = (ICastExpression) assignableExpr;
			tokens.addAll(convertToStringSet(castExpr.getReference().getIdentifier()));
			tokens.addAll(convertToStringSet(castExpr.getTargetType().getName()));
			tokens.addAll(convertToStringSet(castExpr.getReference().getIdentifier()));
			
		} else if (assignableExpr instanceof ICompletionExpression) {
			ICompletionExpression compExpr = (ICompletionExpression) assignableExpr;
			tokens.addAll(convertToStringSet(compExpr.getToken()));
			if (compExpr.getTypeReference() != null) {
				tokens.addAll(convertToStringSet(compExpr.getTypeReference().getName()));
			}
			if (compExpr.getVariableReference() != null) {
				tokens.addAll(convertToStringSet(compExpr.getVariableReference().getIdentifier()));
			}
			
		} else if (assignableExpr instanceof IComposedExpression) {
			IComposedExpression compExpr = (IComposedExpression) assignableExpr;
			for (IVariableReference var: compExpr.getReferences()) {
				tokens.addAll(convertToStringSet(var.getIdentifier()));
			}
			
		} else if (assignableExpr instanceof IIfElseExpression) {
			IIfElseExpression ifElseExpr = (IIfElseExpression) assignableExpr;
			tokens.addAll(convertToStringSet(ifElseExpr.getCondition().toString()));
			tokens.addAll(convertToStringSet(ifElseExpr.getElseExpression().toString()));
			tokens.addAll(convertToStringSet(ifElseExpr.getThenExpression().toString()));
			
		} else if (assignableExpr instanceof IIndexAccessExpression) {
			IIndexAccessExpression indAccExpr = (IIndexAccessExpression) assignableExpr;
			for (ISimpleExpression splExpr: indAccExpr.getIndices()) {
				tokens.addAll(convertToStringSet(splExpr.toString()));
			}
			tokens.addAll(convertToStringSet(indAccExpr.getReference().getIdentifier()));
			
		} else if (assignableExpr instanceof IInvocationExpression) {
			IInvocationExpression invExpr = (IInvocationExpression) assignableExpr;
			tokens.addAll(convertToStringSet(invExpr.getMethodName().getName()));
			for (ISimpleExpression splExpr: invExpr.getParameters()) {
				tokens.addAll(convertToStringSet(splExpr.toString()));
			}
			tokens.addAll(convertToStringSet(invExpr.getReference().getIdentifier()));
		} else if (assignableExpr instanceof ILambdaExpression) {
			ILambdaExpression lmbdExpr = (ILambdaExpression) assignableExpr;
			tokens.addAll(convertToStringSet(lmbdExpr.getName().getIdentifier()));
			for (IStatement stmt: lmbdExpr.getBody()) {
				tokens.addAll(getTokens(getNodeType(stmt), stmt));
			}
			
		} else if (assignableExpr instanceof ISimpleExpression) {
			ISimpleExpression splExpr = (ISimpleExpression) assignableExpr;
			tokens.addAll(convertToStringSet(splExpr.toString()));
			
		} else if (assignableExpr instanceof ITypeCheckExpression) {
			ITypeCheckExpression typExpr = (ITypeCheckExpression) assignableExpr;
			tokens.addAll(convertToStringSet(typExpr.getReference().getIdentifier()));
			tokens.addAll(convertToStringSet(typExpr.getType().getName()));
			
		} else if (assignableExpr instanceof IUnaryExpression) {
			IUnaryExpression unExpr = (IUnaryExpression) assignableExpr;
			if (unExpr.getOperand() != null) {
				tokens.addAll(convertToStringSet(unExpr.getOperand().toString()));
			}
		}
		return tokens;
	}
	
	public static String getLabel(NodeType nodeType, IStatement statement) {
		if (nodeType == NodeType.MethodInvocation) {
			IAssignableExpression expr = ((IExpressionStatement) statement).getExpression();
			if (expr instanceof IInvocationExpression) {
				return ((IInvocationExpression) expr).getMethodName().getName();
			}
		}
		return null;
	}
	
	public static NodeType getNodeType(IStatement stmt) {
		if (stmt instanceof IAssignment) {
			return NodeType.Assignment;
		}
		else if (stmt instanceof IBreakStatement) {
			return NodeType.BreakStatement;
		}
		else if (stmt instanceof IContinueStatement) {
			return NodeType.ContinueStatement;
		}
		else if (stmt instanceof IDoLoop) {
			return NodeType.DoLoop;
		}
		else if (stmt instanceof IEventSubscriptionStatement) {
			return NodeType.EventSubscriptionStatement;
		}
		else if (stmt instanceof IExpressionStatement) {
			if (((IExpressionStatement) stmt).getExpression() instanceof IInvocationExpression) {
				return NodeType.MethodInvocation;
			}
			return NodeType.ExpressionStatement;
		}
		else if (stmt instanceof IForEachLoop) {
			return NodeType.ForEachLoop;
		}
		else if (stmt instanceof IForLoop) {
			return NodeType.ForLoop;
		}
		else if (stmt instanceof IGotoStatement) {
			return NodeType.GotoStatement;
		}
		else if (stmt instanceof IIfElseBlock) {
			return NodeType.IfElseBlock;
		}
		else if (stmt instanceof ILabelledStatement) {
			return NodeType.LabelledStatement;
		}
		else if (stmt instanceof ILockBlock) {
			return NodeType.LockBlock;
		}
		else if (stmt instanceof IReturnStatement) {
			return NodeType.ReturnStatement;
		}
		else if (stmt instanceof ISwitchBlock) {
			return NodeType.SwitchBlock;
		}
		else if (stmt instanceof IThrowStatement) {
			return NodeType.ThrowStatement;
		}
		else if (stmt instanceof ITryBlock) {
			return NodeType.TryBlock;
		}
		else if (stmt instanceof IUncheckedBlock) {
			return NodeType.UncheckedBlock;
		}
		else if (stmt instanceof IUnknownStatement) {
			return NodeType.UnknownStatement;
		}
		else if (stmt instanceof IUnsafeBlock) {
			return NodeType.UnsafeBlock;
		}
		else if (stmt instanceof IUsingBlock) {
			return NodeType.UsingBlock;
		}
		else if (stmt instanceof IVariableDeclaration) {
			return NodeType.VariableDeclaration;
		}
		else if (stmt instanceof IWhileLoop) {
			return NodeType.WhileLoop;
		} else {
			return NodeType.UnknownStatement;
		}
	}

	public static Operation getOperation(float similarity) {
		if (similarity < DELETE_SIMILARITY_THRESHOLD) {
			return Operation.DELETE;
		} else if (similarity < UPDATE_SIMILARITY_THRESHOLD) {
			return Operation.CHANGE;
		} else {
			return Operation.NOP;
		}
	}

	public static float calculateStringSimilarity(Object oldObject, Object newObject) {
		JaccardSimilarity<String> jac = new JaccardSimilarity<>();
		return jac.compare(convertToStringSet(oldObject), convertToStringSet(newObject));
	}
	
	public static String clean(String string) {
		String cleaned = "";
		for (String element: convertToStringSet(string)) {
			if (!element.isEmpty())
			cleaned += element + ".";
		}
		return cleaned.substring(0, cleaned.length()-1);
	}

	public static HashSet<String> convertToStringSet(Object obj) {
		HashSet<String> set = new HashSet<>();
		String str = ToStringUtils.toString(obj);
		String[] split = str.split("\\W+");
		for (String s : split) {
			if (!s.isEmpty() && !s.matches("\\d+")) {
				set.add(s);
			}
		}
		return set;
	}
	
	public static void init(SST oldSst, SST newSst) {
		OLD_SST = oldSst;
		NEW_SST = newSst;
	}
}
