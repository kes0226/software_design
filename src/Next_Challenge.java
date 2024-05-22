import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Next_Challenge extends JFrame {

    private ModalDialog modalDialog;

    //모달 다이얼로그의 챌린지 결정화면
    Next_Challenge(int nextchallenge, String ability, String food_c, boolean failure) {
        //모달다이얼로그로부터 id와 음식번호(food)를 받아서
        //food_info를 열어서 food에 해당하는 다음 챌린지 음식이름, 음식사진 가져오고 화면에 표시
        //챌린지 다시 정하기는 모달다이얼로그
        //챌린지 결정은 id.txt열어서 결정한 챌린지음식번호 적고 챌린지성공횟수 1 증가

        //다이얼로그에서 고른 챌린지를 띄워줌
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //전체화면 frame 만들기
        setSize(614, 650);

        Container cp = getContentPane();
        cp.setLayout(null);

        setVisible(true);

        //고른 챌린지의 food_info를 찾아 음식이름, 사진을 가져옴
        String name = "", path = "";
        try {
            String content = new String(Files.readAllBytes(Paths.get("food/food_info.txt")));
            String[] food_s = content.split("~");
            String[] food = food_s[nextchallenge].split("/");
            name = food[1];
            path = food[3];
        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        //챌린지 제목, 음식 종류, 난이도
        JLabel food = new JLabel(name + "(" + ability + food_c + ")", JLabel.CENTER);
        food.setLocation(150, 50);
        food.setSize(300, 50);
        food.setFont(new Font("NanumGothic", Font.BOLD, 24));
        cp.add(food);
        //챌린지 이미지
        ImageIcon imageIcon = new ImageIcon("food/food_pic/" + path);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setLocation(132, 115);
        imageLabel.setSize(350, 300);
        cp.add(imageLabel);

        JButton retry = new JButton("다시 정하기");
        retry.setLocation(132, 450);
        retry.setSize(150, 40);
        cp.add(retry);

        retry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modalDialog != null) {
                    modalDialog.dispose();
                } else {
                    // 새로운 모달 다이얼로그 생성
                    modalDialog = new ModalDialog(Next_Challenge.this, failure);
                    modalDialog.setVisible(true);
                }
                dispose();
            }
        });


        JButton decide = new JButton("결정");
        decide.setLocation(300, 450);
        decide.setSize(150, 40);
        cp.add(decide);

        //챌린지 결정은 id.txt열어서 결정한 챌린지음식번호 적고 챌린지성공횟수 1 증가
        decide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu();
                menu.Menu_show();

                if(failure){
                    //원래 정보에 챌린지 정보만 바꿔서 다시 적기
                    String id = Login.getID();
                    String passwordContent = "";
                    int success = 0;
                    String friends = "";
                    //원래 id.txt의 정보들 가져오기
                    try {
                        String content = new String(Files.readAllBytes(Paths.get(id+".txt")));
                        String[] part = content.split("/");
                        passwordContent = part[1];
                        success = Integer.parseInt(part[3]);
                        friends = part[2];
                    } catch (IOException ex) {
                        System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
                    }
                    //바뀐 챌린지 정보와 원래 정보 다시 쓰기
                    try (FileWriter writer = new FileWriter(id + ".txt")) {
                        writer.write( id + "/" + passwordContent + "/" + friends +"/"+success+"/" + nextchallenge +"/"+ ability+food_c);
                        System.out.println("ID와 Password를 성공적으로 등록했습니다.");
                    } catch (IOException ex) {
                        System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
                    }
                }
                else{
                    //id.txt에 다음 챌린지와 음식난이도, 종류 저장
                    try (FileWriter writer = new FileWriter(Login.getID() + ".txt", true)) {
                        writer.write(  nextchallenge +"/"+ability+food_c);
                        System.out.println("ID와 Password를 성공적으로 등록했습니다.");
                    } catch (IOException ex) {
                        System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
                    }
                }
            }
        });

    }


}
