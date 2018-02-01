package spring.esla.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.esla.beans.Tree;

@RequestMapping("/tree")
@Controller	
public abstract class DocumentController  {

	@RequestMapping("/init")
	public abstract ModelAndView initAction() throws Exception;
	
	@RequestMapping("/child/{idx}")
	public abstract void childAction(@PathVariable Integer idx,  HttpServletResponse response) throws Exception;

	@RequestMapping("/manage")
	public abstract ModelAndView manageAction(@RequestParam Integer idx, @RequestParam Integer level) throws Exception;
	
	@RequestMapping("/update")
	public abstract ModelAndView manageAction(@ModelAttribute Tree node) throws Exception;
	
	@RequestMapping("/addnode")
	public abstract ModelAndView addNodeAction(@ModelAttribute Tree node) throws Exception;
	
	@RequestMapping("/delete")	//cascading delete node
	public abstract ModelAndView delete(@RequestParam Integer idx) throws Exception;
	
	@RequestMapping("/order")	//노드 순서 직접 변경
	public abstract ModelAndView order(@ModelAttribute Tree node) throws Exception;
	
	@RequestMapping("/swapOrder")	//노드 순서 바꾸기
	public abstract ModelAndView swapOrder(@ModelAttribute Tree node) throws Exception;
	
	
}


