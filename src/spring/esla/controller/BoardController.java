package spring.esla.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.esla.beans.Board;
import spring.esla.beans.Member;



@Controller	
public abstract class BoardController  {
 
	@RequestMapping("/main")	
	public abstract ModelAndView mainAction() throws Exception;

	@RequestMapping("/exception")	
	public abstract ModelAndView exceptionAction() throws Exception;
	
	@RequestMapping("/login")	
	public abstract ModelAndView loginAction(@ModelAttribute Member user, HttpServletRequest request)throws Exception;
	
	@RequestMapping("/logout")	
	public abstract ModelAndView logoutAction(HttpServletRequest request)throws Exception;
	
	//(value="/insert", method=RequestMethod.POST);
	@RequestMapping( "/insert")
	public abstract ModelAndView insertAction(@ModelAttribute Board article, HttpServletRequest request) throws Exception;
																									
	@RequestMapping("/ajaxList")
	public abstract ModelAndView ajaxListAction(@RequestParam Integer page) throws Exception;
	
	@RequestMapping("/write")
	public abstract ModelAndView writeAction(@RequestParam Integer doc_idx) throws Exception;
	
	@RequestMapping("/count")
	public abstract ModelAndView countAction(@RequestParam Integer idx, HttpServletRequest request) throws Exception;
	
	@RequestMapping("/content")
	public abstract ModelAndView contentAction(@RequestParam Integer idx) throws Exception;
	
	@RequestMapping("/tags")
	public abstract ModelAndView tagsAction(@RequestParam Integer idx) throws Exception;
	
	@RequestMapping("/delete")
	public abstract ModelAndView deleteAction(@RequestParam Integer idx, HttpServletRequest request) throws Exception;
	
	@RequestMapping("/download/{idx}")
	public abstract ModelAndView downloadAction(@PathVariable Integer idx, HttpServletRequest request) throws Exception;

	@RequestMapping("/getPage")
	public abstract ModelAndView pageListAction(@RequestParam Integer idx) throws Exception;
	
	@RequestMapping("/download/document/{num}")
	public abstract ModelAndView documentAction(@PathVariable Integer idx, HttpServletRequest request) throws Exception;

	@RequestMapping("/excel/{idx}")
	public abstract ModelAndView pageExcelAction(@PathVariable Integer idx) throws Exception;
	
	@RequestMapping("chapter/excel/{doc_idx}")
	public abstract ModelAndView chapterExcelAction(@PathVariable Integer doc_idx) throws Exception;
}
