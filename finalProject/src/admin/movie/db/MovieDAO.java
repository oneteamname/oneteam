package admin.movie.db;

import java.util.List;

public interface MovieDAO {
	public List movieList_admin(int start,int end);
	public int movieGetCount();
	public List getList(String mode);		// 1. ��ü  2. ������ ��ȭ  3. �󿵿��� ��ȭ  4. ������ ��ȭ  5. �ڽ����ǽ�  
	public List DayToList(String date);		// �ش糯¥�� ���� ��ȭ ����Ʈ(�����Ҷ� �ʿ�)
	public List nowSchedule(String day);
	public Movie_infoDTO getMovie(int num);		// num���� ��ȭ ���� �ҷ�����
	public int insertMovie(Movie_infoDTO dto);	// ��ȭ �߰�
	public int updateMovie(Movie_infoDTO dto);	// ��ȭ ����
	public int updateLike_num(int num);	// ��õ ������ num ���� ��ȭ ���� �ҷ��ͼ�  update  like_num  +1 
	public List searchMovie(String search, String searchStr); // search�� ��ȭ ����
	public List nowMonthMovie(String date);
	///////////////////////////////////////////�⺻ ��ȭ table/////////////////////////////////////
	
	public int insertStillcut(Movie_urlDTO dto); // ��ƿ�� ��� �߰�
	public List getStillcut(int num); //  ��ȭ��ȣ�� select �� stillcut ��ΰ� ��� ����Ʈ 

	/////////////////////////////////////////// ��ƿ�� ��� ////////////////////////////////////////
	
	public void upCount(int num);	// ��ȭ ��ȣ�� �޾Ƽ� ���� �� ��  ī��Ʈ +1 
	public void downCount(int num);	// ��ȭ ��ȣ�� �޾Ƽ� ���� ��� �� ī��Ʈ -1
	
	public int updateClose(int num);	// ��ȭ ��ȣ�� �޾Ƽ� update  closedate :sysdate 
	public List listReply(int movieNum,int startRow,int endRow);
	public int deleteReply(int num);
	public int replyGetCount();
	public int replyGetCount(int movieNum);
	public int insertReply(Movie_info_replyDTO dto);
	public List listReply(int movieNum);
	public List allReply();
}
