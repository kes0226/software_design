import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Challenge_Start extends JFrame{
    //메뉴가 없는 챌린지 UI들 구현

    //Challenge클래스에 상속돼서 음식이름, 레시피 설명, 재료를 거기서 받아와서 다 쓰게 하자
    private String name = "", path = "", ingredient = "", recipe = "", food_p = "";

    Challenge_Start(String name, String path, String ingredient, String recipe, String food_p){
        this.name = name;
        this.path = path;
        this.ingredient = ingredient;
        this.recipe = recipe;
        this.food_p = food_p;
    }


    void show_startChallenge(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //전체화면 frame 만들기
        Container cp = getContentPane();
        setSize(614, 650);

        //전체화면 위에 뼈대가 되는 패널 덮어쓰기
        JPanel jp = new JPanel(); // 새로운 JPanel 인스턴스를 생성합니다.
        jp.setLayout(null);
        jp.setPreferredSize(new Dimension(614, 850));

        JScrollPane scrollPane = new JScrollPane(jp);
        scrollPane.setBounds(10, 10, 614, 650);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //x축의 스크롤바는 없앤다

        cp.add(scrollPane);


        //챌린지 제목, 음식 종류, 난이도
        JLabel food = new JLabel(name+"("+food_p+")", JLabel.CENTER);
        food.setLocation(150, 20);
        food.setSize(300, 50);
        food.setFont(new Font("NanumGothic", Font.BOLD, 24));
        jp.add(food);
        //챌린지 이미지
        ImageIcon imageIcon = new ImageIcon("food/food_pic/" + path);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setLocation(132, 85);
        imageLabel.setSize(350, 300);
        jp.add(imageLabel);


        //레시피
        JTextArea Recipe = new JTextArea(recipe);
        Recipe.setEditable(false); // 사용자 입력 방지
        Recipe.setLineWrap(true); // 텍스트 줄 바꿈 설정
        Recipe.setWrapStyleWord(true);
        Recipe.setLocation(75, 400);
        Recipe.setSize(450, 250);
        Recipe.setFont(new Font("NanumGothic", Font.BOLD, 10));
        jp.add(Recipe);

        //재료
        JTextArea Ingredient = new JTextArea(ingredient);
        Ingredient.setEditable(false); // 사용자 입력 방지
        Ingredient.setLineWrap(true); // 텍스트 줄 바꿈 설정
        Ingredient.setWrapStyleWord(true);
        Ingredient.setLocation(75, 670);
        Ingredient.setSize(450, 70);
        Ingredient.setFont(new Font("NanumGothic", Font.BOLD, 15));
        jp.add(Ingredient);


        //챌린지 포기
        JButton fail = new JButton("챌린지 포기");
        fail.setLocation(132, 765);
        fail.setSize(150, 40);
        jp.add(fail);
        fail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModalDialog dialog = new ModalDialog(Challenge_Start.this, true);
                dialog.setVisible(true);
                dispose();
            }
        });

        //챌린지 저장
        JButton success = new JButton("챌린지 저장");
        success.setLocation(332, 765);
        success.setSize(150, 40);
        jp.add(success);

        success.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                Store_Image image = new Store_Image(name, food_p);
                image.show_Image();  //이미지 업로드 하는 클래스 이동
            }
        });

        setVisible(true);

    }


}
