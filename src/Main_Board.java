import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main_Board extends JFrame {

    private String foodCaptin, food, image = " ";

    public JPanel show_Main_Board(Frame menu) {

        JPanel jp = new JPanel(); // 새로운 JPanel 인스턴스를 생성합니다.
        jp.setLayout(null);
        jp.setSize(614, 610);
        jp.setLocation(0, 40);

        getFoodCaptin();

        JLabel main = new JLabel("요리대장: "+foodCaptin);
        main.setLocation(132, 10);
        main.setSize(500, 50);
        main.setFont(new Font("NanumGothic", Font.BOLD, 24));
        jp.add(main);

        JLabel name = new JLabel("음식: "+food);
        name.setLocation(132, 60);
        name.setSize(500, 50);
        name.setFont(new Font("NanumGothic", Font.BOLD, 24));
        jp.add(name);

        ImageIcon imageIcon = new ImageIcon("food/my_food/" + image);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setLocation(132, 110);
        imageLabel.setSize(400, 390);
        jp.add(imageLabel);

        JButton off = new JButton("프로그램 종료");
        off.setLocation(225, 510);
        off.setSize(150, 40);
        jp.add(off);

        off.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "시스템을 종료합니다");
                menu.dispose();  //프로그램 종료
            }
        });

        return jp; // 새로운 JPanel 인스턴스를 반환합니다.
    }

    private void getFoodCaptin(){
        try {
            String content = new String(Files.readAllBytes(Paths.get("administrator.txt")));
            String[] parts = content.split("/");
            foodCaptin = parts[2];
            food = parts[3];
            image = parts[4];

        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }
    }
}