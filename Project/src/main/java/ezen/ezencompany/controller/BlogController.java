package ezen.ezencompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value="/blog")
@Controller
public class BlogController {

	@RequestMapping(value="/home")
	public String home() {
		
		
		return "blog/home";
	}
}
