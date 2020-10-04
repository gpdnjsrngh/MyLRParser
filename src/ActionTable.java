
public class ActionTable {
	private String[][] actionTable = new String[12][6];
	
	public void insertAction(int r, int c, String action) {
		actionTable[r][c] = action;
	}
	
	public String getAction(int r, int c) {
		return actionTable[r][c];
	}
}
