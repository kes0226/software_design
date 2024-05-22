import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Challenge extends JFrame {

    private ModalDialog modalDialog;

    public JPanel show_Challenge(Frame menu) {
        JPanel jp = new JPanel(); // 새로운 JPanel 인스턴스를 생성합니다.
        jp.setLayout(null);
        jp.setSize(614, 610);
        jp.setLocation(0, 40);

        int challenge = 0;
        String food_p = "";
        String name = "", path = "", ingredient = "", recipe = "";

        //id.txt에서 challenge 음식 인덱스 알아내기
        try {
            String content = new String(Files.readAllBytes(Paths.get(Login.getID() + ".txt")));
            String[] parts = content.split("/");
            challenge = Integer.parseInt(parts[4]);
            food_p = parts[5];
        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        //food_info.txt에서 음식이름, 사진경로 알아내기
        try {
            String content = new String(Files.readAllBytes(Paths.get("food/food_info.txt")));
            String[] food_s = content.split("~");
            String[] food = food_s[challenge].split("/");
            name = food[1];
            ingredient = food[2];
            path = food[3];  //음식사진 이름이 담겨 있는걸로 수정
        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        //recipe.txt에서 레시피 가져오기
        try {
            String content = new String(Files.readAllBytes(Paths.get("food/recipe.txt")));
            String[] recipe_s = content.split("/");
            recipe = recipe_s[challenge];

        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        //챌린지 제목, 음식 종류, 난이도
        JLabel food = new JLabel(name+"("+food_p+")", JLabel.CENTER);
        food.setLocation(150, 50);
        food.setSize(300, 50);
        food.setFont(new Font("NanumGothic", Font.BOLD, 24));
        jp.add(food);
        //챌린지 이미지
        ImageIcon imageIcon = new ImageIcon("food/food_pic/" + path);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setLocation(132, 115);
        imageLabel.setSize(350, 300);
        jp.add(imageLabel);

        //챌린지 시작버튼
        //->챌린지 시작 클래스로 이동
        JButton start = new JButton("Challenge Start!");
        start.setLocation(132, 450);
        start.setSize(150, 40);
        jp.add(start);

        final String finalName = name;
        final String finalPath = path;
        final String finalIngre = ingredient;
        final String finalRecipe = recipe;
        final String finalFood_p = food_p;

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.dispose();  //menu지우기
                Challenge_Start start = new Challenge_Start(finalName, finalPath, finalIngre, finalRecipe, finalFood_p);
                start.show_Challenge();
            }
        });

        //다른 챌린지 고르기
        JButton other = new JButton("다른 챌린지");
        other.setLocation(300, 450);
        other.setSize(150, 40);
        jp.add(other);
        other.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (modalDialog != null) {
                    modalDialog.dispose();
                }
                else{
                    // 새로운 모달 다이얼로그 생성
                    modalDialog =new ModalDialog(menu, true);
                    modalDialog.setVisible(true);
                }
                dispose();
            }
        });

        return jp; // 새로운 JPanel 인스턴스를 반환합니다.
    }

}