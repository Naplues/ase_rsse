package ase.rsse.apirec.transactions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.visitor.ISSTNode;
import cc.kave.commons.utils.ssts.SSTNodeHierarchy;

public class TransactionCreator {
	
	private static SST OLD_SST;
	private static SST NEW_SST;
	private static SSTNodeHierarchy OLD_HIERARCHY;
	private static SSTNodeHierarchy NEW_HIERARCHY;
	private static ArrayList<ISSTNode> UNMATCHED_NEW_PARENT_NODES;
	private static ArrayList<ISSTNode> UNMATCHED_OLD_PARENT_NODES;
	private static ArrayList<ISSTNode> UNMATCHED_OLD_CHILD_NODES;
	private static ArrayList<ISSTNode> UNMATCHED_NEW_CHILD_NODES;
	private static ArrayList<Match> TEMPORARY_MATCHING;
	private static ArrayList<Match> FINAL_MATCHING;
	
	
	
	public static void init(SST oldSST, SST newSST) {
		OLD_SST = oldSST;
		NEW_SST = newSST;
	}

	public static SSTNodeHierarchy parseTree(SST sst) {
		return new SSTNodeHierarchy(sst);
	}
	
	public void createTransaction(SST oldSST, SST newSST) {
		// TODO Auto-generated method stub

	}

	public static ArrayList<Match> createMatching() {
		TEMPORARY_MATCHING = new ArrayList<>();
		FINAL_MATCHING = new ArrayList<>();
		
		// no matching possible
		if (OLD_SST == null || NEW_SST == null) {
			return FINAL_MATCHING;
		}
		
		// old and new are equal
		if (OLD_SST.equals(NEW_SST)) {
			for (ISSTNode newNode: NEW_SST.getChildren()) {
				for (ISSTNode oldNode: OLD_SST.getChildren()) {
					if (oldNode.equals(newNode)) {
						FINAL_MATCHING.add(new Match(oldNode, newNode, 1));
					}
				}
			}
			return FINAL_MATCHING;
		}
		
		// old and new are different
		OLD_HIERARCHY = parseTree(OLD_SST);
		NEW_HIERARCHY = parseTree(NEW_SST);
		
		UNMATCHED_OLD_PARENT_NODES = getParentNodes(OLD_SST, OLD_HIERARCHY);
		UNMATCHED_NEW_PARENT_NODES = getParentNodes(NEW_SST, NEW_HIERARCHY);
		UNMATCHED_OLD_CHILD_NODES = getChildNodes(OLD_SST, OLD_HIERARCHY);
		UNMATCHED_NEW_CHILD_NODES = getChildNodes(NEW_SST, NEW_HIERARCHY);
		
		matchParents();
		// TODO
//		matchLeavesWithParentNames();
//		matchLeaves();
//		Match...
		
		
		return FINAL_MATCHING;
	}
	
	public static ArrayList<ISSTNode> getParentNodes(SST sst, SSTNodeHierarchy hierarchy) {
		ArrayList<ISSTNode> parentNodes = new ArrayList<>();
		for (ISSTNode node: sst.getChildren()) {
			ISSTNode parent = hierarchy.getParent(node);
			parentNodes.add(parent);
		}
		return parentNodes;
	}
	
	public static ArrayList<ISSTNode> getChildNodes(SST sst, SSTNodeHierarchy hierarchy) {
		ArrayList<ISSTNode> childNodes = new ArrayList<>();
		for (ISSTNode node: sst.getChildren()) {
			for (ISSTNode childNode: hierarchy.getChildren(hierarchy.getParent(node))) {
				childNodes.add(childNode);
			}
		}
		return childNodes;
	}
	
	public static void matchParents() {
		
		for(IMethodDeclaration oldMethod: OLD_SST.getMethods()) {
			for (IMethodDeclaration newMethod: NEW_SST.getMethods()) {
				
				if (oldMethod.equals(newMethod)) {
					matchChildren(oldMethod.getChildren(), newMethod.getChildren());
				}
				else if (oldMethod.getName().equals(newMethod.getName())) {
					ArrayList<ISSTNode> subTreeOfOldMethod = getSubTreeOfNode(oldMethod);
					ArrayList<ISSTNode> subTreeOfNewMethod = getSubTreeOfNode(newMethod);
					ArrayList<ISSTNode> parentNodesOfOldSubTree = getParentNodesOfTree(subTreeOfOldMethod);
					ArrayList<ISSTNode> parentNodesOfNewSubTree = getParentNodesOfTree(subTreeOfNewMethod);
					
					while (!parentNodesOfOldSubTree.isEmpty() && !parentNodesOfNewSubTree.isEmpty()) {
						ISSTNode oldParentNode = parentNodesOfOldSubTree.get(0);
						List<ISSTNode> match = parentNodesOfNewSubTree.stream()
								.filter(node -> node.equals(oldParentNode))
								.collect(Collectors.toList());
						if (!match.isEmpty()) {
							ArrayList<ISSTNode> matchesOfOldParents = getSubTreeOfNode(oldParentNode);
							ArrayList<ISSTNode> matchesOfNewParents = getSubTreeOfNode(match.get(0));
							matchChildren(matchesOfOldParents, matchesOfNewParents);
							parentNodesOfOldSubTree.removeAll(matchesOfOldParents);
							parentNodesOfNewSubTree.removeAll(matchesOfNewParents);
						}
					}
				}
			}
		}
	}
	
	public static void matchChildren(Iterable<ISSTNode> oldMethodChildren, Iterable<ISSTNode> newMethodChildren) {
		for (ISSTNode oldChild: oldMethodChildren) {
			for (ISSTNode newChild: newMethodChildren) {
				if (oldChild.equals(newChild)) {
					TEMPORARY_MATCHING.add(new Match(oldChild, newChild, 1));
					UNMATCHED_NEW_CHILD_NODES.remove(newChild);
					UNMATCHED_OLD_CHILD_NODES.remove(oldChild);
				}
			}
		}
	}
	
	private static ArrayList<ISSTNode> getSubTreeOfNode(ISSTNode node) {
		ArrayList<ISSTNode> childNodes = new ArrayList<>();
		ArrayDeque<ISSTNode> queue = new ArrayDeque<>();
		
		queue.add(node);
		
		while(!queue.isEmpty()) {
			ISSTNode currentNode = queue.pollFirst();
			childNodes.add(currentNode);
			
			for (ISSTNode childNode: currentNode.getChildren()) {
				childNodes.add(childNode);
				queue.add(childNode);
			}
		}
		return childNodes;
	}
	
	private static ArrayList<ISSTNode> getParentNodesOfTree(ArrayList<ISSTNode> tree) {
		ArrayList<ISSTNode> parentNodes = new ArrayList<>();
		for (ISSTNode node: tree) {
			if (node.getChildren() != null) {
				parentNodes.add(node);
			}
		}
		return parentNodes;
	}
}
