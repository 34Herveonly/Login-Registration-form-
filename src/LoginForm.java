import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField textField1;
    private JPasswordField passwordField;
    private JButton btnLogin;
    private JButton BtnCancel;
    private JPanel LoginPanel;

    public  LoginForm(JFrame frame) {
        super(frame);
        setTitle("Login Page");
        setContentPane(LoginPanel);
        setModal(true);
        setLocationRelativeTo(frame);
        setMinimumSize(new Dimension(450, 474));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = textField1.getText();
                String password = String.valueOf(passwordField.getPassword());

                try {
                    user=getAuthentication(email,password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (user !=null){
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password","Check Again",JOptionPane.ERROR_MESSAGE);
                }

            }

        });
        BtnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });

        setVisible(true);

    }

    public User user;
    private User getAuthentication(String email,String password) throws SQLException {
        User user = null;

        final String Db_url="jdbc:mysql://localhost:3306/registerusers";
        final String Db_user="root";
        final String Db_password="Moneygram34";

        try{
            Connection connection  = DriverManager.getConnection(Db_url,Db_user,Db_password);

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = new User();
                user.name=resultSet.getString("name");
                user.email=resultSet.getString("email");
                user.phone=resultSet.getString("phone");
                user.adress=resultSet.getString("adress");
                user.password=resultSet.getString("password");
            }

            statement.close();
            connection.close();


        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return user;

    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);

        User user = loginForm.user;
        if (user != null) {
            System.out.println("Successful Authentication Of: "+ user.name);
            System.out.println("     EMAIL:"+user.email);
            System.out.println("     PHONE:"+user.phone);
            System.out.println("     Adress:"+user.adress);
        }
        else {
            System.out.println("Authentication Cancelled");
        }
    }

}

