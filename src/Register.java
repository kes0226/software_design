import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

//rank를 만들기 위해 rank파일에 id파일이름 저장
//한 명의 id파일과 id_list파일을 생성
//id파일에 id/password/성공횟수/처음챌린지/친구/"id_list.txt" 저장
//Register버튼 누른 후 다시 로그인 화면으로
public class Register extends JFrame {

    public void Register_show(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setLayout(null);

        //등록 제목
        JLabel title = new JLabel("Register");
        title.setLocation(250, 40);
        title.setSize(100, 50);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        cp.add(title);

        //id 넣는 칸
        JLabel id = new JLabel("id");
        id.setLocation(150, 125);
        id.setSize(50, 20);
        id.setFont(new Font("Arial", Font.BOLD, 17));
        cp.add(id);
        JTextField id_text = new JTextField("");
        id_text.setLocation(250, 125);
        id_text.setSize(150, 20);
        cp.add(id_text);

        //password 넣는 칸
        JLabel ps = new JLabel("password");
        ps.setLocation(150, 175);
        ps.setSize(90, 20);
        ps.setFont(new Font("Arial", Font.BOLD, 17));
        cp.add(ps);
        JTextField p_text = new JTextField("");
        p_text.setLocation(250, 175);
        p_text.setSize(150, 20);
        cp.add(p_text);


        //JPanel에 선호음식 라디오버튼 추가
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.setLocation(150,235);
        panel1.setSize(250, 60);
        //선호음식 라디오버튼
        JLabel food = new JLabel("선호음식");
        food.setFont(new Font("NanumGothic", Font.BOLD, 17));
        panel1.add(food); // JPanel에 JLable 추가
        JRadioButton korea = new JRadioButton("한식");
        JRadioButton china = new JRadioButton("중식");
        JRadioButton europe = new JRadioButton("일식");
        //buttongroup에 라디오버튼 추가
        ButtonGroup group1 = new ButtonGroup();
        group1.add(korea);
        group1.add(china);
        group1.add(europe);
        //JPanel에 라디오버튼 추가
        panel1.add(korea);
        panel1.add(china);
        panel1.add(europe);
        // JPanel을 컨테이너에 추가
        cp.add(panel1);

        final String[] selectedFood = {""};
        ActionListener foodListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedButton = (JRadioButton) e.getSource();
                selectedFood[0] = selectedButton.getText();
            }
        };
        korea.addActionListener(foodListener);
        china.addActionListener(foodListener);
        europe.addActionListener(foodListener);

        //JPanel에 음식실력 라디오버튼 추가
        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.setLocation(150,285);
        panel2.setSize(250, 100);
        //요리실력 라디오버튼
        JLabel ability = new JLabel("요리실력");
        ability.setFont(new Font("NanumGothic", Font.BOLD, 17));
        panel2.add(ability); // JPanel에 JLable 추가

        JRadioButton high = new JRadioButton("상");
        JRadioButton low = new JRadioButton("하");

        ButtonGroup group2 = new ButtonGroup();

        group2.add(high); // JPanel에 JCheckBox 추가
        group2.add(low);

        panel2.add(high); // JPanel에 JCheckBox 추가
        panel2.add(low);

        cp.add(panel2);

        final String[] selectedAbility = {""};
        ActionListener abilityListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedButton = (JRadioButton) e.getSource();
                selectedAbility[0] = selectedButton.getText();
            }
        };
        high.addActionListener(abilityListener);
        low.addActionListener(abilityListener);


        //등록 버튼
        JButton register = new JButton("register");
        register.setLocation(245, 350);
        register.setSize(100, 30);
        cp.add(register);
        //등록버튼 누를 시 이벤트
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idContent = id_text.getText(); // 입력한 id와 password 받아옴
                String passwordContent = p_text.getText();
                // id와 password가 비워있으면 오류 메시지
                if (idContent.isEmpty() || passwordContent.isEmpty()) {
                    JOptionPane.showMessageDialog(cp, "ID 또는 Password가 비어 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 선호음식 및 요리실력이 선택되지 않은 경우 오류 메시지
                if (selectedFood[0].isEmpty() || selectedAbility[0].isEmpty()) {
                    JOptionPane.showMessageDialog(cp, "선호음식 및 요리실력을 선택해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (checkID(idContent)) {
                    String fileName = idContent.trim(); // 파일이름으로 사용할 때 공백 제거

                    // id와 password를 rank파일에 추가
                    try (FileWriter member = new FileWriter("rank.txt", true)) {
                        member.write(idContent + "/");
                        System.out.println("ID와 Password를 rank에 성공적으로 등록했습니다.");
                    } catch (IOException ex) {
                        System.err.println("파일 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
                    }

                    // 새로운 id파일을 생성 후 id와 password를 추가
                    int nextchallenge = nextchallenge(selectedFood[0], selectedAbility[0]);
                    try (FileWriter writer = new FileWriter(fileName + ".txt")) {
                        writer.write(idContent + "/" + passwordContent + "//" + "0" + "/" + nextchallenge + "/" + selectedAbility[0] + selectedFood[0]); // 초기에 정보 저장해놓은 것
                        System.out.println("ID와 Password를 성공적으로 등록했습니다.");
                    } catch (IOException ex) {
                        System.err.println("파일 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
                    }
                    // id_list.txt 파일을 생성
                    try (FileWriter writer = new FileWriter(fileName + "_list.txt")) {
                        System.out.println("ID와 Password를 성공적으로 등록했습니다.");
                    } catch (IOException ex) {
                        System.err.println("파일 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
                    }

                    // 등록 성공 후 로그인 화면으로 이동
                    dispose(); // 등록창 닫음
                    Login log = new Login();
                    log.Login_show();

                } else {
                    JOptionPane.showMessageDialog(cp, "ID가 유효하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setSize(600, 450);
        setVisible(true);
    }


    //id, password 등록버튼 누르고 id 중복되는지 체크하는 함수
    public boolean checkID(String idContent){  //id 중복되는지 check
        try {
            String content = new String(Files.readAllBytes(Paths.get("rank.txt")));
            String[] parts = content.split("/");
            for(int i=0; i< parts.length; i++){
                if(parts[i].equals(idContent) || parts[i].equals("administrator")){
                    System.out.println(parts[i]);
                    JOptionPane.showMessageDialog(null, "중복되는 ID입니다!");
                    return false;
                }
            }
        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }
        return true;
    }

    public Integer nextchallenge(String selectedFood, String selectedAbility){
        int nextchallenge = 0;
        try {
            String content = new String(Files.readAllBytes(Paths.get("food/food_collection/"+selectedAbility+selectedFood+".txt")));
            String[] parts = content.split("/");
            Random random = new Random();
            nextchallenge = Integer.parseInt(parts[random.nextInt(parts.length)]);
            //랜덤한 음식의 음식번호를 nextchallenge에 저장
        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }
        return nextchallenge;
    }


}
