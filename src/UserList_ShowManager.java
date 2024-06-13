import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserList_ShowManager {
    int[] success;
    String[] rank;

    UserList_ShowManager(int[] success, String[] rank) {
        this.success = success;
        this.rank = rank;
    }

    public JPanel ListB_rank(){

        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        for (int i = 0; i < rank.length; i++) {
            JPanel panel = ListB(rank[i], success[i], i);
            panelContainer.add(panel);
            panelContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        return panelContainer;
    }

    private JPanel ListB(String id, int success, int num) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(385, 40));
        panel.setMaximumSize(new Dimension(385, 40));
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.blue);

        JLabel name = new JLabel(num+". " + id +": " + success);
        name.setFont(new Font("NanumGothic", Font.BOLD, 24));
        panel.add(name, BorderLayout.WEST);

        JButton list = new JButton("list");
        list.setPreferredSize(new Dimension(100, 40));
        panel.add(list, BorderLayout.EAST);

        list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Member_list member = new Member_list(id);
                member.show_Member_list();
            }
        });

        return panel;
    }

}
