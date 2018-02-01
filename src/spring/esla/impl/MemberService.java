package spring.esla.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import spring.esla.model.MemberDao;

import spring.esla.beans.Member;
import spring.esla.controller.MemberController;

@Service
public class MemberService extends MemberController {
	@Autowired
	private MemberDao MemberDao;

	@Override
	public ModelAndView joinAction(Member user) throws Exception {
		MemberDao.insertUserInfo(user);
		
		return new ModelAndView("json","status","success");
	}

	@Override
	public ModelAndView changepwAction(Member user) throws Exception {
		MemberDao.updateUserPassword(user);
		return new ModelAndView("json","status","success");
	}


}
