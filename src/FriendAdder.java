import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class FriendAdder {

    int[] success;
    String[] rank;
    String password, friends, next_chal, food_p;

    FriendAdder(int[] success, String[] rank, String password, String friends,String next_chal, String food_p) {
        this.success = success;
        this.rank = rank;
        this.password = password;
        this.friends = friends;
        this.next_chal = next_chal;
        this.food_p = food_p;
    }

    public JPanel FriendB_rank(){

        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        for (int i = 0; i < rank.length; i++) {
            JPanel panel = FriendB(rank[i], success[i], i);
            panelContainer.add(panel);
            panelContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        return panelContainer;
    }

    private JPanel FriendB(String id, int success, int num) {
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
                if(FriendChecker(id, friends)){
                    try (FileWriter member = new FileWriter(Login.getID()+".txt")) {
                        member.write(Login.getID()+"/"+password+"/"+friends+id+",/"+success+"/"+next_chal+"/"+food_p);
                        System.out.println("친구를 파일에 성공적으로 등록했습니다.");
                        JOptionPane.showMessageDialog(null, id+" 친구 추가 성공");
                    } catch (IOException ex) {
                        System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
                    }
                }
            }
        });

        return panel;
    }


    private boolean FriendChecker(String id, String friends){
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
