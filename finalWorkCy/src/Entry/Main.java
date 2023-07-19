package Entry;

import ModelController.ModelChosenController;
import ModelController.ModelCourseController;
import ModelController.ModelUserController;
import UIController.LoginController;

import java.util.HashMap;

public class Main {

    public static HashMap<String, Object> SessionHold = new HashMap<>();
    public static ModelUserController ModelUser;
    public static ModelCourseController ModelCourse;
    public static ModelChosenController ModelChosen;

    public static void init() {
        ModelUser = new ModelUserController();
        ModelCourse = new ModelCourseController();
        ModelChosen = new ModelChosenController();
    }

    public static void main(String[] args) {
        Main.init();
        new LoginController();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Login Controller status "
                    + (LoginController.status == 0 ? "is 0, may restart" : "not 0, exit."));
            //TODO add re-login after user press exit
//            if (LoginController.status != 0) new LoginController();
        }));
    }

}
