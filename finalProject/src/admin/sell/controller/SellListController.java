package admin.sell.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import admin.movie.db.MovieDAO;
import admin.sell.db.SellDAO;

@Controller
public class SellListController{
	@Autowired
	private SellDAO sellDAO;
	@Autowired
	private MovieDAO movieDAO;
	
	/*private int priceTicket = 8000;*/
	
	
	@RequestMapping(value="/admin_sales.do")
	public ModelAndView admin_sales(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception{
		return new ModelAndView("/admin/sell/admin_sales.jsp");
	}
	
	
	@RequestMapping(value="/admin_sell_month.do")
	public ModelAndView MonthList(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mav = new ModelAndView();
		Map<String,Integer> sellRes = new HashMap();
		
		List<String> sellStr = new ArrayList();
		
		sellStr.add("Adult");
		sellStr.add("Student");
		sellStr.add("Normal");
		
		Calendar now = Calendar.getInstance();
		
		int yy = now.get(Calendar.YEAR);
		int mm = now.get(Calendar.MONTH)+1;
		
		String year = yy+"";
		for (int i = 0; i < mm; i++) {
			String month = (mm-i) +"";
			if(Integer.parseInt(month)<10){
				month="0"+(mm-i);
			}
			System.out.println(month);
			String nowDate = year.substring(2, 4)+"/"+month;
			for(Iterator it = sellStr.iterator();it.hasNext();){
				String sellMode = (String)it.next();
				
				System.out.println(nowDate);
				sellRes.put((mm-i)+sellMode, sellDAO.sellMode("Month"+sellMode, nowDate));
			}
		}
		
		mav.addObject("year", year);
		mav.addObject("month", mm);
		mav.addObject("sellMap",sellRes);
		mav.addObject("sellStr", sellStr);
		mav.setViewName("/admin/sell/month2.jsp");
		
		return mav;
	}
	
	
	@RequestMapping(value="/admin_sell_movie.do")
	public ModelAndView movieList(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mav = new ModelAndView();
		Map<String,Integer> sellRes = new HashMap();
		Map<String,String> map = new HashMap();
		List<String> sellStr = new ArrayList();
		sellStr.add("MoviesAdult");
		sellStr.add("MoviesStudent");
		sellStr.add("Movies");
		
		
		Calendar now = Calendar.getInstance();
		
		int yy = now.get(Calendar.YEAR);
		int mm = Integer.parseInt(arg0.getParameter("month"));
		
		String year = yy+"";
		
		String month = mm +"";
		if(Integer.parseInt(month)<10){
			month="0"+mm;
		}
		String nowDate = year.substring(2, 4)+"/"+month;
		List<String> monthMovieList = movieDAO.nowMonthMovie(nowDate);
		
		for(Iterator it = sellStr.iterator();it.hasNext();){
			String sellMode = (String)it.next();
			map.put("date", nowDate);
					
			for(Iterator listIt = monthMovieList.listIterator(); listIt.hasNext();){
				String title = (String)listIt.next();
				map.put("title",title);
				sellRes.put(mm+title+sellMode, sellDAO.sellMovieMode("Month"+sellMode, map));
			}
			
		}
		mav.addObject("year", year);
		mav.addObject("sellMap",sellRes);
		mav.addObject("sellStr", sellStr);
		mav.addObject("titleList", monthMovieList);
		mav.addObject("month",mm);
		mav.setViewName("/admin/sell/movie2.jsp");
		return mav;
	}
	
}
