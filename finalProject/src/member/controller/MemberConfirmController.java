package member.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import member.db.Gmail;
import member.db.MemberDAO;
import member.db.MemberDTO;

@Controller
public class MemberConfirmController
{
	@Autowired
	private Gmail gmail;
	
	@Autowired
	private MemberDAO memberDAO;

	// [�����ϱ�] ��ư Ŭ��-> windows.open("confirm.do") ������ȣ�� ���� �� �ִ� â�� ����.
	// confirm.do������ Ŭ���̾�Ʈ�� �����ּҸ� �ް�, ������ �߻��Ͽ� ������ �����Ѵ�.
	// ������ȣ�� ���� �� �ִ� â���� ������ȣ�� �����ϰ� ��ȣ�� �´ٸ� windows close Ʋ���� ���â
	
	// ������ȣ�� ���� �� �ִ� â���� ���� �����ϸ� confirmOk.do �� �̵�.
	// confirmOk.do������ �����Ϸ�� ID�� hidden���� �ѰܹްԵǰ�  approve(String id)�� �����Ѵ�.
	

	@RequestMapping(value = "/confirm.do", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView memberConfirm(HttpServletRequest req, HttpServletResponse resp)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("WEB-INF/member/memberConfirmForm.jsp");
		return mav;
	}
	
	@RequestMapping(value = "/confirm2.do", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView memberConfirm2(HttpServletRequest req, HttpServletResponse resp)
	{
		ModelAndView mav = new ModelAndView();
		String clientEmail = "";
		if(req.getParameter("clientEmail")!=null)
			clientEmail = req.getParameter("clientEmail");

		String confirmNumber = makeRandomNumber();

		gmail.sendMail(clientEmail, confirmNumber);

		mav.addObject("confirmNumber", confirmNumber);
		HttpSession session = req.getSession();
		
		mav.addObject("loginId", session.getAttribute("loginId"));
		mav.addObject("clientEmail", clientEmail);
		
		mav.setViewName("WEB-INF/member/memberConfirmForm.jsp");
		return mav;
	}
	

	
	
	@RequestMapping(value="/confirmOk.do", method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView memberConfirmOk(HttpServletRequest req, HttpServletResponse resp) throws SQLException
	{
		System.out.println("Controller in req id : "+req.getParameter("loginId"));
		ModelAndView mav = new ModelAndView();
		
		String id = req.getParameter("loginId");
		String num = req.getParameter("memberNum");
		int res = memberDAO.approve(id);
		
		
		if(res>0){
			mav.addObject("confirmResult", true);
			MemberDTO dto = memberDAO.getMemberAdmin(Integer.parseInt(num));
			System.out.println("MemberConfirmController dto :" + dto);
			System.out.println("MemberConfirmController dto.getEmail() :" + dto.getEmail());
			System.out.println("MemberConfirmController req.email :" + req.getParameter("email"));
			if(dto.getEmail() == null && req.getParameter("email")!=null){
				dto.setEmail(req.getParameter("email"));
				int updateRes = memberDAO.updateMember(dto);
				if(updateRes>0){
					System.out.println("ȸ������ �� �̸��� �Է� ���ؼ� �̸��� ���� �ĵ�Ͻ�����");
				}else{
					System.out.println("ȸ������ �� �̸��� �Է� ���ؼ� �̸��� ���� �ĵ�Ͻ����ַ������� ������");
				}
			}
		}else{
			mav.addObject("confirmResult", false);
		}
		mav.setViewName("WEB-INF/member/memberConfirmForm.jsp");
		
		return mav;
	}

	private String makeRandomNumber()
	{
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < 6; i++)
		{
			int n = (int) (Math.random() * 10);
			buffer.append(n);
		}
		return buffer.toString();
	}

	
	
	
}
