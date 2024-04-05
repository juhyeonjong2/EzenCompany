package test;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List<BoardVO<List<String>>> boardList = new ArrayList<BoardVO<List<String>>>();
		
		for(int i = 0; i < boardList.size(); i ++) {
			BoardVO<List<String>> vo = boardList.get(i);
			List<String> s = vo.getBtitle();
		}
	}

}
