package spring.esla.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.esla.beans.Board;

@RequestMapping("/tags")
@Controller	
public abstract class TagsController  {

	@RequestMapping("/delete")
	public abstract ModelAndView deleteAction(@RequestParam Integer idx) throws Exception;
	
	@RequestMapping("/save")
	public abstract ModelAndView saveAction(@ModelAttribute Board tag) throws Exception;
	
	@RequestMapping("/insert")
	public abstract ModelAndView insertAction(@ModelAttribute Board tag) throws Exception;
	
	@RequestMapping("/extract")
	public abstract ModelAndView extractAction(@RequestParam String text,  HttpServletRequest request) throws Exception;
	
	@RequestMapping("/extract/{text}")
	public abstract ModelAndView extractService(@PathVariable String text,  HttpServletRequest request) throws Exception;
			
	@RequestMapping("/search")
	public abstract ModelAndView searchAction(@RequestParam String text, @RequestParam boolean bigram) throws Exception;
	
	@RequestMapping("/search/{text}")
	public abstract ModelAndView searchService(@PathVariable String text) throws Exception;
			
	@RequestMapping("/bigram")
	public abstract ModelAndView bigramAction(@RequestParam String text) throws Exception;
	
	@RequestMapping("/bigram/{text}")
	public abstract ModelAndView bigramService(@PathVariable String text) throws Exception;
	
	@RequestMapping("/kordic")
	public abstract ModelAndView korDicResultAction(@RequestParam String baseWord, @RequestParam Integer idx) throws Exception;
	
	@RequestMapping("/updateWordno")
	public abstract ModelAndView korDicResultAction(@ModelAttribute Board tag) throws Exception;
	
}


