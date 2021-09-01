package kr.co.jframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/board")
public class MainController {

	
	@RequestMapping("/list")
	public ModelAndView list(){
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/list");
		return mv;
	}
	
	@RequestMapping("/hello")
	public void hello(Model model) {
	}
	
	@RequestMapping("/stView")
	public String stView(Model model) {
		model.addAttribute("name", "Stringboot from Web");
		return "board/stView";
	}
	
}
