import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class ModalDialog extends JDialog{


    public ModalDialog(Frame frame, boolean failure) {
        super(frame, true);  //모달 다이얼로그 설정
        // Create panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        // Create labels
        JLabel label = new JLabel("Next Challenge");
        label.setFont(new Font("NanumGothic", Font.BOLD, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding

        // Create JLists
        String[] ability_s = {"상", "하"};
        String[] food_s = {"한식", "중식", "일식"};
        JList<String> abilityBox = new JList<>(ability_s);
        JList<String> foodBox = new JList<>(food_s);

        final String[] ability = {""};
        final String[] food = {""};

        // Set font size for JLists
        Font listFont = new Font("NanumGothic", Font.PLAIN, 16);
        abilityBox.setFont(listFont);
        foodBox.setFont(listFont);

        abilityBox.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    ability[0] = abilityBox.getSelectedValue();
                }
            }
        });

        foodBox.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    food[0] = foodBox.getSelectedValue();
                }
            }
        });

        // Create button
        JButton decide = new JButton("결정");
        Random random = new Random();
        decide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                dispose();
                //새로운 챌린지 정하기: 파일을 열어 파일 길이 확인하고(ex. 4) rand로 4개 중 랜덤한거 한개를 고름
                try {
                    String content = new String(Files.readAllBytes(Paths.get("food/food_collection/"+ability[0]+food[0]+".txt")));
                    String[] parts = content.split("/");
                    int nextchallenge = Integer.parseInt(parts[random.nextInt(parts.length)]);  //랜덤한 음식의 음식번호를 nextchallenge에 저장
                    //새 챌린지를 바탕으로 새로운 화면 생성(id, 다음챌린지번호, 난이도, 종류)
                    System.out.println(nextchallenge);
                    new Next_Challenge(nextchallenge, ability[0], food[0], failure);
                } catch (IOException ex) {
                    System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
                }
            }
        });

        // Set layouts and spacing
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Add components to panels
        centerPanel.add(abilityBox);
        centerPanel.add(foodBox);
        buttonPanel.add(decide);
        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to the dialog
        add(mainPanel);

        // Set dialog properties
        setTitle("Modal Dialog");
        setSize(200, 200);
        setLocationRelativeTo(frame); // Center the dialog relative to the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
