package matching;

import java.util.ArrayList;
import java.util.List;

import cc.kave.commons.model.ssts.ISST;
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
import cc.kave.commons.model.ssts.declarations.IDelegateDeclaration;
import cc.kave.commons.model.ssts.declarations.IEventDeclaration;
import cc.kave.commons.model.ssts.declarations.IFieldDeclaration;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.declarations.IPropertyDeclaration;
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
import cc.kave.commons.model.ssts.expressions.loopheader.ILoopHeaderBlockExpression;
import cc.kave.commons.model.ssts.expressions.simple.IConstantValueExpression;
import cc.kave.commons.model.ssts.expressions.simple.INullExpression;
import cc.kave.commons.model.ssts.expressions.simple.IReferenceExpression;
import cc.kave.commons.model.ssts.expressions.simple.IUnknownExpression;
import cc.kave.commons.model.ssts.references.IEventReference;
import cc.kave.commons.model.ssts.references.IFieldReference;
import cc.kave.commons.model.ssts.references.IIndexAccessReference;
import cc.kave.commons.model.ssts.references.IMethodReference;
import cc.kave.commons.model.ssts.references.IPropertyReference;
import cc.kave.commons.model.ssts.references.IUnknownReference;
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
import cc.kave.commons.model.ssts.visitor.ISSTNodeVisitor;

public class ChildrenListVisitor implements ISSTNodeVisitor<Object, List<Object>> {

	@Override
	public List<Object> visit(ISST sst, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.addAll(sst.getDelegates());
		children.addAll(sst.getEvents());
		children.addAll(sst.getFields());
		children.addAll(sst.getProperties());
		children.addAll(sst.getMethods());
		return children;
	}

	@Override
	public List<Object> visit(IDelegateDeclaration stmt, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IEventDeclaration stmt, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IFieldDeclaration stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(stmt.getName().getValueType());
		children.add(stmt.getName().getName());
		return children;
	}

	@Override
	public List<Object> visit(IMethodDeclaration stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(stmt.getName().getName());
		children.add(stmt.getName().getReturnType());
		children.addAll(stmt.getName().getParameters());
		children.addAll(stmt.getBody());
		return null;
	}

	@Override
	public List<Object> visit(IPropertyDeclaration stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.addAll(stmt.getGet());
		children.addAll(stmt.getSet());
		return children;
	}

	@Override
	public List<Object> visit(IVariableDeclaration stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(stmt.getReference());
		return children;
	}

	@Override
	public List<Object> visit(IAssignment stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(stmt.getReference());
		children.add(stmt.getExpression());
		return children;
	}

	@Override
	public List<Object> visit(IBreakStatement stmt, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IContinueStatement stmt, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IExpressionStatement stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(stmt.getExpression());
		return children;
	}

	@Override
	public List<Object> visit(IGotoStatement stmt, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(ILabelledStatement stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(stmt.getStatement());
		return children;
	}

	@Override
	public List<Object> visit(IReturnStatement stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(stmt.getExpression());
		return children;
	}

	@Override
	public List<Object> visit(IThrowStatement stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(stmt.getReference());
		return children;
	}

	@Override
	public List<Object> visit(IEventSubscriptionStatement stmt, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(stmt.getReference());
		children.add(stmt.getExpression());
		return children;
	}

	@Override
	public List<Object> visit(IDoLoop block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(block.getCondition());
		children.addAll(block.getBody());
		return children;
	}

	@Override
	public List<Object> visit(IForEachLoop block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(block.getDeclaration());
		children.add(block.getLoopedReference());
		children.addAll(block.getBody());
		return children;
	}

	@Override
	public List<Object> visit(IForLoop block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(block.getCondition());
		children.addAll(block.getInit());
		children.addAll(block.getStep());
		children.addAll(block.getBody());
		return children;
	}

	@Override
	public List<Object> visit(IIfElseBlock block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(block.getCondition());
		children.addAll(block.getThen());
		children.addAll(block.getThen());
		return children;
	}

	@Override
	public List<Object> visit(ILockBlock block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(block.getReference());
		children.addAll(block.getBody());
		return children;
	}

	@Override
	public List<Object> visit(ISwitchBlock block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(block.getReference());
		// No support for CaseBlocks
		children.addAll(block.getDefaultSection());
		return children;
	}

	@Override
	public List<Object> visit(ITryBlock block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(block.getBody());
		children.add(block.getFinally());
		return children;
	}

	@Override
	public List<Object> visit(IUncheckedBlock block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.addAll(block.getBody());
		return children;
	}

	@Override
	public List<Object> visit(IUnsafeBlock block, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IUsingBlock block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(block.getReference());
		children.addAll(block.getBody());
		return children;
	}

	@Override
	public List<Object> visit(IWhileLoop block, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(block.getCondition());
		children.addAll(block.getBody());
		return children;
	}

	@Override
	public List<Object> visit(IBinaryExpression expr, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(ICastExpression expr, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(expr.getReference());
		return children;
	}

	@Override
	public List<Object> visit(ICompletionExpression expr, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		if (expr.getVariableReference() != null) {
			children.add(expr.getVariableReference());
		}
		return children;
	}

	@Override
	public List<Object> visit(IComposedExpression expr, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.addAll(expr.getReferences());
		return children;
	}

	@Override
	public List<Object> visit(IIfElseExpression expr, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(expr.getCondition());
		children.add(expr.getThenExpression());
		children.add(expr.getElseExpression());
		return children;
	}

	@Override
	public List<Object> visit(IIndexAccessExpression expr, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(expr.getReference());
		children.addAll(expr.getIndices());
		return children;
	}

	@Override
	public List<Object> visit(IInvocationExpression entity, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(entity.getReference());
		children.addAll(entity.getParameters());
		return children;
	}

	@Override
	public List<Object> visit(ILambdaExpression expr, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(expr.getBody());
		return children;
	}

	@Override
	public List<Object> visit(ITypeCheckExpression expr, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IUnaryExpression expr, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(expr.getOperand());
		return children;
	}

	@Override
	public List<Object> visit(ILoopHeaderBlockExpression expr, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.addAll(expr.getBody());
		return children;
	}

	@Override
	public List<Object> visit(IConstantValueExpression expr, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(INullExpression expr, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IReferenceExpression expr, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(expr.getReference());
		return children;
	}

	@Override
	public List<Object> visit(IEventReference ref, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(ref.getReference());
		return children;
	}

	@Override
	public List<Object> visit(IFieldReference ref, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(ref.getReference());
		return children;
	}

	@Override
	public List<Object> visit(IIndexAccessReference ref, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(ref.getExpression());
		return children;
	}

	@Override
	public List<Object> visit(IMethodReference ref, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(ref.getReference());
		return children;
	}

	@Override
	public List<Object> visit(IPropertyReference ref, Object context) {
		ArrayList<Object> children = new ArrayList<>();
		children.add(ref.getReference());
		return null;
	}

	@Override
	public List<Object> visit(IVariableReference ref, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IUnknownReference ref, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IUnknownExpression expr, Object context) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> visit(IUnknownStatement stmt, Object context) {
		return new ArrayList<Object>();
	}
}