import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		//LR�ļ�, Rule, �Ľ����̺�  ����
		LRparser parser = new LRparser();
		parser.buildParsingTable();
			
		//�Ľ��� Ƚ��
		int count = 0; 
		while(true) {
			count++;
			//�Է� �ޱ� (Input ����)
			Scanner scanner = new Scanner(System.in);
			System.out.print("�Է�(exit�Է��ϸ� ����)>> ");
			String inputString = scanner.next();
			if(inputString.equals("exit")) {
				System.exit(1);
			}
			Input input = new Input(inputString);
			parser.setInput(input);
		
			try {
				//�Ľ� ����
				parser.performParsing(count);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}