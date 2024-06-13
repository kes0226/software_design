import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Recipe extends JFrame{

    private String name, food_p, ingredient, recipe;
    File store_file;
    java.awt.Image resizedImage;

    public JPanel Recipe_Panel(){
        JPanel jp = new JPanel(); // 새로운 JPanel 인스턴스를 생성합니다.
        jp.setLayout(null);
        jp.setSize(614, 610);
        jp.setLocation(0, 40);


        JLabel f_name = new JLabel("음식이름,특성");
        f_name.setBounds(50, 20, 150, 30);
        f_name.setFont(new Font("NanumGothic", Font.BOLD, 17));
        jp.add(f_name);
        JTextField name_text = new JTextField("");
        name_text.setBounds(165, 20, 230, 30);
        jp.add(name_text);

        JButton uploadButton = new JButton("이미지 업로드");
        uploadButton.setBounds(400, 20, 150, 30);
        jp.add(uploadButton);
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(Recipe.this.Recipe_Panel());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    store_file = selectedFile;

                    // 이미지를 새로운 크기로 스케일링
                    ImageIcon originalIcon = new ImageIcon(selectedFile.getPath());
                    java.awt.Image originalImage = originalIcon.getImage();
                    resizedImage = originalImage.getScaledInstance(400, 390, Image.SCALE_SMOOTH);

                    // 스케일링된 이미지를 ImageIcon으로 변환
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    // 새로운 프레임 생성
                    JFrame imageFrame = new JFrame("선택된 이미지");
                    imageFrame.setSize(450, 450);
                    imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    imageFrame.setLayout(new BorderLayout());
                    // 이미지 레이블을 새로운 프레임에 추가
                    JLabel imageLabel = new JLabel();
                    imageLabel.setIcon(resizedIcon);
                    imageFrame.add(imageLabel, BorderLayout.CENTER);
                    // 프레임을 화면에 표시
                    imageFrame.setVisible(true);
                }
            }
        });


        JLabel f_ingre = new JLabel("재료");
        f_ingre.setBounds(50, 60, 50, 20);
        f_ingre.setFont(new Font("NanumGothic", Font.BOLD, 17));
        jp.add(f_ingre);
        JTextArea ingre_text = new JTextArea("");
        ingre_text.setLineWrap(true);
        ingre_text.setFont(new Font("NanumGothic", Font.PLAIN, 15));
        ingre_text.setBounds(50, 85,500, 70);
        jp.add(ingre_text);

        JLabel f_recipe = new JLabel("레시피");
        f_recipe.setBounds(50, 165, 100, 20);
        f_recipe.setFont(new Font("NanumGothic", Font.BOLD, 17));
        jp.add(f_recipe);
        JTextArea textArea = new JTextArea("");
        textArea.setLineWrap(true);  // 텍스트가 가로로 길어질 때 자동 줄바꿈
        textArea.setFont(new Font("NanumGothic", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 190, 500, 250);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jp.add(scrollPane);


        JButton store = new JButton("저장");
        store.setBounds(400,485,100,30);
        jp.add(store);
        store.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] food = name_text.getText().split(",");
                name = food[0].trim();
                food_p = food[1].trim();
                ingredient = ingre_text.getText().trim();
                recipe = textArea.getText().trim();

                if(store_file!=null){
                    try {
                        save(); //음식 정보 저장
                        JOptionPane.showMessageDialog(null, "레시피 저장 성공!");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Recipe.this.Recipe_Panel(), "저장에 실패했습니다.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "이미지를 업로드하세요!");
                }
            }
        });

        return jp;
    }

    private void save() throws IOException {

        Store_Image image = new Store_Image(name, food_p);
        image.save_image("administrator", resizedImage);

        int length = 0; //음식의 일련번호
        try {
            String content = new String(Files.readAllBytes(Paths.get("food/food_info.txt")));
            String[] part = content.split("~");
            length = part.length;
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }
        try (FileWriter writer = new FileWriter("food/food_info.txt", true)) {
            writer.write(length+"/"+name+"/"+ingredient+"/"+name+".jpg~");
            System.out.println("이미지를 성공적으로 저장했습니다.");
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        } //food_info에 저장

        //food_collection에 일련번호 저장
        try (FileWriter writer = new FileWriter("food/food_collection/"+food_p+".txt", true)) {
            writer.write(length+"/");
            System.out.println("이미지를 성공적으로 저장했습니다.");
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        //레시피에 저장
        try (FileWriter writer = new FileWriter("food/recipe.txt", true)) {
            writer.write(recipe+"/");
            System.out.println("이미지를 성공적으로 저장했습니다.");
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }


    }


}
