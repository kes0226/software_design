import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

//id와 password를 파일에서 확인하고 맞는 사용자를 찾음
//로그인 끝난 후에는 메인화면으로 (Board의 사용자id객체 생성)
//register할 떄에는 register화면으로
public class Login extends JFrame {
    private static final Logger logger = Logger.getLogger(Login.class.getName());

    private static String id;

    public void Login_show() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setLayout(null);

        JLabel la = new JLabel("Login");
        la.setLocation(250, 50);
        la.setSize(100, 50);
        la.setFont(new Font("Arial", Font.BOLD, 24));
        cp.add(la);

        final JLabel member_id = new JLabel("id");
        member_id.setLocation(150, 130);
        member_id.setSize(50, 25);
        member_id.setFont(new Font("Arial", Font.BOLD, 18));
        cp.add(member_id);

        JTextField id_text = new JTextField("");
        id_text.setLocation(250, 130);
        id_text.setSize(150, 25);
        cp.add(id_text);

        JLabel ps = new JLabel("password");
        ps.setLocation(150, 180);
        ps.setSize(90, 25);
        ps.setFont(new Font("Arial", Font.BOLD, 18));
        cp.add(ps);

        JTextField p_text = new JTextField("");
        p_text.setLocation(250, 180);
        p_text.setSize(150, 25);
        cp.add(p_text);

        JButton login = new JButton("login");
        login.setLocation(245, 240);
        login.setSize(100, 30);
        cp.add(login);

        JButton register = new JButton("register");
        register.setLocation(245, 290);
        register.setSize(100, 30);
        cp.add(register);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = id_text.getText();
                String password = p_text.getText();


                if (checkInfo(id, password)) {
                    JOptionPane.showMessageDialog(null, "Login Successful! Member ID: " + id);
                    dispose();
                    Menu menu = new Menu();
                    menu.Menu_show();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid ID or Password!");
                }
            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  //창을 닫음
                Register re = new Register();
                re.Register_show();
            }
        });

        setSize(600, 450);
        setVisible(true);
    }

    private boolean checkInfo(String id, String password) {
        // member.txt 파일에서 해당 ID와 Password가 일치하는지 확인
        try {
            String content = new String(Files.readAllBytes(Paths.get(id+".txt")));
            String[] parts = content.split("/");
            if (parts[0].equals(id) && parts[1].equals(password))
                return true;

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while reading file", e);
        }
        return false;
    }

    public static String getID(){
        return id;
    }
}
