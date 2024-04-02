package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ezen.ezencompany.service.BoardService;
import ezen.ezencompany.vo.BoardVO;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		// board ���̺��� ������ ��� ��������
		List<BoardVO> list = boardService.list();
		model.addAttribute("list", list);
		return "board/list";
	}
		
	@RequestMapping(value = "/list2.do", method = RequestMethod.GET)
	public String list2(Model model) {
		// �����Ͻ� ���� -> DB�� �����ϴ� �Խñ� ��� ��������
		List<String> slist = new ArrayList<String>();
		slist.add("ù��° ������");
		slist.add("�ι�° ������");
		slist.add("����° ������");

		// Model ��ü�� controller ���� ȭ������ �����͸� ���� �ϴ� ��ü �̴�.
		// Model ��ü�� �ݵ�� dispatcherservlet�� �����ϴ� ��ü�θ� �����͸� ������ �� �ִ�.
		// dispatcherservlet�� ���� Model ��ü�� �޴� ����� �Ű������̴�.

		model.addAttribute("list", slist);// request.setAttribute("list",slist);

		return "board/list";
}
}