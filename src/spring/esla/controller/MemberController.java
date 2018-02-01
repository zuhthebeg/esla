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
@RequestMapping("/member")	
public abstract class MemberController  {
 
	@RequestMapping("/join")	
	public abstract ModelAndView joinAction(@ModelAttribute Member user) throws Exception;

	@RequestMapping("/changepw")	
	public abstract ModelAndView changepwAction(@ModelAttribute Member user) throws Exception;

}
