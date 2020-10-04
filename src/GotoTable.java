
public class GotoTable {
	private int[][] gotoTable = new int[12][3];
	
	public void insertState(int r, int c, int state) {
		gotoTable[r][c] = state;
	}
	
	public String getState(int r, int c) {
		return Integer.toString(gotoTable[r][c]);
	}
}
