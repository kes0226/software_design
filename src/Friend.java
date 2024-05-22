import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Member;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Friend {

    private String[] friends = null;

    public JPanel Friend_Panel() {
        // Create the main panel
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.setSize(600, 570);
        jp.setLocation(0, 40);

        int length = 0;
        try {
            String content = new String(Files.readAllBytes(Paths.get( Login.getID()+".txt")));
            String[] parts = content.split("/");
            String how_friends = parts[2];
            if(how_friends.equals("")){
                JLabel add = new JLabel("Add friends!", JLabel.CENTER);
                add.setLocation(150, 50);
                add.setSize(300, 50);
                add.setFont(new Font("NanumGothic", Font.BOLD, 24));
                jp.add(add);
                return jp;
            }
            else{
                friends = how_friends.split(",");
                length = friends.length;
                System.out.println(length);
            }
        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        // Create a panel to hold the dynamic panels
        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        // Add dynamic panels to the panelContainer
        for (int i = 0; i < length; i++) { // Example with 20 panels
            JPanel panel = createPanel(friends[i]);
            panelContainer.add(panel);
            panelContainer.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between panels
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

    private JPanel createPanel(String text) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(385, 40));
        panel.setMaximumSize(new Dimension(385, 40));
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.blue);

        JLabel name = new JLabel(text);
        name.setFont(new Font("NanumGothic", Font.BOLD, 24));
        panel.add(name, BorderLayout.WEST);

        JButton list = new JButton("놀러가기");
        list.setPreferredSize(new Dimension(100, 40));
        panel.add(list, BorderLayout.EAST);

        list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Member_list member = new Member_list(text);
                member.show_Member_list();
            }
        });

        return panel;
    }
}

