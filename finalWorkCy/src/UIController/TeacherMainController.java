package UIController;

import Entry.Main;
import Model.Chosen;
import Model.Course;
import Model.User;
import UIController.Teacher.AddCourseController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class TeacherMainController extends MasterController {

    JButton button_student_view;
    JButton button_course_manager;
    JButton button_time_table_view;
    JPanel panel_center;
    JPanel panel_east;

    JButton button_add_course;
    JButton button_delete_course;
    JButton button_course_chosen;
    JButton button_course_score;
    JTable table;

    public TeacherMainController() {
        super(1100, 650, "����ϵͳ - ��ʦ��");
    }

    public TeacherMainController(int Action) {
        super(1100, 650, "����ϵͳ - ��ʦ��");

        if (Action == 1) {
            this.on_course_manager_click();
        }
    }

    public void on_course_manager_click() {
        JPanel table_panel = new JPanel();
        table_panel.setLayout(new BorderLayout());

        HashMap<String, String> queryCondition = new HashMap<>();
        queryCondition.put("teacher_id", String.valueOf(((User) Main.SessionHold.get("CurrentLoginUser")).id));

        Object[][] cellData = new Object[Main.ModelCourse.selectManyObjectsFromList(queryCondition).size()][10];
        Iterator<Object> iterator = Main.ModelCourse.selectManyObjectsFromList(queryCondition).iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Course tempCourse = (Course) iterator.next();
            if (tempCourse == null) {
                continue;
            }

            cellData[i][0] = tempCourse.id;
            cellData[i][1] = tempCourse.course_code;
            cellData[i][2] = tempCourse.course_name;
            cellData[i][3] = tempCourse.course_hour;
            cellData[i][4] = tempCourse.course_grade;
            cellData[i][5] = tempCourse.course_time;
            cellData[i][6] = tempCourse.course_room;
            cellData[i][7] = tempCourse.member_count;
            cellData[i][8] = tempCourse.getChosenNumber();
            cellData[i][9] = tempCourse.which_year;
            i++;
        }

        Object[][] newCellData = new Object[i][10];
        System.arraycopy(cellData, 0, newCellData, 0, i);

        String[] columnNames = {"id", "�γ̴���", "�γ�����", "��ʱ��", "ѧ��", "�Ͽ�ʱ��", "�Ͽν���", "�Ͽ�����", "��ѡ����", "����ѧ��"};
        table = new JTable(newCellData, columnNames);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane panel_scroll = new JScrollPane(table);
        panel_scroll.setPreferredSize(new Dimension(800, 500));
        panel_center.add(panel_scroll);
        panel_center.add(new JLabel("˫������ɱ༭��Ӧ����Ϣ��Ȼ�󰴻س������棻���id����Խ���ɾ�����鿴��ѡ��ѧ������ѧ����ֵȲ�����"));

        TeacherMainController ins_obj = this;
        // �༭�¼�
        DefaultTableModel model = new DefaultTableModel(newCellData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 8;
            }
        };

        table.setModel(model);

        table.getModel().addTableModelListener(e -> {
            try {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model1 = (TableModel) e.getSource();
                String columnName = model1.getColumnName(column);
                Object data = model1.getValueAt(row, column);

                if (columnName.equals("id")) {
                    ins_obj.showDialog("id������༭�����α༭��������Ч��");
                }

                int id = (int) model1.getValueAt(row, 0);

                HashMap<String, String> queryCondition1 = new HashMap<>();
                queryCondition1.put("id", String.valueOf(id));
                Course tempCourse = (Course) Main.ModelCourse.selectSingleObjectFromList(queryCondition1);

                if (((String) data).indexOf('|') != -1) {
                    ins_obj.showDialog("���в�֧�ֵ��ַ�|�����޸ģ���");
                    return;
                }

                if (columnName.equals("�γ̴���")) {
                    int index = Main.ModelCourse.getIndex(tempCourse);
                    tempCourse.course_code = (String) data;
                    Main.ModelCourse.updateObjectToList(index, tempCourse);
                    System.out.println("�γ̴���༭�ɹ���");
                }

                if (columnName.equals("�γ�����")) {
                    int index = Main.ModelCourse.getIndex(tempCourse);
                    tempCourse.course_name = (String) data;
                    Main.ModelCourse.updateObjectToList(index, tempCourse);
                    System.out.println("�γ����Ʊ༭�ɹ���");
                }

                if (columnName.equals("��ʱ��")) {
                    int index = Main.ModelCourse.getIndex(tempCourse);
                    tempCourse.course_hour = (String) data;
                    Main.ModelCourse.updateObjectToList(index, tempCourse);
                    System.out.println("��ʱ���༭�ɹ���");
                }

                if (columnName.equals("ѧ��")) {
                    int index = Main.ModelCourse.getIndex(tempCourse);
                    tempCourse.course_grade = Float.parseFloat((String) data);
                    Main.ModelCourse.updateObjectToList(index, tempCourse);
                    System.out.println("ѧ�ֱ༭�ɹ���");
                }

                if (columnName.equals("�Ͽ�ʱ��")) {
                    int index = Main.ModelCourse.getIndex(tempCourse);
                    tempCourse.course_time = (String) data;
                    Main.ModelCourse.updateObjectToList(index, tempCourse);
                    System.out.println("�Ͽ�ʱ��༭�ɹ���");
                }

                if (columnName.equals("�Ͽν���")) {
                    int index = Main.ModelCourse.getIndex(tempCourse);
                    tempCourse.course_room = (String) data;
                    Main.ModelCourse.updateObjectToList(index, tempCourse);
                    System.out.println("�Ͽν��ұ༭�ɹ���");
                }

                if (columnName.equals("�Ͽ�����")) {
                    int index = Main.ModelCourse.getIndex(tempCourse);
                    tempCourse.member_count = Integer.parseInt((String) data);
                    Main.ModelCourse.updateObjectToList(index, tempCourse);
                    System.out.println("�Ͽ������༭�ɹ���");
                }

                if (columnName.equals("����ѧ��")) {
                    int index = Main.ModelCourse.getIndex(tempCourse);
                    tempCourse.which_year = Float.parseFloat((String) data);
                    Main.ModelCourse.updateObjectToList(index, tempCourse);
                    System.out.println("������ݱ༭�ɹ���");
                }
            } catch (Exception e1) {
                ins_obj.showDialog("���������ƺ���������������룡");
            }
        });

        table.setSize(table.getWidth() + 200, table.getHeight());

        panel_east.add(button_add_course = new JButton("��ӿγ�"));
        button_add_course.addActionListener(this);

        panel_east.add(button_delete_course = new JButton("ɾ���γ�"));
        button_delete_course.addActionListener(this);

        //button_course_chosen
        panel_east.add(button_course_chosen = new JButton("�γ���ѡѧ��"));
        button_course_chosen.addActionListener(this);

        panel_east.add(button_course_score = new JButton("�γ̴��"));
        button_course_score.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel_center.removeAll();
        panel_east.removeAll();

        if (e.getSource() == button_add_course) {
            new AddCourseController();
            this.dispose();
        }

        if (e.getSource() == button_delete_course) this.on_course_delete_click();

        if (e.getSource() == button_course_manager || e.getSource() == button_add_course || e.getSource() == button_delete_course) {
            this.on_course_manager_click();
        }

        if (e.getSource() == button_student_view) this.on_student_view_click();

        if (e.getSource() == button_time_table_view) this.on_time_table_click();

        if (e.getSource() == button_course_chosen) this.on_course_chosen_click();

        if (e.getSource() == button_course_score) this.on_course_score_click();

        panel_east.repaint();
        panel_center.repaint();

        //fixme | solve the problem of cannot refresh
        this.setSize(this.getWidth() + 1, this.getHeight() + 1);
        this.setSize(this.getWidth() - 1, this.getHeight() - 1);
    }

    public void on_time_table_click() {
        JPanel table_panel = new JPanel();
        table_panel.setLayout(new BorderLayout());

        HashMap<String, String> queryCondition = new HashMap<>();
        queryCondition.put("teacher_id", String.valueOf(((User) Main.SessionHold.get("CurrentLoginUser")).id));

        Object[][] cellData = new Object[Main.ModelCourse.selectManyObjectsFromList(queryCondition).size()][8];
        Iterator<Object> iterator = Main.ModelCourse.selectManyObjectsFromList(queryCondition).iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Course tempCourse = (Course) iterator.next();
            if (tempCourse == null) {
                continue;
            }

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            if (!(tempCourse.which_year >= year && year + 1 > tempCourse.which_year)) {
                continue;
            }

            cellData[i][0] = tempCourse.id;
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

        String[] columnNames = {"id", "�γ̴���", "�γ�����", "��ʱ��", "ѧ��", "�Ͽ�ʱ��", "�Ͽν���", "�Ͽ�����"};
        table = new JTable(newCellData, columnNames);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane panel_scroll = new JScrollPane(table);
        panel_scroll.setPreferredSize(new Dimension(800, 500));
        panel_center.add(panel_scroll);

        //�༭�¼�
        DefaultTableModel model = new DefaultTableModel(newCellData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(model);
    }

    public void on_course_chosen_click() {
        if (table != null) {
            if (table.getSelectedRow() == -1) {
                this.showDialog("��ѡ����Ҫ�鿴�ı���.");
                this.on_course_manager_click();
                return;
            }

            int course_id = (int) table.getModel().getValueAt(table.getSelectedRow(), 0);

            JPanel table_panel = new JPanel();
            table_panel.setLayout(new BorderLayout());


            HashMap<String, String> queryCondition = new HashMap<>();
            queryCondition.put("course_id", String.valueOf(course_id));

            Object[][] cellData = new Object[Main.ModelChosen.selectManyObjectsFromList(queryCondition).size()][10];
            Iterator<Object> iterator = Main.ModelChosen.selectManyObjectsFromList(queryCondition).iterator();

            int i = 0;
            while (iterator.hasNext()) {
                Chosen tempChosen = (Chosen) iterator.next();

                if (tempChosen == null) {
                    continue;
                }

                Course tempCourse = tempChosen.course();
                if (tempCourse == null) {
                    continue;
                }

                User tempUser = tempChosen.user();
                if (tempUser == null) {
                    continue;
                }

                cellData[i][0] = tempChosen.id;
                cellData[i][1] = tempUser.user_number;
                cellData[i][2] = tempUser.user_name;
                cellData[i][3] = tempCourse.course_code;
                cellData[i][4] = tempCourse.course_name;
                cellData[i][5] = tempCourse.course_hour;
                cellData[i][6] = tempCourse.course_grade;
                cellData[i][7] = tempCourse.course_time;
                cellData[i][8] = tempCourse.course_room;
                cellData[i][9] = tempCourse.member_count;
                i++;
            }

            Object[][] newCellData = new Object[i][10];
            System.arraycopy(cellData, 0, newCellData, 0, i);

            String[] columnNames = {"ѡ�μ�¼id", "ѧ��ѧ��", "ѧ������", "�γ̴���", "�γ�����", "��ʱ��", "ѧ��", "�Ͽ�ʱ��", "�Ͽν���", "�Ͽ�����"};
            table = new JTable(newCellData, columnNames);
            table.setFillsViewportHeight(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            JScrollPane panel_scroll = new JScrollPane(table);
            panel_scroll.setPreferredSize(new Dimension(800, 500));
            panel_center.add(panel_scroll);

            //�༭�¼�
            DefaultTableModel model = new DefaultTableModel(newCellData, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                    
                }
            };

            table.setModel(model);
        }

        

    }

    public void on_course_score_click() {
        if (table != null) {
            if (table.getSelectedRow() == -1) {
                this.showDialog("��ѡ����Ҫ�鿴�ı���.");
                this.on_course_manager_click();
                return;
            }

            int course_id = (int) table.getModel().getValueAt(table.getSelectedRow(), 0);

            JPanel table_panel = new JPanel();
            table_panel.setLayout(new BorderLayout());


            HashMap<String, String> queryCondition = new HashMap<>();
            queryCondition.put("course_id", String.valueOf(course_id));

            Object[][] cellData = new Object[Main.ModelChosen.selectManyObjectsFromList(queryCondition).size()][11];
            Iterator<Object> iterator = Main.ModelChosen.selectManyObjectsFromList(queryCondition).iterator();

            int i = 0;
            while (iterator.hasNext()) {
                Chosen tempChosen = (Chosen) iterator.next();

                if (tempChosen == null) continue;

                Course tempCourse = tempChosen.course();
                if (tempCourse == null) continue;

                User tempUser = tempChosen.user();
                if (tempUser == null) continue;

                cellData[i][0] = tempChosen.id;
                cellData[i][1] = tempUser.user_number;
                cellData[i][2] = tempUser.user_name;
                cellData[i][3] = tempCourse.course_code;
                cellData[i][4] = tempCourse.course_name;
                cellData[i][5] = tempCourse.course_hour;
                cellData[i][6] = tempCourse.course_grade;
                cellData[i][7] = tempCourse.course_time;
                cellData[i][8] = tempCourse.course_room;
                cellData[i][9] = tempCourse.member_count;
                cellData[i][10] = tempChosen.course_score;
                i++;
            }

            Object[][] newCellData = new Object[i][11];
            System.arraycopy(cellData, 0, newCellData, 0, i);

            String[] columnNames = {"ѡ�μ�¼id", "ѧ��ѧ��", "ѧ������", "�γ̴���", "�γ�����", "��ʱ��", "ѧ��", "�Ͽ�ʱ��", "�Ͽν���", "�Ͽ�����", "����"};
            table = new JTable(newCellData, columnNames);
            table.setFillsViewportHeight(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            JScrollPane panel_scroll = new JScrollPane(table);
            panel_scroll.setPreferredSize(new Dimension(800, 500));
            panel_center.add(panel_scroll);
            panel_center.add(new JLabel("˫�������е�����Խ��д�֣�������ɺ󰴻س������ɱ��棡"));

            TeacherMainController ins_obj = this;
            // �༭�¼�
            DefaultTableModel model = new DefaultTableModel(newCellData, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 10;
                }
            };

            table.setModel(model);

            table.getModel().addTableModelListener(e -> {
                try {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    TableModel model1 = (TableModel) e.getSource();
                    String columnName = model1.getColumnName(column);
                    Object data = model1.getValueAt(row, column);

                    if (columnName.equals("id")) {
                        ins_obj.showDialog("id������༭�����α༭��������Ч��");
                    }

                    if (((String) data).indexOf('|') != -1) {
                        ins_obj.showDialog("���в�֧�ֵ��ַ�|�����޸ģ���");
                        return;
                    }

                    int id = (int) model1.getValueAt(row, 0);

                    HashMap<String, String> queryCondition1 = new HashMap<>();
                    queryCondition1.put("id", String.valueOf(id));
                    Chosen tempChosen = (Chosen) Main.ModelChosen.selectSingleObjectFromList(queryCondition1);

                    if (columnName.equals("����")) {
                        int index = Main.ModelChosen.getIndex(tempChosen);
                        tempChosen.course_score = Float.parseFloat((String) data);
                        Main.ModelChosen.updateObjectToList(index, tempChosen);
                        System.out.println("��ֳɹ�");
                    }
                } catch (Exception e1) {
                    ins_obj.showDialog("���������ƺ���������������룡");
                }
            });
        }

        

    }

    public void on_student_view_click() {
        
        JPanel table_panel = new JPanel();
        table_panel.setLayout(new BorderLayout());

        HashMap<String, String> queryCondition = new HashMap<>();
        queryCondition.put("role_type", "2");

        Object[][] cellData = new Object[Main.ModelUser.selectManyObjectsFromList(queryCondition).size()][3];
        Iterator<Object> iterator = Main.ModelUser.selectManyObjectsFromList(queryCondition).iterator();
        int i = 0;
        while (iterator.hasNext()) {
            User tempUser = (User) iterator.next();
            if (tempUser == null) {
                continue;
            }
            cellData[i][0] = tempUser.id;
            cellData[i][1] = tempUser.user_name;
            cellData[i][2] = tempUser.user_number;
            i++;
        }

        String[] columnNames = {"id", "�û���", "ѧ��"};
        table = new JTable(cellData, columnNames);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane panel_scroll = new JScrollPane(table);
        panel_scroll.setPreferredSize(new Dimension(800, 500));
        panel_center.add(panel_scroll);

        //�༭�¼�
        DefaultTableModel model = new DefaultTableModel(cellData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
                
            }
        };

        table.setModel(model);
    }

    public void on_course_delete_click() {
        
        if (table != null) {
            if (table.getSelectedRow() == -1) {
                this.showDialog("��ѡ����Ҫɾ���ı��");
                return;
            }

            int id = (int) table.getModel().getValueAt(table.getSelectedRow(), 0);

            HashMap<String, String> queryCondition = new HashMap<>();
            queryCondition.put("id", String.valueOf(id));
            Course tempCourse = (Course) Main.ModelCourse.selectSingleObjectFromList(queryCondition);

            Main.ModelCourse.deleteObjectFromList(tempCourse);
        }
    }

    @Override
    public void addToPane() {
        

        pane.setLayout(new BorderLayout());
        pane.add("North", new JLabel("��ӭ��¼����ʦ " + ((User) Main.SessionHold.get("CurrentLoginUser")).user_name));


        JPanel Row1 = new JPanel();
        Row1.setLayout(new FlowLayout());
        Row1.add(button_course_manager = new JButton("�γ̹���"));
        Row1.add(button_student_view = new JButton("ѧ���鿴"));
        Row1.add(button_time_table_view = new JButton("��ʦ�α�"));

        button_course_manager.addActionListener(this);
        button_student_view.addActionListener(this);
        button_time_table_view.addActionListener(this);

        Row1.setLayout(new GridLayout(5, 1));
        pane.add("West", Row1);

        panel_center = new JPanel();
        panel_center.add(new JLabel("��ӭ����������ߵ��а�ť������ز�����"));
        pane.add("Center", panel_center);

        panel_east = new JPanel();
        panel_east.setLayout(new GridLayout(5, 1));
        pane.add("East", panel_east);
    }

}
