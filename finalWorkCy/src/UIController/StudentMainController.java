package UIController;

import Entry.Main;
import Model.Chosen;
import Model.Course;
import Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class StudentMainController extends MasterController{

	JButton button_score_view;
	JButton button_course_manager;
	JButton button_time_table_view;
	JPanel panel_center;
	JPanel panel_east;

	JButton button_chose_course;
	JButton button_dischose_course;
	JTable table;

	public StudentMainController() {
		super(1200, 600, "教务系统 - 学生端");

	}

	public void on_course_manager_click() {
		JPanel table_panel = new JPanel();
		table_panel.setLayout(new BorderLayout());

		Object[][] cellData = new Object[Main.ModelCourse.getAllObjectsFromList().size()][11];
		Iterator<Object> iterator = Main.ModelCourse.getAllObjectsFromList().iterator();
		int i = 0;
		while(iterator.hasNext()) {
			Course tempCourse = (Course)iterator.next();
			if(tempCourse == null) continue;

			cellData[i][0] = tempCourse.id;
			cellData[i][1] = tempCourse.hasChosen() ? "已选" : "未选";
			cellData[i][2] = tempCourse.course_code;
			cellData[i][3] = tempCourse.course_name;
			cellData[i][4] = tempCourse.course_hour;
			cellData[i][5] = tempCourse.course_grade;
			cellData[i][6] = tempCourse.course_time;
			cellData[i][7] = tempCourse.course_room;
			cellData[i][8] = tempCourse.member_count;
			cellData[i][9] = tempCourse.getChosenNumber();
			cellData[i][10] = tempCourse.which_year;
			i++;
		}

		Object[][] newCellData = new Object[i][11];
		System.arraycopy(cellData, 0, newCellData, 0, i);

		String[] columnNames = {"id", "选课状况", "课程代码", "课程名称", "课时数", "学分", "上课时间", "上课教室", "上课人数", "已选人数", "开课学年"};
		table = new JTable(newCellData, columnNames);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane panel_scroll = new JScrollPane(table);
		panel_scroll.setPreferredSize(new Dimension(900, 500));
		panel_center.add(panel_scroll);
		panel_center.add(new JLabel("单击表项后即可按右边的按钮进行选课等操作."));

		//编辑事件
		DefaultTableModel model = new DefaultTableModel(newCellData, columnNames)
		{
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		    	return false;
		    }
		};

		table.setModel(model);

		panel_east.add(button_chose_course = new JButton("选课"));
		button_chose_course.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		panel_center.removeAll();
		panel_east.removeAll();

		if(e.getSource() == button_chose_course) this.on_course_chose_click();

		if(e.getSource() == button_course_manager || e.getSource() == button_chose_course) {
			this.on_course_manager_click();
		}

		if(e.getSource() == button_dischose_course) this.on_course_dischose_click();

		if(e.getSource() == button_time_table_view || e.getSource() == button_dischose_course) {
			this.on_time_table_click();
		}

		if(e.getSource() == button_score_view) this.on_course_score_click();

		panel_east.repaint();
		panel_center.repaint();

		//fixme | solve the problem of cannot refresh
		this.setSize(this.getWidth() + 1, this.getHeight() + 1);
		this.setSize(this.getWidth() - 1, this.getHeight() - 1);
	}

	public void on_course_dischose_click() {
		
		if(table != null) {
			if(table.getSelectedRow() == -1) {
				this.showDialog("请选择您要退选的表项。");
				return;
			}

			try {
				int id = (int) table.getModel().getValueAt(table.getSelectedRow(), 0);

				HashMap<String, String> queryCondition = new HashMap<>();
	    		queryCondition.put("id", String.valueOf(id));
	    		Chosen tempChosen = (Chosen) Main.ModelChosen.selectSingleObjectFromList(queryCondition);
	    		Course tempCourse = tempChosen.course();


		      	if(!tempCourse.hasChosen()) {
		      		this.showDialog("尚未选此门课！退选失败。");
					return;
		      	}

	      	    Main.ModelChosen.deleteObjectFromList(tempChosen);

	      	    this.showDialog("退选成功！");
			} catch(Exception e) {
				this.showDialog("发生了一个错误，请检查您的操作！");
			}

		}
	}

	public void on_time_table_click() {
		
		JPanel table_panel = new JPanel();
		table_panel.setLayout(new BorderLayout());


		HashMap<String, String> queryCondition = new HashMap<>();
		queryCondition.put("student_user_id", String.valueOf(((User)Main.SessionHold.get("CurrentLoginUser")).id));

		Object[][] cellData = new Object[Main.ModelChosen.selectManyObjectsFromList(queryCondition).size()][8];
		Iterator<Object> iterator = Main.ModelChosen.selectManyObjectsFromList(queryCondition).iterator();

		int i = 0;
		while(iterator.hasNext()) {
			Chosen tempChosen = (Chosen)iterator.next();

			if(tempChosen == null) continue;

			Course tempCourse = tempChosen.course();
			if(tempCourse == null) continue;

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			if(!(tempCourse.which_year >= year && year + 1 > tempCourse.which_year)) {
				continue;
			}

			cellData[i][0] = tempChosen.id;
			cellData[i][1] = tempCourse.course_code;
			cellData[i][2] = tempCourse.course_name;
			cellData[i][3] = tempCourse.course_hour;
			cellData[i][4] = tempCourse.course_grade;
			cellData[i][5] = tempCourse.course_time;
			cellData[i][6] = tempCourse.course_room;
			cellData[i][7] = tempCourse.member_count;
			i++;
		}

		Object[][] newCellData = new Object[i][8];
		System.arraycopy(cellData, 0, newCellData, 0, i);

		String[] columnNames = {"选课记录id", "课程代码", "课程名称", "课时数", "学分", "上课时间", "上课教室", "上课人数"};
		table = new JTable(newCellData, columnNames);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane panel_scroll = new JScrollPane(table);
		panel_scroll.setPreferredSize(new Dimension(900, 500));
		panel_center.add(panel_scroll);

		// 编辑事件
		DefaultTableModel model = new DefaultTableModel(newCellData, columnNames)
		{
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		    	return false;
		    }
		};

		table.setModel(model);

		panel_east.add(button_dischose_course = new JButton("退选"));
		button_dischose_course.addActionListener(this);
	}
	
	public void on_course_score_click() {
		JPanel table_panel = new JPanel();
		table_panel.setLayout(new BorderLayout());


		HashMap<String, String> queryCondition = new HashMap<>();
		queryCondition.put("student_user_id", String.valueOf(((User)Main.SessionHold.get("CurrentLoginUser")).id));
		
		Object[][] cellData = new Object[Main.ModelChosen.selectManyObjectsFromList(queryCondition).size()][10];
		Iterator<Object> iterator = Main.ModelChosen.selectManyObjectsFromList(queryCondition).iterator();

		int i = 0;
		while(iterator.hasNext()) {
			Chosen tempChosen = (Chosen)iterator.next();

			if(tempChosen == null) {
				continue;
			}

			Course tempCourse = tempChosen.course();
			if(tempCourse == null) {
				continue;
			}

			User tempUser = tempChosen.user();
			if(tempUser == null) {
				continue;
			}

			if(tempChosen.course_score == -1) {
				continue;
			}

			cellData[i][0] = tempChosen.id;
			cellData[i][1] = tempCourse.course_code;
			cellData[i][2] = tempCourse.course_name;
			cellData[i][3] = tempCourse.course_hour;
			cellData[i][4] = tempCourse.course_grade;
			cellData[i][5] = tempCourse.course_time;
			cellData[i][6] = tempCourse.course_room;
			cellData[i][7] = tempCourse.member_count;
			cellData[i][8] = tempChosen.course_score;
			cellData[i][9] = String.format("%.2f", (tempChosen.course_score < 60 ? 0 : (tempChosen.course_score - 60) / 10 + 1));
			i++;
		}

		Object[][] newCellData = new Object[i][10];
		System.arraycopy(cellData, 0, newCellData, 0, i);

		String[] columnNames = {"选课记录id", "课程代码", "课程名称", "课时数", "学分", "上课时间", "上课教室", "上课人数", "分数", "绩点"};
		table = new JTable(newCellData, columnNames);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane panel_scroll = new JScrollPane(table);
		panel_scroll.setPreferredSize(new Dimension(900, 500));
		panel_center.add(panel_scroll);

		// 编辑事件
		DefaultTableModel model = new DefaultTableModel(newCellData, columnNames)
		{
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		    	return false;
		    }
		};

		table.setModel(model);

	}

	public void on_course_chose_click() {
		
		if(table != null) {
			if(table.getSelectedRow() == -1) {
				this.showDialog("请选择您要选课的表项。");
				return;
			}

			int id = (int) table.getModel().getValueAt(table.getSelectedRow(), 0);

			HashMap<String, String> queryCondition = new HashMap<>();
    		queryCondition.put("id", String.valueOf(id));
      	    Course tempCourse = (Course) Main.ModelCourse.selectSingleObjectFromList(queryCondition);

	      	if(tempCourse.getChosenNumber() + 1 > tempCourse.member_count) {
	      		this.showDialog("人数超限，选课失败。");
				return;
	      	}

	      	if(tempCourse.hasChosen()) {
	      		this.showDialog("已选过此门课，选课失败。");
				return;
	      	}


      	    Chosen tempChosen = new Chosen(tempCourse.id, ((User)Main.SessionHold.get("CurrentLoginUser")).id, -1);

      	    Main.ModelChosen.addObjectToList(tempChosen);

      	    this.showDialog("选课成功！");

		}
	}

	@Override
	public void addToPane() {
		

		pane.setLayout(new BorderLayout());
		pane.add("North", new JLabel("欢迎登录：学生 "+ ((User)Main.SessionHold.get("CurrentLoginUser")).user_name));


		JPanel Row1 = new JPanel();
		Row1.setLayout(new FlowLayout());
		Row1.add(button_course_manager = new JButton("课程查看与选课"));
		Row1.add(button_score_view = new JButton("我的成绩单"));
		Row1.add(button_time_table_view = new JButton("我的课表"));

		button_course_manager.addActionListener(this);
		button_score_view.addActionListener(this);
		button_time_table_view.addActionListener(this);

		Row1.setLayout(new GridLayout(5, 1));
		pane.add("West", Row1);

		panel_center = new JPanel();
		panel_center.add(new JLabel("欢迎您！请点击左边的列按钮进行相关操作。"));
		pane.add("Center", panel_center);

		panel_east = new JPanel();
		panel_east.setLayout(new GridLayout(5,1));
		pane.add("East", panel_east);
	}

}
