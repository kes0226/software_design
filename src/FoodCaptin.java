import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FoodCaptin {

    String foodCaptin;
    String food;
    String date;
    String image;

    public JPanel Captin_Panel(){

        JPanel jp = new JPanel(); // 새로운 JPanel 인스턴스를 생성합니다.
        jp.setLayout(null);
        jp.setSize(614, 610);
        jp.setLocation(0, 40);

        final JLabel captin = new JLabel("요리대장");
        captin.setLocation(100, 130);
        captin.setSize(150, 30);
        captin.setFont(new Font("NanumGothic", Font.BOLD, 20));
        jp.add(captin);
        JTextField id_text = new JTextField("");
        id_text.setLocation(250, 130);
        id_text.setSize(150, 30);
        jp.add(id_text);


        final JLabel food_n = new JLabel("음식이름");
        food_n.setLocation(100, 180);
        food_n.setSize(150, 30);
        food_n.setFont(new Font("NanumGothic", Font.BOLD, 20));
        jp.add(food_n);
        JTextField food_txt = new JTextField("");
        food_txt.setLocation(250, 180);
        food_txt.setSize(150, 30);
        jp.add(food_txt);


        final JLabel day = new JLabel("날짜");
        day.setLocation(100, 230);
        day.setSize(150, 30);
        day.setFont(new Font("NanumGothic", Font.BOLD, 20));
        jp.add(day);
        JTextField date_txt = new JTextField("");
        date_txt.setLocation(250, 230);
        date_txt.setSize(150, 30);
        jp.add(date_txt);


        JButton login = new JButton("요리대장 결정");
        login.setLocation(200, 300);
        login.setSize(200, 50);
        jp.add(login);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foodCaptin = id_text.getText();
                food = food_txt.getText();
                date = date_txt.getText();
                image = search_image(foodCaptin, food, date);
                if(image == null){
                    JOptionPane.showMessageDialog(null, "해당되는 정보가 없습니다", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    try (FileWriter member = new FileWriter("administrator.txt")) {
                        member.write("administrator/eunseo/"+foodCaptin+"/"+food+"/"+image);
                        JOptionPane.showMessageDialog(null, "요리대장 저장 성공!");
                    } catch (IOException ex) {
                        System.err.println("파일 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
                    }
                }

            }
        });

        return jp;
    }

    private String search_image(String captin, String name, String date){
        String[] part;
        String image;
        try {
            String content = new String(Files.readAllBytes(Paths.get(captin+"_list.txt")));
            String[] parts = content.split("\n");
            for (String s : parts) {
                part = s.split("/");
                if (part[0].equals(name) && part[2].equals(date)) {
                    image = part[3];
                    return image;
                }
            }

        } catch (IOException ex) {
            System.err.println("파일에 읽기 중 오류가 발생했습니다: " + ex.getMessage());
        }
        return null;
    }

}
