import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JOptionPane;


public class Rank {

    String[] rank;
    String[] parts = new String[6];
    String password, friends, next_chal, food_p;

    public JPanel show_Panel() {
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.setSize(600, 570);
        jp.setLocation(0, 40);


        try {
            String content = new String(Files.readAllBytes(Paths.get( "rank.txt")));
            rank = content.split("/");
            if(rank.length == 0){
                JLabel add = new JLabel("Rank", JLabel.CENTER);
                add.setLocation(150, 50);
                add.setSize(300, 50);
                add.setFont(new Font("NanumGothic", Font.BOLD, 24));
                jp.add(add);
                return jp;
            }
        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        //각 id의 성공횟수 불러오기
        int [] success = new int[rank.length];
        for (int i = 0; i < rank.length; i++) {
            try {
                String content = new String(Files.readAllBytes(Paths.get( rank[i]+".txt")));
                parts = content.split("/");
                success[i] = Integer.parseInt(parts[3]);
                if(rank[i].equals(Login.getID())){
                    password = parts[1];
                    friends = parts[2];
                    next_chal = parts[4];
                    food_p = parts[5];
                }
            } catch (IOException ex) {
                System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
            }
        }

        //정렬 알고리즘 써서 구하기
        int temp;
        String r_temp;
        for (int i = 1; i < rank.length; i++){
            for(int j = i; j>0 && success[j]>success[j-1]; j--){
                temp = success[j];
                success[j] = success[j-1];
                success[j-1] = temp;

                r_temp = rank[j];
                rank[j] = rank[j-1];
                rank[j-1] = r_temp;
            }
        }

        // Create a panel to hold the dynamic panels
        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        // Add dynamic panels to the panelContainer
        for (int i = 0; i < rank.length; i++) {
            JPanel panel = createPanel(rank[i], success[i], i);
            panelContainer.add(panel);
            panelContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Create a JScrollPane to contain the panelContainer
        JScrollPane scrollPane = new JScrollPane(panelContainer);
        scrollPane.setPreferredSize(new Dimension(600, 570));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the JScrollPane to the main panel
        jp.add(scrollPane, BorderLayout.CENTER);

        return jp;
    }

    private JPanel createPanel(String id, int success, int num) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(385, 40));
        panel.setMaximumSize(new Dimension(385, 40));
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.blue);

        JLabel name = new JLabel(num+". " + id +": " + success);
        name.setFont(new Font("NanumGothic", Font.BOLD, 24));
        panel.add(name, BorderLayout.WEST);

        JButton fri = new JButton("친구");
        fri.setPreferredSize(new Dimension(100, 40));
        panel.add(fri, BorderLayout.EAST);

        fri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //추가할 친구인 id가 자신의 친구와 중복되는지 아니면 나를 친구로 추가하는지 확인
                if(check(id, friends)){
                    try (FileWriter member = new FileWriter(Login.getID()+".txt")) {
                        member.write(Login.getID()+"/"+password+"/"+friends+id+",/"+success+"/"+next_chal+"/"+food_p);
                        System.out.println("친구를 파일에 성공적으로 등록했습니다.");
                    } catch (IOException ex) {
                        System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
                    }
                }
            }
        });

        return panel;
    }

    public boolean check(String id, String friends){
        String[] fri = friends.split(",");

        if(id.equals(Login.getID())){
            JOptionPane.showMessageDialog(null, "자신을 친구 추가했습니다!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            for(int i = 0; i < fri.length; i++){
                if(id.equals(fri[i])){
                    JOptionPane.showMessageDialog(null, "중복된 친구를 추가했습니다!", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            return true;
        }
    }

}
