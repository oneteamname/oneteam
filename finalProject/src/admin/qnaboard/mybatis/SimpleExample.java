package admin.qnaboard.mybatis;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ibatis.common.resources.Resources;

import admin.qnaboard.db.AdminQNABoardDTO;
import qnaboard.db.QNABoardDTO;


/**
 * This is not a best practices class. It's just an example to give you an idea
 * of how iBATIS works. For a more complete example, see JPetStore 5.0 at
 * http://www.ibatis.com.
 */
public class SimpleExample {

	/**
	 * SqlMapClient instances are thread safe, so you only need one. In this
	 * case, we'll use a static singleton. So sue me. ;-)
	 */
	private static SqlSessionFactory sqlMapper;

	/**
	 * It's not a good idea to put code that can fail in a class initializer,
	 * but for sake of argument, here's how you configure an SQL Map.
	 */
	static {
		try {
			Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		} catch (IOException e) {
			// Fail fast.
			throw new RuntimeException("Something bad happened while building the SqlMapClient instance." + e, e);
		}
	}
	
	public static void admin_deleteBoard(int num){
		SqlSession session = sqlMapper.openSession();
		session.delete("admin_QNAdeleteBoard", num);
		session.commit();
		session.close();
	}
	
	public static void admin_insertBoard(HashMap map){
		SqlSession session = sqlMapper.openSession();
		session.insert("admin_QNAinsertBoard", map);
		session.commit();
		session.close();
	}
	
	public static int admin_getCount(String mode){
		SqlSession session = sqlMapper.openSession();
		int count = 0;
		count = session.selectOne("admin_QNAgetCount", mode);
		session.close();
		return count;
	}

	public static int admin_allGetCount(){
		SqlSession session = sqlMapper.openSession();
		int count = 0;
		count = session.selectOne("admin_QNAallGetCount");
		session.close();
		return count;
	}
	
	public static List admin_allListBoard(HashMap map){
		SqlSession session = sqlMapper.openSession();
		List list = session.selectList("admin_QNAallListBoard", map);
		session.close();
		return list;
	}

	public static List admin_listBoard(HashMap map) {
		List list = null;
		SqlSession session = sqlMapper.openSession();
		list = session.selectList("admin_QNAlistBoard", map);
		session.close();
		System.out.println("list size : "+list.size());
		return list;
	}
	
	public static AdminQNABoardDTO admin_getBoard(int num){
		SqlSession session = sqlMapper.openSession();
		AdminQNABoardDTO dto = (AdminQNABoardDTO) session.selectOne("admin_QNAgetBoard", num);
		session.close();
		return dto;
	}
	
	public static void admin_upCount(int num){
		SqlSession session = sqlMapper.openSession();
		session.update("admin_QNAupCount", num);
		session.commit();
		session.close();
	}
	
	public static void deleteBoard(int num){
		SqlSession session = sqlMapper.openSession();
		session.delete("QNAdeleteBoard", num);
		session.commit();
		session.close();
	}
	
	public static void updateBoard(HashMap map){
		SqlSession session = sqlMapper.openSession();
		session.update("QNAupdateBoard", map);
		session.commit();
		session.close();
	}

	public static List admin_getCategory(){
		SqlSession session = sqlMapper.openSession();
		List list = session.selectList("admin_QNAgetCategory");
		session.close();
		return list;
	}
	
	public static void admin_stepUpdate(int re_step){
		SqlSession session = sqlMapper.openSession();
		session.update("admin_stepUpdate", re_step);
		session.commit();
		session.close();
	}
}
