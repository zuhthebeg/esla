package spring.esla.impl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;



import spring.esla.beans.Tree;
import spring.esla.controller.*;
import spring.esla.model.DocumentDao;
@Service
public class DocumentService extends DocumentController {
	@Autowired
	private DocumentDao documentDao;
	
	@Override
	public ModelAndView initAction() throws Exception {
		ModelAndView mav = new ModelAndView("json");
		 ArrayList<Tree> process = documentDao.getProcessNodes();
		 ArrayList<HashMap<String, Object>> nodes = new ArrayList<HashMap<String, Object>>();
		for(Tree node : process){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("title", node.getVal());
			map.put("key", node.getIdx());
			map.put("name", node.getName());
			map.put("isFolder",true);
			map.put("isLazy", true);
			//map.put("expand",true);
			nodes.add(map);
		}
		
		Tree topNode = documentDao.getMetainfoByDocumentIdx(0);
		mav.addObject("title",topNode.getVal());
		mav.addObject("key", topNode.getIdx());
		mav.addObject("isFolder",true);
		mav.addObject("expand",true);
		mav.addObject("children", nodes);
		mav.addObject("processList",process);
		return mav;
	}

	@Override
	public void childAction(@PathVariable Integer idx, HttpServletResponse response) throws Exception {
		 ArrayList<Tree> childs = documentDao.getChildNodeByIdx(idx);
		 ArrayList<HashMap<String, Object>> nodes = new ArrayList<HashMap<String, Object>>();
		for(Tree node : childs){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("title", node.getVal());
			map.put("name", node.getName());
			map.put("key", node.getIdx());
			if(node.getIs_leaf().equals("false")){
				map.put("isFolder",true);
				map.put("isLazy", true);
			}
			//map.put("expand",true);
			nodes.add(map);
		}
		 
		JSON json = JSONSerializer.toJSON(nodes, new JsonConfig());
		response.setContentType("application/x-json;charset=utf-8");
		response.getWriter().print(json.toString());
	}

	@Override
	public ModelAndView manageAction(Integer idx,  Integer level) throws Exception {
		Tree node = documentDao.getMetainfoByDocumentIdx(idx);
		
		ModelAndView mav = new ModelAndView("manage");
		mav.addObject("node", node);
		mav.addObject("level", level);
		return mav;
	}

	@Override
	public ModelAndView manageAction(Tree node) throws Exception {
		documentDao.updateNodeByIdx(node);
		return new ModelAndView("json", "status", "success");
	}

	@Override
	public ModelAndView addNodeAction(Tree node) throws Exception {
		if(node.getName().equals("chapter"))
			node.setIs_leaf("true");
		else
			node.setIs_leaf("false");
		
		documentDao.insertChildNode(node);
		return new ModelAndView("json", "status", "success");
	}

	@Override
	public ModelAndView delete(Integer idx) throws Exception {
		documentDao.deleteNodeAndChild(idx);
		return new ModelAndView("json", "status", "success");
	}

	@Override
	public ModelAndView order(Tree node)
			throws Exception {
		documentDao.updateNodeOrder(node);
		return new ModelAndView("json", "status", "success");
	}

	@Override
	public ModelAndView swapOrder(Tree node)
			throws Exception {
		documentDao.swapNodeOrder(node);
		return new ModelAndView("json", "status", "success");
	}

}
