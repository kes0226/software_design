import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Recipe {

    private String name, food_p, ingredient, recipe;
    File store_file;
    java.awt.Image resizedImage;

    public JPanel Recipe_Panel(){
        JPanel jp = new JPanel(); // 새로운 JPanel 인스턴스를 생성합니다.
        jp.setLayout(null);
        jp.setSize(614, 610);
        jp.setLocation(0, 40);

        JButton uploadButton = new JButton("이미지 업로드");
        uploadButton.setBounds(250, 0, 150, 30);
        jp.add(uploadButton);
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(Recipe.this.Recipe_Panel());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // 이미지를 새로운 크기로 스케일링
                    ImageIcon originalIcon = new ImageIcon(selectedFile.getPath());
                    java.awt.Image originalImage = originalIcon.getImage();
                    resizedImage = originalImage.getScaledInstance(400, 390, Image.SCALE_SMOOTH);

                }
            }
        });

        JButton store = new JButton("저장");
        store.setBounds(400,485,100,30);
        jp.add(store);
        store.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(store_file!=null){
                    try {
                        save(); //음식 정보 저장

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

        BufferedImage bufferedImage = new BufferedImage(400,390, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();

        String outputImagePath = "food/food_pic/"+name+".jpg";
        File outputFile = new File(outputImagePath);
        // BufferedImage를 파일로 저장
        try {
            ImageIO.write(bufferedImage, "jpg", outputFile);
            System.out.println("Image saved successfully: " + outputImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }



        int length = 0; //음식의 일련번호
        try {
            String content = new String(Files.readAllBytes(Paths.get("food/food_info.txt")));
            String[] part = content.split("~");
            length = part.length;
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        try (FileWriter writer = new FileWriter("food/food_info.txt", true)) {
            writer.write(length+"/"+name+"/"+ingredient+"/"+name+".jpg" + "\n");
            System.out.println("이미지를 성공적으로 저장했습니다.");
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        try (FileWriter writer = new FileWriter("food/food_collection/"+food_p+".txt", true)) {
            writer.write(length+"/");
            System.out.println("이미지를 성공적으로 저장했습니다.");
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        try (FileWriter writer = new FileWriter("food/recipe.txt", true)) {
            writer.write(recipe+"/");
            System.out.println("이미지를 성공적으로 저장했습니다.");
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }


    }


}
