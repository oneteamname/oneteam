package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import member.db.MemberDAO;
import member.db.MemberDTO;

@Controller
public class MemberCheckController
{
	@Autowired
	private MemberDAO memberDAO;
	
	@RequestMapping(value = "/member_JoinCheck.do", method = RequestMethod.GET)
	public ModelAndView memberJoinCheckForm(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception
	{
		// TODO Auto-generated method stub
		System.out.println("MemberCheckController.java, GET");
		return new ModelAndView("WEB-INF/member/memberCheckForm.jsp");
	}

	@RequestMapping(value = "/member_JoinCheck.do", method = RequestMethod.POST)
	public ModelAndView memberJoinCheckPro(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		System.out.println(req);
		System.out.println(resp);
		
		ModelAndView mav = new ModelAndView();
		String name=req.getParameter("name");
		String ssn= req.getParameter("ssn");
		String hp= req.getParameter("hp");
		
		MemberDTO dto = null;
		
		System.out.println(name);
		System.out.println(ssn);
		System.out.println(hp);
		dto = memberDAO.checkMember(name, ssn ,hp);

		if (dto == null)
		{// ���԰��� ����form����
			mav.setViewName("WEB-INF/member/memberJoinForm.jsp");
			mav.addObject("name", req.getParameter("name"));
			mav.addObject("ssn", req.getParameter("ssn"));
			mav.addObject("hp", req.getParameter("hp"));
			return mav;
		} else
		{// ���ԺҰ� main����(script"�̹̰��Ե� ȸ���Դϴ�. �α��� �� �ּ���.")
			mav.setViewName("index.jsp");
			mav.addObject("memberCheck", false);

			return mav;
		}
	}

}
