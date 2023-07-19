package Model;

import Entry.Main;

import java.util.HashMap;

public class Course {
	static int ID = 1;
	public int id;
	public String course_code;
	public String course_name;
	public String course_hour;
	public float which_year;
	public int course_nature;
	public int course_type;
	public float course_grade;
	public String course_room;
	public String course_time;
	public String course_week;
	public int teacher_id;
	public int member_count;
	
	public Course(String course_code, String course_name, String course_hour, float which_year, 
					int course_nature, int course_type, float course_grade, String course_room, String course_time,
					String course_week, int member_count, int teacher_id) {
		this.id = ID++;
		this.course_code = course_code;
		this.course_name = course_name;
		this.course_hour = course_hour;
		this.which_year = which_year;	
		this.course_nature = course_nature;	
		this.course_type = course_type;	
		this.course_grade = course_grade;	
		this.course_room = course_room;	
		this.course_time = course_time;	
		this.course_week = course_week;	
		this.teacher_id = teacher_id;	
		this.member_count = member_count;	
	}
	
	public Course(int id, String course_code, String course_name, String course_hour, float which_year, 
			int course_nature, int course_type, float course_grade, String course_room, String course_time,
			String course_week, int member_count, int teacher_id) {
		if(ID <= id) {
			ID = id + 1;
		}
		
		this.id = id;
		this.course_code = course_code;
		this.course_name = course_name;
		this.course_hour = course_hour;
		this.which_year = which_year;	
		this.course_nature = course_nature;	
		this.course_type = course_type;	
		this.course_grade = course_grade;	
		this.course_room = course_room;	
		this.course_time = course_time;	
		this.course_week = course_week;	
		this.teacher_id = teacher_id;	
		this.member_count = member_count;
	}
	
	public String toString() {
		return this.id + "|" + this.course_code + "|" + this.course_name + "|" + this.course_hour + "|" + this.which_year + "|" + this.course_nature + "|"
				+ this.course_type + "|" + this.course_grade + "|" + this.course_room + "|" + this.course_time + "|" + this.course_week + "|" +this.member_count + "|" + this.teacher_id;
	}
	
	public static Course fromString(String inputStr) {
		String[] inputArray = inputStr.split("\\|");
		if(inputArray.length == 13) {
			return new Course(Integer.parseInt(inputArray[0]), inputArray[1], inputArray[2], inputArray[3],
							Float.parseFloat(inputArray[4]), Integer.parseInt(inputArray[5]), Integer.parseInt(inputArray[6]),
							Float.parseFloat(inputArray[7]), inputArray[8], inputArray[9], inputArray[10], Integer.parseInt(inputArray[11]), Integer.parseInt(inputArray[12]));
		}
		
		return null;
	}
	
	public int getChosenNumber() {
		HashMap<String, String> queryCondition = new HashMap<>();
  		queryCondition.put("course_id", String.valueOf(this.id));
  		return Main.ModelChosen.selectManyObjectsFromList(queryCondition).size();
	}
	
	public boolean hasChosen() {
		HashMap<String, String> queryCondition = new HashMap<>();
  		queryCondition.put("course_id", String.valueOf(this.id));
  		queryCondition.put("student_user_id", String.valueOf(((User)Main.SessionHold.get("CurrentLoginUser")).id));
  		return Main.ModelChosen.selectSingleObjectFromList(queryCondition) != null;
	}
}
