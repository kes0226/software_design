import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.*;//날짜 형식을 위한 정규표현식 사용

public class Store_Image extends JFrame {

    private ModalDialog modalDialog;

    String name, food_p, date;
    File store_file;
    java.awt.Image resizedImage;

    Store_Image(String name, String food_p){
        this.name = name;
        this.food_p = food_p;
    }

    void show_Image() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(614, 650);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        JButton uploadButton = new JButton("이미지 업로드");
        uploadButton.setBounds(250, 0, 150, 30);
        panel.add(uploadButton);

        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(134, 50, 400, 425);
        panel.add(imageLabel);

        JLabel date = new JLabel("날짜");
        date.setLocation(132, 485);
        date.setSize(50, 25);
        date.setFont(new Font("NanumGothic", Font.BOLD, 15));
        panel.add(date);

        JTextField date_txt = new JTextField("");
        date_txt.setLocation(182, 485);
        date_txt.setSize(150, 25);
        panel.add(date_txt);

        JButton store = new JButton("저장");
        store.setBounds(400,485,100,30);
        panel.add(store);

        //저장할 파일명, 상수로 저장해야하는데 안되서 배열로 저장함
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(Store_Image.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    store_file = selectedFile;

                    // 이미지를 새로운 크기로 스케일링
                    ImageIcon originalIcon = new ImageIcon(selectedFile.getPath());
                    java.awt.Image originalImage = originalIcon.getImage();
                    resizedImage = originalImage.getScaledInstance(400, 390, Image.SCALE_SMOOTH);

                    // 스케일링된 이미지를 ImageIcon으로 변환
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    imageLabel.setIcon(resizedIcon); //고른 이미지를 화면에 보여주기
                }
            }
        });

        store.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = date_txt.getText();
                if(checkDateFormat(date) && store_file!=null){
                    try {
                        save_image(date, resizedImage); //id_list에 저장
                        save_file(date);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Store_Image.this, "이미지 저장에 실패했습니다.");
                    }
                    if (modalDialog != null) {
                        modalDialog.dispose();
                    }
                    else{
                        // 새로운 모달 다이얼로그 생성
                        modalDialog =new ModalDialog(Store_Image.this, false);
                        modalDialog.setVisible(true);
                    }
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "잘못된 날짜 형식!");
                }
            }
        });

        setVisible(true);
    }

    public static boolean checkDateFormat(String date) {
        // 정규 표현식을 사용하여 "YYYY.MM.DD" 형식인지 확인
        String pattern = "^\\d{4}\\.\\d{2}\\.\\d{2}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(date);
        return m.matches();
    }

    public void save_image(String date, java.awt.Image resizedImage) throws IOException {

        BufferedImage bufferedImage = new BufferedImage(400,390, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();
        String outputImagePath;
        if(date.equals("administrator")){
            outputImagePath = "food/food_pic/"+name+".jpg";
        }
        else{
            outputImagePath = "food/my_food/"+name+"_"+date+".jpg";
        }
        File outputFile = new File(outputImagePath);
        // BufferedImage를 파일로 저장
        try {
            ImageIO.write(bufferedImage, "jpg", outputFile);
            System.out.println("Image saved successfully: " + outputImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void save_file(String date){
        //id_list.txt에 모든 정보 적기
        String id = Login.getID();
        try (FileWriter writer = new FileWriter(id+"_list.txt", true)) {
            //사진 파일 저장이 성공하면 id_list.txt파일 이번 챌린지 성공한 음식 모든 내용을 적음
            writer.write(name+"/"+food_p+"/"+date +"/"+name+"_"+date+".jpg" + "\n");
            System.out.println("이미지를 성공적으로 저장했습니다.");
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }

        //success파트를 1 늘리기 위해 id.txt에 원래 파일 정보 다시 적음
        String passwordContent = "";
        int success = 0;
        String friends = "";
        //원래 id.txt의 정보들 가져오기
        try {
            String content = new String(Files.readAllBytes(Paths.get(id+".txt")));
            String[] part = content.split("/");
            passwordContent = part[1];
            success = Integer.parseInt(part[3])+1;
            friends = part[2];
            System.out.println(success);
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }
        //id.txt에 고친 success와 원래 파일 정보들 적기
        try (FileWriter writer = new FileWriter(id + ".txt")) {
            writer.write( id + "/" + passwordContent + "/" + friends +"/"+success+"/");
            System.out.println("ID와 Password를 성공적으로 등록했습니다.");
        } catch (IOException ex) {
            System.err.println("파일에 쓰기 중 오류가 발생했습니다: " + ex.getMessage());
        }
    }
}
