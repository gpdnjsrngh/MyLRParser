import java.util.ArrayList;
import java.util.List;

public class MyStack {
	private int top;
	private String value;
	private List stack;
	private int size;
	
	public MyStack() {
		top=-1;
		stack = new ArrayList();
	}
	
	public void push(String value) {
		stack.add(++top, value);
		size++;
	}
	
	public String pop() {
		size--;
		return (String) stack.get(top--);		
	}
	
	public String peek() {
		return (String) stack.get(top);
	}

	public String elementAt(int i) {
		return (String) stack.get(i);
	}
	
	public boolean isEmpty() {
		return top==-1;
	}
	
	public int size() {
		return size;
	}
	
	public List getStack() {
		return stack;
	}
}
