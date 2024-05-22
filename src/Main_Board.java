import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Main_Board extends JFrame {
    String id;

    Main_Board(String id) {
        this.id = id;
    }

    public JPanel show_Main_Board() {

        JPanel jp = new JPanel(); // 새로운 JPanel 인스턴스를 생성합니다.
        jp.setSize(614, 610);
        jp.setLocation(0, 40);

        JLabel main = new JLabel("main" + id);
        main.setLocation(250, 50);
        main.setSize(500, 50);
        main.setFont(new Font("Arial", Font.BOLD, 24));

        jp.add(main);
        return jp; // 새로운 JPanel 인스턴스를 반환합니다.
    }
}