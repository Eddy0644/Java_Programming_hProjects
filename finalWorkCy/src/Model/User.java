package Model;

public class User {
	static int ID = 1;
	public int id;
	public String user_number;
	public String user_name;
	public String user_password;
	public int role_type; //1 for teacher, 2 for student
	
	public User(String user_number, String user_name, String user_password, int role_type) {
		this.id = ID++;
		this.user_number = user_number;
		this.user_name = user_name;
		this.user_password = user_password;
		this.role_type = role_type;		
	}
	
	public User(int id, String user_number, String user_name, String user_password, int role_type) {
		if(ID <= id) {
			ID = id + 1;
		}
		
		this.id = id;
		this.user_number = user_number;
		this.user_name = user_name;
		this.user_password = user_password;
		this.role_type = role_type;		
	}
	
	public String toString() {
		return this.id + "|" + this.user_number + "|" + this.user_name + "|" + this.user_password + "|" + this.role_type;
	}
	
	public static User fromString(String inputStr) {
		String[] inputArray = inputStr.split("\\|");
		if(inputArray.length == 5) {
			return new User(Integer.parseInt(inputArray[0]), inputArray[1], inputArray[2], inputArray[3], Integer.parseInt(inputArray[4]));
		}
		
		return null;
	}
}
