package matching;

import java.util.HashMap;
import java.util.List;

import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.impl.visitor.ToStringVisitor;
import cc.kave.commons.model.ssts.visitor.ISSTNode;

public final class SSTUtility {
	
	public static HashMap<ISSTNode, ISSTNode> FIRST_PARENT_CACHE_TREE;
	
	public static List<Object> getChildren(ISSTNode node) {
		ChildrenListVisitor clv = new ChildrenListVisitor();
		List<Object> children = node.accept(clv, null);
		return children;		
	}
	
	public static String getValue(ISST node, boolean isUpdateCall) {
		StringBuilder sb = new StringBuilder();
		ToStringVisitor visitor = new ToStringVisitor();
		visitor.visit(node, sb);
		return sb.toString();
	}
}
