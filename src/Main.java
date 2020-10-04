import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		//LR파서, Rule, 파싱테이블  생성
		LRparser parser = new LRparser();
		parser.buildParsingTable();
			
		//파싱한 횟수
		int count = 0; 
		while(true) {
			count++;
			//입력 받기 (Input 생성)
			Scanner scanner = new Scanner(System.in);
			System.out.print("입력(exit입력하면 종료)>> ");
			String inputString = scanner.next();
			if(inputString.equals("exit")) {
				System.exit(1);
			}
			Input input = new Input(inputString);
			parser.setInput(input);
		
			try {
				//파싱 수행
				parser.performParsing(count);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}