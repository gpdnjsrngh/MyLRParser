
public class Rule {
	private String LHS;
	private String RHS;
	private int nRHS;
	
	public Rule(String lhs, String rhs) {
		LHS = lhs;
		RHS = rhs;
		nRHS = rhs.length();
	}
	
	public int getRHSLen() {
		return nRHS;
	}
	
	public String getLHS() {
		return LHS;
	}
}
