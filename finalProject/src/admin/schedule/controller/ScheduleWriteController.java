package admin.schedule.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import admin.schedule.db.ScheduleDAO;
import admin.schedule.db.ScheduleDTO;
@Controller
public class ScheduleWriteController {
	@Autowired
	private ScheduleDAO admin_scheduleDAO;

	@RequestMapping(value="/admin_schedule.do", method=RequestMethod.GET)
	public ModelAndView writeForm(HttpServletRequest arg0, 
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		
		String title = arg0.getParameter("title");
		
		return new ModelAndView("/admin/schedule/admin_schedule_writeForm.jsp?title="+title);
	}
	
	@RequestMapping(value="/admin_schedule.do", method=RequestMethod.POST)
	protected ModelAndView writePro(HttpServletRequest arg0, 
			HttpServletResponse arg1) throws Exception {
		ScheduleDTO dto = new ScheduleDTO();
		dto.setDay(arg0.getParameter("day"));
		dto.setTime(Integer.parseInt(arg0.getParameter("time")));
		dto.setTheater(arg0.getParameter("theater"));
		dto.setTheaternum(Integer.parseInt(arg0.getParameter("theaternum")));
		dto.setTitle(arg0.getParameter("title"));
		admin_scheduleDAO.insertSchedule(dto);
		
		return new ModelAndView("/admin_schedule_list.do");
	}
}
