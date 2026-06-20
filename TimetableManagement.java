import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class TimetableManagement extends JFrame implements ActionListener {

    JTextField classField, dayField, periodField, subjectField, facultyField, roomField;

    JButton addBtn, viewBtn, clearBtn;

    JTable table;
    DefaultTableModel model;

    Connection con;

    TimetableManagement() {

        setTitle("Timetable Management System");
        setSize(750, 500);
        setLayout(null);

        JLabel title = new JLabel("Timetable Management System");
        title.setBounds(250, 10, 250, 30);
        add(title);

        JLabel l1 = new JLabel("Class");
        l1.setBounds(30, 60, 100, 25);
        add(l1);

        classField = new JTextField();
        classField.setBounds(120, 60, 150, 25);
        add(classField);

        JLabel l2 = new JLabel("Day");
        l2.setBounds(30, 100, 100, 25);
        add(l2);

        dayField = new JTextField();
        dayField.setBounds(120, 100, 150, 25);
        add(dayField);

        JLabel l3 = new JLabel("Period");
        l3.setBounds(30, 140, 100, 25);
        add(l3);

        periodField = new JTextField();
        periodField.setBounds(120, 140, 150, 25);
        add(periodField);

        JLabel l4 = new JLabel("Subject");
        l4.setBounds(30, 180, 100, 25);
        add(l4);

        subjectField = new JTextField();
        subjectField.setBounds(120, 180, 150, 25);
        add(subjectField);

        JLabel l5 = new JLabel("Faculty");
        l5.setBounds(30, 220, 100, 25);
        add(l5);

        facultyField = new JTextField();
        facultyField.setBounds(120, 220, 150, 25);
        add(facultyField);

        JLabel l6 = new JLabel("Room");
        l6.setBounds(30, 260, 100, 25);
        add(l6);

        roomField = new JTextField();
        roomField.setBounds(120, 260, 150, 25);
        add(roomField);

        addBtn = new JButton("Add");
        addBtn.setBounds(30, 320, 80, 30);
        add(addBtn);

        viewBtn = new JButton("View");
        viewBtn.setBounds(120, 320, 80, 30);
        add(viewBtn);

        clearBtn = new JButton("Clear");
        clearBtn.setBounds(210, 320, 80, 30);
        add(clearBtn);

        addBtn.addActionListener(this);
        viewBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Class");
        model.addColumn("Day");
        model.addColumn("Period");
        model.addColumn("Subject");
        model.addColumn("Faculty");
        model.addColumn("Room");

        table = new JTable(model);

        JScrollPane pane = new JScrollPane(table);

        pane.setBounds(300, 60, 420, 300);

        add(pane);

        connectDB();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void connectDB() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/timetable_system",
                    "root",
                    "aparna@cse");

            System.out.println("Database Connected");

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addBtn) {

            try {

                if (classField.getText().isEmpty() ||
                        dayField.getText().isEmpty() ||
                        periodField.getText().isEmpty() ||
                        subjectField.getText().isEmpty() ||
                        facultyField.getText().isEmpty() ||
                        roomField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(this,
                            "Fill all fields");

                    return;
                }

                String sql =
                        "INSERT INTO timetable(class_name,day,period,subject,faculty,room) VALUES (?,?,?,?,?,?)";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setString(1, classField.getText());

                pst.setString(2, dayField.getText());

                pst.setInt(3,
                        Integer.parseInt(periodField.getText()));

                pst.setString(4, subjectField.getText());

                pst.setString(5, facultyField.getText());

                pst.setString(6, roomField.getText());

                int rows = pst.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(this,
                            "Record Added Successfully");

                }

                classField.setText("");
                dayField.setText("");
                periodField.setText("");
                subjectField.setText("");
                facultyField.setText("");
                roomField.setText("");

            }

            catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(this,
                        ex.getMessage());

            }

        }

        if (e.getSource() == viewBtn) {

            try {

                model.setRowCount(0);

                Statement st =
                        con.createStatement();

                ResultSet rs =
                        st.executeQuery(
                                "SELECT * FROM timetable");

                while (rs.next()) {

                    model.addRow(new Object[]{

                            rs.getInt("id"),

                            rs.getString("class_name"),

                            rs.getString("day"),

                            rs.getInt("period"),

                            rs.getString("subject"),

                            rs.getString("faculty"),

                            rs.getString("room")

                    });

                }

            }

            catch (Exception ex) {

                ex.printStackTrace();

            }

        }

        if (e.getSource() == clearBtn) {

            classField.setText("");

            dayField.setText("");

            periodField.setText("");

            subjectField.setText("");

            facultyField.setText("");

            roomField.setText("");

        }

    }

    public static void main(String[] args) {

        new TimetableManagement();

    }

}