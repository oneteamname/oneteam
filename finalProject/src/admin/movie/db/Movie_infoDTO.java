package admin.movie.db;

public class Movie_infoDTO {
	private int num;		// ��ȣ (������ movies_seq)
	private String title;	// ��ȭ ����
	private String genre;	// �帣
	private int grade;		// ��û���ɿ��� (0 = ��ü , 12 = 12�� , 15, 18, 19 )
	private String country;	// ��ȭ ����
	private String opendate;// ������
	private int runtime;	// �󿵽ð� (�� ���� / 120, 150, 188 ...) 
	private String movie_info;// ��ȭ ����
	private String poster;	// ����Ʈ url �ּ�
	private int watchcount;	// ��û�ο� ��
	private int like_num;	// ��õ �� 
	private String director;
	private String actor;
	private String file_directory;
	private String closedate;
	
	
	
	
	public String getClosedate() {
		return closedate;
	}
	public void setClosedate(String closedate) {
		this.closedate = closedate;
	}
	public String getFile_directory() {
		return file_directory;
	}
	public void setFile_directory(String file_directory) {
		this.file_directory = file_directory;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOpendate() {
		return opendate;
	}
	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}
	public int getRuntime() {
		return runtime;
	}
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	public String getMovie_info() {
		return movie_info;
	}
	public void setMovie_info(String movie_info) {
		this.movie_info = movie_info;
	}
	public int getLike_num() {
		return like_num;
	}
	public void setLike_num(int like_num) {
		this.like_num = like_num;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public int getWatchcount() {
		return watchcount;
	}
	public void setWatchcount(int watchcount) {
		this.watchcount = watchcount;
	}
	

	
}
