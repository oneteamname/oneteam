package admin.fileboard.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import admin.fileboard.db.AdminFILEBoardDAO;

@Controller
public class AdminFILEBoardListController {
	
	@Autowired
	private AdminFILEBoardDAO adminFILEBoardDAO;
	
	@RequestMapping(value="/admin_fileboard_list.do")
	public ModelAndView admin_file_list(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		int pageSize = 4;
		String pageNum = arg0.getParameter("pageNum");
		if(pageNum == null){
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = pageSize*(currentPage-1)+1;
		int endRow = startRow+pageSize-1;
		int count = 0;
		
		count = adminFILEBoardDAO.getCount();
		
		if(endRow > count){
			endRow = count;
		}
		
		int pageCount = count/pageSize+(count%pageSize==0 ? 0 : 1);
		int pageBlock = 3;
		int startPage = (currentPage-1)/pageBlock*pageBlock+1;
		int endPage = startPage+pageBlock-1;
		
		List list = adminFILEBoardDAO.listBoard(startRow, endRow);
		
		HttpSession session = arg0.getSession();
		String upPath = arg0.getServletContext().getRealPath("fileboard_files/");
		session.setAttribute("upPath", upPath);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("endPage", endPage);
		mav.addObject("startPage", startPage);
		mav.addObject("pageBlock", pageBlock);
		mav.addObject("pageCount", pageCount);
		mav.addObject("pageSize", pageSize);
		mav.addObject("count", count);
		mav.addObject("currentPage", currentPage);
		mav.addObject("boardList", list);
		
		mav.setViewName("admin/fileboard/admin_list.jsp");
		
		return mav;
	}
	
	@RequestMapping(value="/admin_fileboard_delete.do")
	public ModelAndView admin_file_delete(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		int num = ServletRequestUtils.getIntParameter(arg0, "num");
		String id = ServletRequestUtils.getStringParameter(arg0, "id");
		String fileName = ServletRequestUtils.getStringParameter(arg0, "fileName");
		
		String upPath = arg0.getServletContext().getRealPath("/fileboard_files/"+id);
		File file = new File(upPath+"/"+fileName);
		
		boolean delResult = file.delete();
		if(!delResult){
	    	System.out.println("��������!!");
	    }
		
		adminFILEBoardDAO.deleteBoard(num);
		return new ModelAndView("redirect:admin_fileboard_list.do");
	}
}
