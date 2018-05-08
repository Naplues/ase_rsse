package ase.rsse.apirec.transactions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.simmetrics.metrics.JaccardSimilarity;

import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.visitor.ISSTNode;
import cc.kave.commons.utils.ToStringUtils;
import cc.kave.commons.utils.ssts.SSTNodeHierarchy;

public final class MatchCreator {
	
	public static float STRING_SIMILARITY_THRESHOLD = 0.3f;
	private static SST OLD_SST;
	private static SST NEW_SST;
	private static SSTNodeHierarchy OLD_HIERARCHY;
	private static SSTNodeHierarchy NEW_HIERARCHY;
	private static ArrayList<ISSTNode> UNMATCHED_NEW_PARENT_NODES;
	private static ArrayList<ISSTNode> UNMATCHED_OLD_PARENT_NODES;
	private static ArrayList<ISSTNode> UNMATCHED_OLD_LEAF_NODES;
	private static ArrayList<ISSTNode> UNMATCHED_NEW_LEAF_NODES;
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
		UNMATCHED_OLD_LEAF_NODES = getLeafNodes(OLD_SST, OLD_HIERARCHY);
		UNMATCHED_NEW_LEAF_NODES = getLeafNodes(NEW_SST, NEW_HIERARCHY);
		
		matcHash();
		matchLeaves();
		addBestTemporaryMatchingToFinalMatching();
		matchBest();
		
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
	
	public static ArrayList<ISSTNode> getLeafNodes(SST sst, SSTNodeHierarchy hierarchy) {
		ArrayList<ISSTNode> leafNodes = new ArrayList<>();
		for (ISSTNode node: sst.getChildren()) {
			for (ISSTNode childNode: hierarchy.getChildren(hierarchy.getParent(node))) {
				if (childNode.getChildren() == null) {
					leafNodes.add(childNode);
				}
			}
		}
		return leafNodes;
	}
	
