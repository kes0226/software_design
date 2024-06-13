import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Rank {

    String[] rank;
    int[] success;
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

        get_sorted_rank();

        // Create a panel to hold the dynamic panels
        JPanel panelContainer = new JPanel();

        if(Login.getID().equals("administrator")){
            UserList_ShowManager mem_list = new UserList_ShowManager(success,rank);
            panelContainer = mem_list.ListB_rank();
        }else{
            FriendAdder fri_add = new FriendAdder(success,rank, password, friends, next_chal, food_p);
            panelContainer = fri_add.FriendB_rank();
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


    private void get_sorted_rank(){
        //각 id의 성공횟수 불러오고
        success = new int[rank.length];

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

        //정렬 알고리즘 써서 success로 정렬하기
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

    }


}
