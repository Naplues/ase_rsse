package matching;

import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.visitor.ISSTNode;

public final class MatchingUtility {
	
	// TODO[all]: clarify what values we should use here
	private static final int DYNAMIC_THRESHOLD_FOR_SMALL_SUBTREES = 1;
    private static final double INNER_NODE_SIMILARITY_MEASURE = 0.5d;
    private static final double INNER_NODE_SIMILARITY_SMALL_SUBTREES_MEASURE = 0.5d;
    private static final double INNER_NODE_SIMILARITY_WEIGHTING = 0.5d;
    private static final double PARENT_STRING_SIMILARITY_MEASURE = 0.5d;
    private static final double STRING_SIMILARITY_MEASURE = 0.5d;
    
    private ISSTNode firstRoot;
    private ISSTNode secondRoot;
    
    public MatchingUtility() {
    }

	public ISSTNode getFirstRoot() {
		return firstRoot;
	}

	public void setFirstRoot(ISSTNode firstRoot) {
		this.firstRoot = firstRoot;
	}

	public ISSTNode getSecondRoot() {
		return secondRoot;
	}

	public void setSecondRoot(ISSTNode secondRoot) {
		this.secondRoot = secondRoot;
	}
	
	public boolean isMatchCriterion1(ISST x, ISST y) {
		if (x.getClass() != y.getClass()) {
			return false;
		}
		return true;
	}
	
	public double calculateMatchCriterion1(ISST x, ISST y) {
		if (!isMatchCriterion1(x, y)) {
			return 0.0;
		}
		
		String xStr = SSTUtility.getValue(x, false);
		String yStr = SSTUtility.getValue(y, false);
		
		
	}
	
	
	

}
