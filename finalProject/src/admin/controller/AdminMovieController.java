package admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;

import admin.movie.db.MovieDAO;
import admin.movie.db.Movie_infoDTO;
import admin.movie.db.Movie_info_replyDTO;

@Controller
public class AdminMovieController {
	
	@Autowired
	private MovieDAO movieDAO;
	
	
	@RequestMapping(value = "/admin_movie.do", method=RequestMethod.GET)
	public ModelAndView admin_member(HttpServletRequest req, HttpServletResponse resp) throws SQLException
	{
		ModelAndView mav = new ModelAndView();
		int pageSize = 3;
		int pageBlock = 3;
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null)
		{
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int startRow = pageSize * (currentPage - 1) + 1;
		int endRow = startRow + pageSize - 1;
		int count = 0;
		count = movieDAO.movieGetCount();//?
		if(endRow>count){
			endRow=count;
		}
		List list = movieDAO.movieList_admin(startRow, endRow);
		mav.addObject("list", list);
		mav.addObject("pageSize", pageSize);
		mav.addObject("startPage", (currentPage-1)/ pageBlock*pageBlock+1);
		mav.addObject("pageBlock", pageBlock);
		mav.addObject("pageCount", count/pageSize+(count%pageSize==0 ? 0 : 1));
		mav.addObject("count", count);
		mav.addObject("currentPage", currentPage);
		mav.setViewName("admin/movie/admin_movie.jsp");
		return mav;
	}
	// ��ȭ����Ʈ����
	@RequestMapping(value = "/client_movie_all.do")
	public ModelAndView client_movie_all(HttpServletRequest req, HttpServletResponse resp) {
		ModelAndView mav = new ModelAndView();

		int pageSize = 6;
		int pageBlock = 3;

		String pageNum = req.getParameter("pageNum");

		if (pageNum == null) {
			pageNum = "1";
		}
		System.out.println("pageNum : " + pageNum);

		int currentPage = Integer.parseInt(pageNum);
		int startRow = pageSize * (currentPage - 1) + 1;
		int endRow = startRow + pageSize - 1;

		int count = 0;
		System.out.println("movieDAO.movieGetCount() before count : " + count);
		count = movieDAO.movieGetCount();// ?

		if (endRow > count) {
			endRow = count;
		}


		List list = movieDAO.movieList_admin(startRow, endRow);

		List allReplyList = movieDAO.allReply(); // ������
		// String upPath = req.getServletContext().getRealPath("/poster");
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat format2 = new SimpleDateFormat("20yy");
			for (Iterator it = list.iterator(); it.hasNext();) {
				Movie_infoDTO dto = (Movie_infoDTO) it.next();
				String date = format2.format(format1.parse(dto.getOpendate()));
				String str = dto.getTitle() + "(" + date + ")";
				dto.setFile_directory(str);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List numList = new ArrayList();
	      
	      for(Iterator it = allReplyList.iterator(); it.hasNext();){
	    	 Movie_info_replyDTO dto = (Movie_info_replyDTO)it.next();
	    	 numList.add(dto.getMovieNum());
	      }
	      int temp; 
	      for(Iterator numIt = numList.iterator(); numIt.hasNext();){
	    	  String term = (String)numIt.next();
	    	  temp = 1;
		      for(Iterator it = allReplyList.iterator(); it.hasNext();){
			    	 Movie_info_replyDTO dto = (Movie_info_replyDTO)it.next();
		    	 
		    		 if(dto.getMovieNum().equals(term)){
		    			 dto.setRn(temp);
		    			 temp++;
		    		 }
		      }
		  }
	      
	    mav.addObject("numList", numList);
		
		mav.addObject("pageSize", pageSize);
		mav.addObject("startPage", (currentPage - 1) / pageBlock * pageBlock + 1);
		mav.addObject("pageBlock", pageBlock);
		mav.addObject("pageCount", count / pageSize + (count % pageSize == 0 ? 0 : 1));
		mav.addObject("Godcount", count);
		mav.addObject("currentPage", currentPage);
		mav.addObject("allReplyList", allReplyList);
		mav.addObject("allmovieList", list);
		mav.setViewName("client/movie_all.jsp");
		return mav;
	}
	   @RequestMapping(value = "/client_movie_due.do")
	   public ModelAndView client_movie_due(HttpServletRequest req, HttpServletResponse resp) {
	      ModelAndView mav = new ModelAndView();
	      List list = null;
	      list = movieDAO.getList("due");
	      List allReplyList = movieDAO.allReply();
	      
	      String upPath = req.getServletContext().getRealPath("/poster");
	      try {
        	  SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        	  SimpleDateFormat format2 = new SimpleDateFormat("20yy");
        	  for(Iterator it = list.iterator(); it.hasNext();){
        		  Movie_infoDTO dto = (Movie_infoDTO)it.next();
        		  String date = format2.format(format1.parse(dto.getOpendate()));
        		  String str = dto.getTitle()+"("+date+")";
        		  dto.setFile_directory(str);
        	  }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      List numList = new ArrayList();
	      
	      for(Iterator it = allReplyList.iterator(); it.hasNext();){
	    	 Movie_info_replyDTO dto = (Movie_info_replyDTO)it.next();
	    	 numList.add(dto.getMovieNum());
	      }
	      mav.addObject("numList", numList);
	      mav.addObject("allReplyList", allReplyList);
	      mav.addObject("duemovieList", list);
	      mav.setViewName("/client/movie_due.jsp");

	      return mav;
	   }
	   @RequestMapping(value = "/client_movie_box.do")
	   public ModelAndView client_movie_box(HttpServletRequest req, HttpServletResponse resp) {
	      ModelAndView mav = new ModelAndView();
	      List list = null;
	      List allReplyList = movieDAO.allReply();
	      String upPath = req.getServletContext().getRealPath("/poster");
	      
	      mav.addObject("allReplyList",allReplyList);
	      list = movieDAO.getList("box");
	      
	      
	      try {
        	  SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        	  SimpleDateFormat format2 = new SimpleDateFormat("20yy");
        	  for(Iterator it = list.iterator(); it.hasNext();){
        		  Movie_infoDTO dto = (Movie_infoDTO)it.next();
        		  String date = format2.format(format1.parse(dto.getOpendate()));
        		  String str = dto.getTitle()+"("+date+")";
        		  dto.setFile_directory(str);
        	  }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      List numList = new ArrayList();
	      
	      for(Iterator it = allReplyList.iterator(); it.hasNext();){
	    	 Movie_info_replyDTO dto = (Movie_info_replyDTO)it.next();
	    	 numList.add(dto.getMovieNum());
	      }
	      mav.addObject("numList", numList);	    
	      mav.addObject("boxmovieList", list);
	      mav.setViewName("/client/movie_box.jsp");

	      return mav;
	   }
	   @RequestMapping(value = "/client_movie_now.do")
	   public ModelAndView client_movie_now(HttpServletRequest req, HttpServletResponse resp) {
	      ModelAndView mav = new ModelAndView();
	      List list = null;
	      String upPath = req.getServletContext().getRealPath("/poster");
	      list = movieDAO.getList("now");
	      List allReplyList = movieDAO.allReply();
	      
	      
	      try {
        	  SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        	  SimpleDateFormat format2 = new SimpleDateFormat("20yy");
        	  for(Iterator it = list.iterator(); it.hasNext();){
        		  Movie_infoDTO dto = (Movie_infoDTO)it.next();
        		  String date = format2.format(format1.parse(dto.getOpendate()));
        		  String str = dto.getTitle()+"("+date+")";
        		  dto.setFile_directory(str);
        	  }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      List numList = new ArrayList();
	      
	      for(Iterator it = allReplyList.iterator(); it.hasNext();){
	    	 Movie_info_replyDTO dto = (Movie_info_replyDTO)it.next();
	    	 numList.add(dto.getMovieNum());
	      }
	      mav.addObject("numList", numList);
	      mav.addObject("allReplyList",allReplyList);
	      mav.addObject("nowmovieList", list);
	      mav.setViewName("/client/movie_now.jsp");

	      return mav;
	   }
	//��ȭ�󼼺���
		@RequestMapping(value="/admin_movie_detail.do",method=RequestMethod.GET)
		public ModelAndView admin_movie_detail(HttpServletRequest req,HttpServletResponse resp){
			ModelAndView mav = new ModelAndView();
			
			int num = Integer.parseInt(req.getParameter("num"));
			String openDate=null;
			Movie_infoDTO dto = null;
			List replyList = null;
			
			int pageSize = 3;
			int pageBlock = 3;
			
			String pageNum = req.getParameter("pageNum");
			String upPath = req.getServletContext().getRealPath("/poster");
			Calendar today = Calendar.getInstance();
			if (pageNum == null)
			{
				pageNum = "1";
			}
			System.out.println("pageNum : "+pageNum);
			
			int currentPage = Integer.parseInt(pageNum);
			int startRow = pageSize * (currentPage - 1) + 1;
			int endRow = startRow + pageSize - 1;
			
			int count = 0;
			count = movieDAO.replyGetCount(num);
			
			
			dto = movieDAO.getMovie(num);
			replyList = movieDAO.listReply(num,startRow,endRow);
			
			int todayYear = today.get(Calendar.YEAR);
			int todayMonth = today.get(Calendar.MONTH)+1;
			int todayDay = today .get(Calendar.DATE);
			Date now = null;
			Date closeDate=null;
			Date opendate=null;
			int compare=1;
			try {
	        	  SimpleDateFormat format1 = new SimpleDateFormat("yy/MM/dd");
	        	  SimpleDateFormat format2 = new SimpleDateFormat("20yy");
	        	  String date = format2.format(format1.parse(dto.getOpendate()));
        		  String str = dto.getTitle()+"("+date+")";
        		  dto.setFile_directory(str);
	              System.out.println(str);
	              System.out.println("20"+dto.getClosedate());
	              now = format1.parse(todayYear+"/"+todayMonth+"/"+todayDay);
	              closeDate = format1.parse("20"+dto.getClosedate());
	              opendate = format1.parse("20"+dto.getClosedate());
	                 compare = opendate.compareTo(now);
	                 if(compare>0){
	                    compare = now.compareTo(closeDate);
	                 }
	              compare = now.compareTo(closeDate);
	              System.out.println(compare);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mav.addObject("endRow", endRow);
			mav.addObject("compare", compare);
			mav.addObject("openDate", openDate);
			mav.addObject("upPath", upPath);
			mav.addObject("pageSize", pageSize);
			mav.addObject("startPage", (currentPage-1)/ pageBlock*pageBlock+1);
			mav.addObject("pageBlock", pageBlock);
			mav.addObject("pageCount", count/pageSize+(count%pageSize==0 ? 0 : 1));
			
			mav.addObject("count", count);
			mav.addObject("currentPage", currentPage);
			
			mav.addObject("movieDTO",dto);
			mav.addObject("replyList", replyList);
			mav.setViewName("/admin/movie/admin_movie_detail.jsp");
			
			return mav;
		}
		
		//��ȭ ������ ����
		@RequestMapping(value="/admin_movie_replyDelete.do",method=RequestMethod.GET)
		public ModelAndView admin_movie_replyDelete(HttpServletRequest req,HttpServletResponse resp){
			ModelAndView mav = new ModelAndView();
			String mode = req.getParameter("mode");
			int num = Integer.parseInt(req.getParameter("num"));
			String url2 = req.getHeader("referer");
			System.out.println(url2);
/*			String url3[] =url2.split("[?]");
			if(url3.length>1){
				url2 = url3[0];
			}*/
				
			String msg=null;
			String url=null;
			if(mode.equals("admin")){
				int movieNum = Integer.parseInt(req.getParameter("movieNum"));
				url="/admin_movie_detail.do?num="+movieNum; //�ڷΰ��� location??
			}if(mode.equals("all")){
				url="/client_movie_all.do";
				System.out.println("��������");
			}
			if(mode.equals("now")){
				url="/client_movie_now.do";
				System.out.println("��������");
			}else if(mode.equals("box")){
				url="/client_movie_box.do";
				System.out.println("��������");
			}else if(mode.equals("due")){
				url="/client_movie_due.do";
				System.out.println("��������");
			}
			
			
			int res = movieDAO.deleteReply(num);
			if(res>0){
			}else{
				msg="������ ���� ����!!";
				mav.addObject("msg", msg);
				System.out.println("��������");
			}
			
			
			mav.setViewName(url);
			
			return mav;
		}
		
		//��ȭ���
		@RequestMapping(value="/admin_movie_insert.do",method=RequestMethod.GET)
		public ModelAndView admin_movie_writeForm(HttpServletRequest req,HttpServletResponse resp){
			
			return new ModelAndView("/admin/movie/movie_input.jsp");
		}
		
		//��ȭ���
		@RequestMapping(value="/movie_inputPro.do",method=RequestMethod.POST)
		public ModelAndView admin_movie_writePro(HttpServletRequest req,HttpServletResponse resp) {
			ModelAndView mav = new ModelAndView();
			Movie_infoDTO dto = new Movie_infoDTO();
			MultipartRequest mu = null;
			String upPath = req.getServletContext().getRealPath("");	//���н� ����???
			String msg=null;
			String url="";
			String realFileName=null;
			String openDate=null;
				try {
					mu = new MultipartRequest(req,upPath,10*1024*1024,"UTF-8");
					String fileName = mu.getFilesystemName("poster");
					try {
			        	  SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
			        	  SimpleDateFormat format2 = new SimpleDateFormat("20yy");
			              openDate = format2.format(format1.parse(mu.getParameter("opendate")));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    String fileReName = mu.getParameter("title")+"("+openDate+")";
				    int i = -1;
				          i = fileName.lastIndexOf("."); // ���� Ȯ���� ��ġ
				          realFileName = fileReName + fileName.substring(i, fileName.length());  //Ȯ���ڿ� �ٲ� ���ϸ� ��ġ��
				    FileInputStream fis = new FileInputStream(upPath+fileName);
				    File oldFile = new File(upPath+fileName);
				    upPath = req.getServletContext().getRealPath("/poster/"+fileReName);
				    File dir = new File(upPath);
				    dir.mkdirs();	//��ȭ�̸��� �´� ���丮 ����
				    FileOutputStream fos = new FileOutputStream(upPath+"/"+realFileName);
				    byte [] buf = new byte[1024];
					while(fis.available()>0)
					{
						int len = fis.read(buf);
						fos.write(buf, 0, len);
					}
					fis.close();
					fos.close();
					boolean delete=oldFile.delete();
				    if(!delete){
				    	System.out.println("��������!!");
				    }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			dto.setTitle(mu.getParameter("title"));
			dto.setGenre(mu.getParameter("genre"));
			dto.setGrade(Integer.parseInt(mu.getParameter("grade")));
			dto.setCountry(mu.getParameter("country"));
			dto.setOpendate(mu.getParameter("opendate"));
			dto.setRuntime(Integer.parseInt(mu.getParameter("runtime")));
			dto.setMovie_info(mu.getParameter("movie_info"));
			dto.setPoster(realFileName);
			dto.setDirector(mu.getParameter("director"));
			dto.setActor(mu.getParameter("actor"));
			
			int res = movieDAO.insertMovie(dto);
			
			if(res>0){
				url="redirect:/admin_movie.do";
			}else{
				msg="��ȭ ��� ����!!";
				url="/admin_movie_insert.do";
			}
			
			mav.addObject("msg",msg);
			mav.setViewName(url);
			
			return mav;
		}
		 // ��������
	   @RequestMapping(value = "/admin_movie_insertReply.do")
	   public ModelAndView admin_movie_insert_reply(HttpServletRequest req, HttpServletResponse resp) {
	      ModelAndView mav = new ModelAndView();
	      Movie_info_replyDTO dto = new Movie_info_replyDTO();
	      String msg = "";
	      String url = "";
	      String surl = req.getParameter("surl"); // �� ���� �ɰ��� ������ �־����.
	      System.out.println(surl);
	      String backurl = surl.substring(surl.length() - 13, surl.length() - 4);
	      System.out.println(backurl);
	      if(req.getParameter("id").trim().equals("")||req.getParameter("id")==null){
	    	  msg = "�α����� ���ּ���";
	    	  url = "/client_" + backurl + ".do"; 
	    	  mav.addObject("msg", msg);
	    	  mav.setViewName(url);

		      return mav;
	      }
	      dto.setId(req.getParameter("id"));
	      dto.setReply(req.getParameter("reply"));
	      dto.setMovieNum(req.getParameter("movieNum"));
	      System.out.println(dto);
	      int res = movieDAO.insertReply(dto);
	      
	      if (res > 0) {
	         url = "redirect:/client_" + backurl + ".do"; 
	      } else {
	         msg = "��� ����!!";
	         url = "/admin_movie_insert.do";
	         mav.addObject("msg", msg);
	      }

	      
	      mav.setViewName(url);

	      return mav;
	   }
	   
	/*//��ȭ����Ʈ����
	
	@RequestMapping(value="/client_movie_due.do")
	public ModelAndView client_movie_due(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView();
		List list = null;
		list = movieDAO.getList("due");
	
		mav.addObject("duemovieList",list);
		mav.setViewName("/client/movie_due.jsp");
		
		return mav;
	}
	@RequestMapping(value="/client_movie_box.do")
	public ModelAndView client_movie_box(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView();
		List list = null;
		
		list = movieDAO.getList("box");
		
	
		
		mav.addObject("boxmovieList",list);
		mav.setViewName("/client/movie_box.jsp");
		
		return mav;
	}
	@RequestMapping(value="/client_movie_now.do")
	public ModelAndView client_movie_now(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView();
		List list = null;
		
		list = movieDAO.getList("now");
	
		mav.addObject("nowmovieList",list);
		mav.setViewName("/client/movie_now.jsp");
		
		return mav;
	}
	
	
	
	
	
	//��ȭ ������Ʈ
	@RequestMapping(value="/admin_movie_update.do",method=RequestMethod.GET)
	public ModelAndView admin_movie_updateForm(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView();
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		Movie_infoDTO dto = null;
		
		dto = movieDAO.getMovie(num);
		
		mav.addObject("movieDTO",dto);
		mav.setViewName("/admin/movie/admin_movie_update.jsp");
		
		return mav;
	}
	
	
	//��ȭ ������Ʈ
	@RequestMapping(value="/admin_movie_update.do",method=RequestMethod.POST)
	public ModelAndView admin_movie_updatePro(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView();
		Movie_infoDTO dto = new Movie_infoDTO();
		MultipartRequest mu = null;
		String upPath = req.getServletContext().getRealPath("");	//���н� ����???
		String msg="";
		String url="";
		try {
			mu = new MultipartRequest(req,upPath,10*1024*1024,"EUC-KR");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dto.setNum(Integer.parseInt(mu.getParameter("num")));
		dto.setTitle(mu.getParameter("title"));
		dto.setGenre(mu.getParameter("genre"));
		dto.setGrade(Integer.parseInt(mu.getParameter("grade")));
		dto.setCountry(mu.getParameter("country"));
		dto.setOpendate(mu.getParameter("opendate"));
		dto.setRuntime(Integer.parseInt(mu.getParameter("runtime")));
		dto.setMovie_info(mu.getParameter("movie_info"));
		dto.setPoster(mu.getFilesystemName("poster"));
		dto.setDirector(mu.getParameter("director"));
		dto.setActor(mu.getParameter("actor"));
		
		int res = movieDAO.updateMovie(dto);
		
		if(res>0){
			msg="���� ����!!";
			url="/admin_movie.do";
		}else{
			msg="���� ����!!";
			url="/admin_movie.do";
		}
		
		mav.addObject("msg",msg);
		mav.setViewName(url);
		
		return mav;
	}
	
	//��ȭ����
	@RequestMapping(value="/admin_movie_delete.do")
	public ModelAndView admin_movie_delete(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView();
		
		int res = 0;
		
		return mav;
	}
	
	//��ȭ�˻�
	@RequestMapping(value="/search_movie.do",method=RequestMethod.POST)
	public ModelAndView search_movie(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView();
		String search = req.getParameter("search");
		String searchStr = req.getParameter("searchStr");
		
		List list = null;
		
		list = movieDAO.searchMovie(search, searchStr);
		
		mav.addObject("movieList",list);
		mav.setViewName("/admin_movie.do");
		
		return mav;
		
	}
	// ��ȭ ��ȣ�� �޾Ƽ� ���� �� ��  ī��Ʈ +1 
	@RequestMapping(value="/upcount.do",method=RequestMethod.GET)
	public ModelAndView upCount(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView();
		int num = Integer.parseInt(req.getParameter("num"));
		
		movieDAO.upCount(num);
		
		return new ModelAndView("");
	}
	// ��ȭ ��ȣ�� �޾Ƽ� ���� �� ��  ī��Ʈ -1 
	@RequestMapping(value="/downcount.do",method=RequestMethod.GET)
	public ModelAndView downCount(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mav = new ModelAndView();
		int num = Integer.parseInt(req.getParameter("num"));
		
		movieDAO.downCount(num);
		
		return new ModelAndView("");
	}*/
	
	
	
}
