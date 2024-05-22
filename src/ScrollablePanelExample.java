import javax.swing.*;
import java.awt.*;

public class ScrollablePanelExample {

    String id;
    ScrollablePanelExample(String id) {
        this.id = id;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Scrollable JPanel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(614, 630);

        // 크기가 frame과 같은 JPanel 생성
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1)); // 예시로 GridLayout을 사용
        for (int i = 0; i < 20; i++) {
            contentPanel.add(new JLabel("Label " + i));
        }

        // JScrollPane에 JPanel 추가
        JScrollPane scrollPane = new JScrollPane(contentPanel);

        // JScrollPane의 스크롤 속성 설정
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // JScrollPane을 JFrame에 추가
        frame.add(scrollPane);

        frame.setVisible(true);
    }
}
