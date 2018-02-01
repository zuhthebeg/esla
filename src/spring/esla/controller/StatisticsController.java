package spring.esla.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.esla.beans.Part;
import spring.esla.beans.Statistics;
import spring.esla.beans.Tree;

@RequestMapping("/statistics")
@Controller	
public abstract class StatisticsController  {

	@RequestMapping("/bar")
	public abstract ModelAndView barChartAction(@RequestParam Integer doc_idx, @RequestParam String isAllWord, @ModelAttribute Part part) throws Exception;

	@RequestMapping("/search")
	public abstract ModelAndView searchAction(@RequestParam Integer doc_idx) throws Exception;

	@RequestMapping("/save")
	public abstract ModelAndView saveAction(@ModelAttribute Statistics stat) throws Exception;

	@RequestMapping("/searchword")
	public abstract ModelAndView wordSearchAction(@ModelAttribute Statistics stat) throws Exception;

	@RequestMapping("/tag/{tag_idx}")
	public abstract ModelAndView tagInfomationAction(@PathVariable Integer tag_idx) throws Exception;

/*	@RequestMapping("/pie")
	public abstract ModelAndView pieChartAction(@RequestParam Integer doc_idx, @RequestParam String part) throws Exception;
*/
}


