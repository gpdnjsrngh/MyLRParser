
public class Input {
	private String input;
	private int currIdx;
	private int iLen;
	
	public Input(String input) {
		this.input = input+'$';
		currIdx = 0;
		iLen = input.length();
	}
	
	public char getAInput(int idx) {
		return input.charAt(idx);
	}

	public int getCurrIdx() {
		return currIdx;
	}
	
	public int getiLen() {
		return iLen;
	}
	
	public boolean isEmpty() {
		if(currIdx>=iLen)
			return true;
		else return false;
	}
	
	public void incCurrIdx() {
		if(this.getAInput(currIdx)=='$')
			return;
		currIdx++;
	}
}
