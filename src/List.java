import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class List extends JFrame {

    private String[][] dataArray;
    private JPanel panel;
    private String[] food_name;


    public JPanel show_List() {

        JPanel jp = new JPanel();
        jp.setLayout(null);
        jp.setSize(614, 610);
        jp.setLocation(0, 40);

        get_list(); //list정보 가져옴

        //JList생성
        JList<String> foodlist = new JList<>(food_name);
        JScrollPane scrollPane = new JScrollPane(foodlist); // JScrollPane을 사용하여 JList를 감싸기
        // JList의 위치와 크기 설정
        scrollPane.setBounds(5, 40, 100, 500); // 좌표와 크기 설정
        //scrollPane.setPreferredSize(new Dimension(width, height)); // 선호 크기 설정
        Font listFont = new Font("NanumGothic", Font.PLAIN, 12);
        foodlist.setFont(listFont);

        //음식 사진정보를 보여줄 panel생성
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(500, 500);
        panel.setLocation(110, 40);

        //초기 panel :챌린지 제목, 음식 종류, 난이도
        JLabel food = new JLabel("List", JLabel.CENTER);
        food.setLocation(100, 5);
        food.setSize(300, 50);
        food.setFont(new Font("NanumGothic", Font.BOLD, 24));
        panel.add(food);
        jp.add(panel);


        foodlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String food = foodlist.getSelectedValue();
                    String[] f_list = food.split(",");

                    jp.remove(panel); // 기존의 panel을 제거
                    panel = View_food(f_list[0], f_list[1]); // 새로운 panel로 대체
                    jp.add(panel); // 새로운 panel 추가

                    jp.revalidate(); // 레이아웃을 새로고침
                    jp.repaint(); // 화면을 다시 그림
                }
            }
        });
        jp.add(scrollPane);

        return jp; // 새로운 JPanel 인스턴스를 반환합니다.
    }

    private void get_list(){

        int length = 0;
        String[] foods = {""};
        try {
            String content = new String(Files.readAllBytes(Paths.get(Login.getID() + "_list.txt")));
            foods = content.split("\n");
            length = foods.length;

        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        dataArray = new String[length][];
        for (int i = 0; i < length; i++) {
            dataArray[i] = foods[i].split("/");
        }
        food_name = new String[length];
        for(int i = 0; i < length; i++) {
            food_name[i] = String.format("%d,%s", i, dataArray[i][0]);
        }
    }

    private JPanel View_food(String num, String name) {
        JPanel view = new JPanel();
        view.setLayout(null);
        view.setSize(500, 500);
        view.setLocation(110, 40);

        int f_num = Integer.parseInt(num);

        //챌린지 제목, 음식 종류, 난이도
        JLabel food = new JLabel(name+"("+dataArray[f_num][1]+")", JLabel.CENTER);
        food.setLocation(100, 5);
        food.setSize(300, 50);
        food.setFont(new Font("NanumGothic", Font.BOLD, 24));
        view.add(food);
        //챌린지 이미지
        ImageIcon imageIcon = new ImageIcon("food/my_food/" + dataArray[f_num][3]);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setLocation(50, 60);
        imageLabel.setSize(400, 390);
        view.add(imageLabel);
        System.out.println("food/my_food/" + dataArray[f_num][3]);

        //날짜
        JLabel date = new JLabel(dataArray[f_num][2]);
        date.setLocation(55, 450);
        date.setSize(300, 50);
        date.setFont(new Font("NanumGothic", Font.BOLD, 20));
        view.add(date);

        return view;
    }


}
