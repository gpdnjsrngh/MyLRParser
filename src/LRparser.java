import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LRparser {
	private enum NonTerminal {E, T, F}
	private enum Terminal {id, plus, mul, lb, rb, S} //a, +, *, (, ), $
	private MyStack stack;
	private ActionTable actionTable;
	private GotoTable gotoTable;
	private Input input;
	private Rule[] rules = new Rule[6];
	BufferedOutputStream bs = null;
	
	public LRparser() {
		//Rule ����, �ʱ�ȭ
		rules[0] = new Rule("E", "E+T");
		rules[1] = new Rule("E", "T");
		rules[2] = new Rule("T", "T*F");
		rules[3] = new Rule("T", "F");
		rules[4] = new Rule("F", "(E)");
		rules[5] = new Rule("F", "a");
	}
	
	//ActionTable�� GotoTable ����,�ʱ�ȭ
	void buildParsingTable() {
		//ActionTable ����
		actionTable = new ActionTable();
		//ActionTable ��Ʈ�� �ʱ�ȭ
		actionTable.insertAction(0, 0, "s5");		
		actionTable.insertAction(0, 3, "s4");
		actionTable.insertAction(1, 1, "s6");
		actionTable.insertAction(1, 5, "acc");
		actionTable.insertAction(2, 1, "r2");
		actionTable.insertAction(2, 2, "s7");
		actionTable.insertAction(2, 4, "r2");
		actionTable.insertAction(2, 5, "r2");
		actionTable.insertAction(3, 1, "r4");
		actionTable.insertAction(3, 2, "r4");
		actionTable.insertAction(3, 4, "r4");
		actionTable.insertAction(3, 5, "r4");
		actionTable.insertAction(4, 0, "s5");
		actionTable.insertAction(4, 3, "s4");
		actionTable.insertAction(5, 1, "r6");
		actionTable.insertAction(5, 2, "r6");
		actionTable.insertAction(5, 4, "r6");
		actionTable.insertAction(5, 5, "r6");
		actionTable.insertAction(6, 0, "s5");
		actionTable.insertAction(6, 3, "s4");
		actionTable.insertAction(7, 0, "s5");
		actionTable.insertAction(7, 3, "s4");
		actionTable.insertAction(8, 1, "s6");
		actionTable.insertAction(8, 4, "s11");
		actionTable.insertAction(9, 1, "r1");
		actionTable.insertAction(9, 2, "s7");
		actionTable.insertAction(9, 4, "r1");
		actionTable.insertAction(9, 5, "r1");
		actionTable.insertAction(10, 1, "r3");
		actionTable.insertAction(10, 2, "r3");
		actionTable.insertAction(10, 4, "r3");
		actionTable.insertAction(10, 5, "r3");
		actionTable.insertAction(11, 1, "r5");
		actionTable.insertAction(11, 2, "r5");
		actionTable.insertAction(11, 4, "r5");
		actionTable.insertAction(11, 5, "r5");
		
		//GotoTable ����, ��Ʈ�� �ʱ�ȭ
		gotoTable = new GotoTable();
		gotoTable.insertState(0, 0, 1);
		gotoTable.insertState(0, 1, 2);
		gotoTable.insertState(0, 2, 3);
		gotoTable.insertState(4, 0, 8);
		gotoTable.insertState(4, 1, 2);
		gotoTable.insertState(4, 2, 3);
		gotoTable.insertState(6, 1, 9);
		gotoTable.insertState(6, 2, 3);
		gotoTable.insertState(7, 2, 10);
	}
	
	void setInput(Input input) {
		this.input = input;
	}
	
	
	void performParsing(int count) throws IOException {
		//Stack �ʱ�ȭ
		stack = new MyStack();
		stack.push("0");
		
		
		try {
			bs = new BufferedOutputStream(new FileOutputStream("output"+count+".txt"));
			String str="            <���>\n-------------------------------\n   | STACK | INPUT | ACTION |\n-------------------------------\n";
			bs.write(str.getBytes());
			bs.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		int num = 0;
		//actionTable�� ��Ʈ���� acc�� ������
		while(true) {
			String str=num+" | ";
			bs.write(str.getBytes());
			bs.flush();
	
			//���� ���
			showStack();
			//input ���
			showInput();
			
			//���� �Է°�
			Character currInput = input.getAInput(input.getCurrIdx());
			
			//stack�� top�� nonTerminal�� �� -> E, T, F
			if(isNonterminal(stack.peek().charAt(0))) {
				
				NonTerminal nt = NonTerminal.valueOf(stack.peek());
				int stateNumber = Integer.parseInt(stack.elementAt(stack.size()-2));
				
				//stateNumber�� nonTerminal�� ���ο� ���� (GOTO���̺�)
				String newState = gotoTable.getState(stateNumber, nt.ordinal());
				
				str="GOTO "+ newState +"\n";
				bs.write(str.getBytes());
				bs.flush();
				
				stack.push(newState);
				
			}
			
			//stack�� top�� stateNumber�� ��
			else {
				Terminal t;
				//�Է°��� enum������ ��ȯ
				switch(currInput) {
				case '*':
					t = Terminal.valueOf("mul");
					break;
				case '+':
					t = Terminal.valueOf("plus");
					break;
				case '(':
					t = Terminal.valueOf("lb");
					break;
				case ')':
					t = Terminal.valueOf("rb");
					break;
				case '$':
					t = Terminal.valueOf("S");
					break;
				default:
					t = Terminal.valueOf("id");
					break;
				}
				
				//ActionTable���� action �˾ƿ���
				int stateNumber = Integer.parseInt(stack.peek());
				String action = actionTable.getAction(stateNumber, t.ordinal());
				
				str=action +"\n";
				bs.write(str.getBytes());
				bs.flush();
				
				//shift
				if(action.charAt(0)=='s') {
					//����  �Է°� push
					stack.push(currInput.toString());
					action=action.substring(1);
					//���ο� ���� push
					stack.push(action);
					//�Է°� ������ ����
					input.incCurrIdx();
				}
				
				//reduce
				else if(action.charAt(0)=='r') {
					String ruleNumber = action.substring(1);
					
					//ruleNumber��° ��Ģ �˾Ƴ���
					Rule rule = rules[Integer.parseInt(ruleNumber)-1];
					
					for(int i=0; i<rule.getRHSLen()*2; i++)
						stack.pop();
										
					//�ش� ��Ģ�� LHS push
					stack.push(rule.getLHS());
				}
				
				else if(action.equals("acc")) {
					break;
				}
			}
			num++;
		}
		
	}
	
	//nonterminal�̸� true�� ��ȯ�ϴ� �Լ�
	boolean isNonterminal(char c) {
		return Character.isUpperCase(c);
	}
	
	//stack ��� �Լ�
	void showStack() {
		String str="";
		
		for(int i=0; i<stack.size(); i++)
			str+=stack.elementAt(i);
		
		str+="  |  ";
		try {
			bs.write(str.getBytes());
			bs.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//input ��� �Լ�
	void showInput() {
		String str="";
		for(int i=input.getCurrIdx(); i<input.getiLen(); i++)
			str+=input.getAInput(i);
		
		str+="$  |  ";

		try {
			bs.write(str.getBytes());
			bs.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
