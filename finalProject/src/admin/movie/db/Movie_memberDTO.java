package admin.movie.db;

public class Movie_memberDTO {
	private int num;			// ��ȭ ��ȣ �޾ƿ� �� 				��ȣ  |  ����   |  ���  
	private String director;	// ��ȭ ����					 1	  ABC    abc
	private String actor;		// ��ȭ ���					 1    ABC    def  ...
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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

}
