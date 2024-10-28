import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationForm extends JDialog {
    private JTextField tfName;
    private JTextField tfAdress;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JPasswordField pfPassword;
    private JTextField pfComfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel RegisterPanel;

    RegistrationForm(JFrame frame) {
        super(frame);
        setTitle("Create New Account");
        setContentPane(RegisterPanel);
        setModal(true);
        setLocationRelativeTo(frame);
        setMaximumSize(new Dimension(450,474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }

        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    private void registerUser() {
String name = tfName.getText();
String adress = tfAdress.getText();
String email = tfEmail.getText();
String phone = tfPhone.getText();
String password = String.valueOf(pfPassword.getPassword());
String comfirmPassword = pfComfirmPassword.getText();

if (name.isEmpty() || adress.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
    JOptionPane.showMessageDialog(null, "Please fill all the fields"
            ,"Fill All Fields ",JOptionPane.WARNING_MESSAGE);
    return;
}
if (comfirmPassword.isEmpty() || !comfirmPassword.equals(password)) {
    JOptionPane.showMessageDialog(null,"Password and confirm password do not match"
            ,"Try Again",JOptionPane.WARNING_MESSAGE);
    return;
}
       user= addUserToDataBase(name,email,adress,phone,password);
if (user != null) {
    dispose();
}
else {
    JOptionPane.showMessageDialog(null,"Failed To Create New Account","Try Again"
            ,JOptionPane.INFORMATION_MESSAGE);

}
    }

  public User user;
    private User addUserToDataBase(String name, String email, String adress, String phone, String password) {
        User user=null;
        final String Db_Url="jdbc:mysql://localhost:3306/registerusers";
        String User="root";
        final String Password="Moneygram34";
        try{
            Connection connection= DriverManager.getConnection(Db_Url,User,Password);

            Statement statement=connection.createStatement();
            String sql="INSERT INTO users(name,email,phone,adress,password)" +
                    "Values(?,?,?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,adress);
            preparedStatement.setString(4,phone);
            preparedStatement.setString(5,password);

            int AddedRows= preparedStatement.executeUpdate();

            if(AddedRows>0){
                 user= new User();
                 user.name=name;
                 user.email=email;
                 user.phone=phone;
                 user.adress=adress;
                 user.password=password;
            }

            preparedStatement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }

    public static void main(String[] args) {
        RegistrationForm form = new RegistrationForm(null);
        User user = form.user;
        if (user != null) {
            System.out.println("Successful Registration of : "+ user.name);
        }
        else {
            System.out.println("Registration Canceled");
        }
    }
    }