	public static void matcHash() {
		// match all equal nodes
		for (ISSTNode oldParentNode: UNMATCHED_OLD_PARENT_NODES) {
			for (ISSTNode newParentNode: UNMATCHED_NEW_PARENT_NODES) {
				if (oldParentNode.equals(newParentNode)) {
					TEMPORARY_MATCHING.add(new Match(oldParentNode, newParentNode, 1));
					UNMATCHED_OLD_PARENT_NODES.remove(oldParentNode);
					UNMATCHED_NEW_PARENT_NODES.remove(newParentNode);
				}
			}
		}
		for (ISSTNode oldParentNode: UNMATCHED_OLD_LEAF_NODES) {
			for (ISSTNode newParentNode: UNMATCHED_NEW_LEAF_NODES) {
				if (oldParentNode.equals(newParentNode)) {
					TEMPORARY_MATCHING.add(new Match(oldParentNode, newParentNode, 1));
					UNMATCHED_OLD_LEAF_NODES.remove(oldParentNode);
					UNMATCHED_NEW_LEAF_NODES.remove(newParentNode);
				}
			}
		}

		// match method declarations
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
						ISSTNode oldParentNode = parentNodesOfOldSubTree.remove(0);
						List<ISSTNode> match = parentNodesOfNewSubTree.stream()
								.filter(node -> node.equals(oldParentNode))
								.collect(Collectors.toList());
						if (!match.isEmpty()) {
							ArrayList<ISSTNode> matchesOfOldParents = getSubTreeOfNode(oldParentNode);
							ArrayList<ISSTNode> matchesOfNewParents = getSubTreeOfNode(match.get(0));
							matchChildren(matchesOfOldParents, matchesOfNewParents);
							parentNodesOfOldSubTree.removeAll(matchesOfOldParents);
							parentNodesOfNewSubTree.removeAll(matchesOfNewParents);
							UNMATCHED_OLD_PARENT_NODES.removeAll(matchesOfOldParents);
							UNMATCHED_NEW_PARENT_NODES.removeAll(matchesOfNewParents);
						}
					}
				}
			}
		}
	}
	
	public static void matchLeaves() {
		for (ISSTNode oldLeaf: UNMATCHED_OLD_LEAF_NODES) {
			for (ISSTNode newLeaf: UNMATCHED_NEW_LEAF_NODES) {
				if (oldLeaf.getClass() == newLeaf.getClass()) {
					float stringSimilarity = calculateStringSimilarity(oldLeaf, newLeaf);
					if (calculateStringSimilarity(oldLeaf, newLeaf) > STRING_SIMILARITY_THRESHOLD) {
						TEMPORARY_MATCHING.add(new Match(oldLeaf, newLeaf, stringSimilarity));
						UNMATCHED_NEW_LEAF_NODES.remove(oldLeaf);
						UNMATCHED_NEW_LEAF_NODES.remove(newLeaf);
					}
				}
			}
		}
	}
	
	public static void matchBest() {
		while (!UNMATCHED_OLD_LEAF_NODES.isEmpty() && !UNMATCHED_NEW_LEAF_NODES.isEmpty()) {
			ISSTNode oldUnmatched = UNMATCHED_OLD_LEAF_NODES.remove(0);
			HashMap<Float, ArrayList<ISSTNode>> candidates = new HashMap<>();
			
			// calculate score for all potential matches
			float max = 0;
			for (ISSTNode newUnmatched: UNMATCHED_NEW_LEAF_NODES) {
				if (isMatchParents(oldUnmatched, newUnmatched)) {
					float similarity = calculateStringSimilarity(oldUnmatched, newUnmatched);
					max = similarity > max ? similarity : max;
					if (candidates.get(similarity) != null) {
						candidates.get(similarity).add(oldUnmatched);
					} else {
						ArrayList<ISSTNode> list = new ArrayList<>();
						list.add(newUnmatched);
						candidates.put(similarity, list);
					}
				}
			}
			// get best potential match and add it to final matching if similarity > threshold
			if (max > 0) {
				FINAL_MATCHING.add(new Match(oldUnmatched, candidates.get(max).get(0), max));
				UNMATCHED_OLD_LEAF_NODES.remove(oldUnmatched);
				UNMATCHED_NEW_LEAF_NODES.remove(candidates.get(max).get(0));
			} else {
				UNMATCHED_OLD_LEAF_NODES.remove(oldUnmatched);
			}
		}
	}
	
	public static void matchChildren(Iterable<ISSTNode> oldMethodChildren, Iterable<ISSTNode> newMethodChildren) {
		for (ISSTNode oldChild: oldMethodChildren) {
			for (ISSTNode newChild: newMethodChildren) {
				if (oldChild.equals(newChild)) {
					FINAL_MATCHING.add(new Match(oldChild, newChild, 1));
					UNMATCHED_OLD_LEAF_NODES.remove(oldChild);
					UNMATCHED_NEW_LEAF_NODES.remove(newChild);
					UNMATCHED_OLD_PARENT_NODES.remove(oldChild);
					UNMATCHED_NEW_PARENT_NODES.remove(newChild);
				}
			}
		}
	}
	
	public static boolean isMatchParents(ISSTNode oldNode, ISSTNode newNode) {
		if (oldNode instanceof ISST && newNode instanceof ISST) {
			return true;
		}
		else if (oldNode.getClass() != newNode.getClass()) {
			return false;
		} else if (oldNode instanceof IMethodDeclaration && newNode instanceof IMethodDeclaration) {
			return isMethodDeclarationSpecialCase((IMethodDeclaration) oldNode, (IMethodDeclaration) newNode);
		}
		else if (calculateInnerNodeSimilarity(oldNode, newNode) > STRING_SIMILARITY_THRESHOLD) {
			return true;
		}
		return false;
	}

	private static float calculateInnerNodeSimilarity(ISSTNode oldNode, ISSTNode newNode) {
		ArrayList<ISSTNode> subTreeOfOldNode = getSubTreeOfNode(oldNode);
		ArrayList<ISSTNode> subTreeOfNewNode = getSubTreeOfNode(newNode);
		
		int maxNumberOfLeaves = Math.max(subTreeOfOldNode.size(), subTreeOfNewNode.size());
		
		if (!Collections.disjoint(subTreeOfOldNode, subTreeOfNewNode)) {
			List<ISSTNode> union = subTreeOfNewNode.stream()
			.filter(node -> subTreeOfOldNode.contains(node))
			.collect(Collectors.toList());
			return ((float) union.size()) / maxNumberOfLeaves; 
		}
		return 0f;
	}
	
	private static boolean isMethodDeclarationSpecialCase(IMethodDeclaration oldMethodDecl, IMethodDeclaration newMethodDecl) {
		if (oldMethodDecl.getName().getFullName().equals(newMethodDecl.getName().getFullName())) {
			return true;
		} else if (oldMethodDecl.getBody().equals(newMethodDecl.getBody())) {
			return true;
		}
		return false;
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
	
	private static ArrayList<ISSTNode> getLeafeNodesOfTree(ArrayList<ISSTNode> tree) {
		ArrayList<ISSTNode> leafNodes = new ArrayList<>();
		for (ISSTNode node: tree) {
			if (node.getChildren() == null) {
				leafNodes.add(node);
			}
		}
		return leafNodes;
	}
	
	public static void addBestTemporaryMatchingToFinalMatching() {
		Collections.sort(TEMPORARY_MATCHING, new Comparator<Match>() {
			@Override
			public int compare(Match m1, Match m2) {
				return m1.getSimilarity() > m2.getSimilarity() ? -1 : 1;
			}
		});
		while (!TEMPORARY_MATCHING.isEmpty()) {
			Match bestMatch = TEMPORARY_MATCHING.remove(0);
			FINAL_MATCHING.add(bestMatch);
			UNMATCHED_OLD_LEAF_NODES.remove(bestMatch.getOldNode());
			UNMATCHED_NEW_LEAF_NODES.remove(bestMatch.getNewNode());
		}
	}
	
	private static float calculateStringSimilarity(ISSTNode oldNode, ISSTNode newNode) {
		JaccardSimilarity<String> jac = new JaccardSimilarity<>();
		return jac.compare(convertToStringSet(oldNode), convertToStringSet(newNode));
	}
	
	private static HashSet<String> convertToStringSet(ISSTNode node) {
		HashSet<String> set = new HashSet<>();
		String str = ToStringUtils.toString(node);
		String[] split = str.split("\\s+");
		for (String s: split) {
			set.add(s);
		}
		return set;
	}
}
