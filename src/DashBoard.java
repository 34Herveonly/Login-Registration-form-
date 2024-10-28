import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DashBoard extends JFrame {
    private JPanel dashboardPane;
    private JLabel lbAdmin;
    private JButton btnregister;


    private DashBoard() throws SQLException {
        setTitle("DashBoard");
        setContentPane(dashboardPane);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        boolean hasRegisteredUser = connectedToDatabase();

        if(hasRegisteredUser){

                LoginForm loginForm= new LoginForm(this);
                User user=loginForm.user;

                if(user!=null){
                    lbAdmin.setText("User : "+ user.name);
                    setLocationRelativeTo(null);
                    setVisible(true);

                }
                else {
                    dispose();
                }
        }
else {
           RegistrationForm registrationForm = new RegistrationForm(this);
           User user=registrationForm.user;
           if(user!=null){
               lbAdmin.setText("User : "+ user.name);
               setLocationRelativeTo(null);
               setVisible(true);
           }
else {
    dispose();
           }
        }
        btnregister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm= new RegistrationForm(DashBoard.this);
                User user=registrationForm.user;
                if(user!=null){
                    JOptionPane.showMessageDialog(DashBoard.this, user.name + " has been registered successfully"
                            , "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private boolean connectedToDatabase() throws SQLException {
        boolean hasRegisteredUser = false;

        final String MySql_url="jdbc:mysql://localhost:3306/registerusers";
        final String MySql_user="root";
        final String MySql_password="Moneygram34";

        try {
            Connection connection = DriverManager.getConnection(MySql_url, MySql_user, MySql_password);
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "ID INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(50) NOT NULL,"
                    + "email VARCHAR(50) NOT NULL UNIQUE,"
                    + "phone VARCHAR(50) NOT NULL,"
                    + "adress VARCHAR(50) NOT NULL,"
                    + "password VARCHAR(50) NOT NULL)";

                    statement.executeUpdate(sql);

             statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");
             if(resultSet.next()){
                 int count=resultSet.getInt(1);
                 if(count>0){
                     hasRegisteredUser=true;

                 }
             }
             statement.close();
             connection.close();



            statement.close();
            connection.close();


            connection = DriverManager.getConnection(MySql_url, MySql_user, MySql_password);
            statement = connection.createStatement();

            String Sql = "CREATE TABLE IF NOT EXIST * FROM registerusers";


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return hasRegisteredUser;


    }

    public static void main(String[] args) {
        try {
            DashBoard dashBoard = new DashBoard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
