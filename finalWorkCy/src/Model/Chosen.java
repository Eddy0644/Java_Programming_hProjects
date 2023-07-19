package Model;

import Entry.Main;

import java.util.HashMap;

public class Chosen {
	static int ID = 1;
	public int id;
	public int course_id;
	public int student_user_id;
	public float course_score;
	
	public Chosen(int course_id, int course_name, float course_score) {
		this.id = ID++;
		this.course_id = course_id;
		this.student_user_id = course_name;
		this.course_score = course_score;
	}
	
	public Chosen(int id, int course_id, int course_name, float course_score) {
		if(ID <= id) {
			ID = id + 1;
		}
		
		this.id = id;
		this.course_id = course_id;
		this.student_user_id = course_name;
		this.course_score = course_score;
	}
	
	public String toString() {
		return this.id + "|" + this.course_id + "|" + this.student_user_id + "|" + this.course_score;
	}
	
	public static Chosen fromString(String inputStr) {
		String[] inputArray = inputStr.split("\\|");
		if(inputArray.length == 4) {
			return new Chosen(Integer.parseInt(inputArray[0]), Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2]), Float.parseFloat(inputArray[3]));
		}
		
		return null;
	}
	
	public Course course() {
		HashMap<String, String> queryCondition = new HashMap<>();
		queryCondition.put("id", String.valueOf(this.course_id));
		return (Course) Main.ModelCourse.selectSingleObjectFromList(queryCondition);
	}
	
	public User user() {
		HashMap<String, String> queryCondition = new HashMap<>();
		queryCondition.put("id", String.valueOf(this.student_user_id));
		return (User) Main.ModelUser.selectSingleObjectFromList(queryCondition);
	}
}
