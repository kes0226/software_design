import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Menu extends JFrame {

    JPanel jp;

    Main_Board main;
    Challenge challenge;
    List list;
    Friend friend;
    Rank rank;
    Recipe recipe;
    FoodCaptin foodCaptin;


    Menu() {
        jp = new JPanel();
        main = new Main_Board();
        challenge = new Challenge();
        list = new List();
        friend = new Friend();
        rank = new Rank();
    }

    Menu(String id){
        jp = new JPanel();
        rank = new Rank();
        recipe = new Recipe();
        foodCaptin = new FoodCaptin();
        main = new Main_Board();
    }

    public void Administrator_Menu(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setLayout(null);
        setSize(614, 650);

        // 뼈대가 되면 jp 설정
        jp.setSize(614, 610);
        jp.setLocation(0, 40);

        //젤 처음 화면은 member화면으로
        jp = main.show_Main_Board(Menu.this);
        cp.add(jp);

        //main가는 버튼
        JButton main_B = new JButton("main");
        main_B.setLocation(0, 0);
        main_B.setSize(150, 40);
        cp.add(main_B);

        main_B.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.remove(jp); // 원래 있던 JPanel을 삭제 후
                jp = main.show_Main_Board(Menu.this); // 새로운 JPanel을 가져와서 jp에 할당
                cp.add(jp); // 새로운 JPanel 추가
                revalidate(); // 레이아웃을 새로고침
                repaint(); //화면을 다시 그림
            }

        });

        // member 가는 버튼
        JButton mem_b = new JButton("member");
        mem_b.setLocation(150, 0);
        mem_b.setSize(150, 40);
        cp.add(mem_b);
        mem_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cp.remove(jp); //cp에 원래 있던 jp 삭제

                jp = rank.show_Panel();

                cp.add(jp);  //다시 cp에 jp를 추가

                revalidate(); // 레이아웃을 새로고침
                repaint(); // 화면을 다시 그림

            }
        });

        JButton recipe_B = new JButton("recipe");
        recipe_B.setLocation(300, 0);
        recipe_B.setSize(150, 40);
        cp.add(recipe_B);
        recipe_B.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cp.remove(jp); //cp에 원래 있던 jp 삭제

                jp = recipe.Recipe_Panel();

                cp.add(jp);  //다시 cp에 jp를 추가

                revalidate(); // 레이아웃을 새로고침
                repaint(); // 화면을 다시 그림

            }
        });

        JButton captin_B = new JButton("captin");
        captin_B.setLocation(450, 0);
        captin_B.setSize(150, 40);
        cp.add(captin_B);
        captin_B.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cp.remove(jp); //cp에 원래 있던 jp 삭제

                jp = foodCaptin.Captin_Panel();

                cp.add(jp);  //다시 cp에 jp를 추가

                revalidate(); // 레이아웃을 새로고침
                repaint(); // 화면을 다시 그림

            }
        });

        setVisible(true);

    }

    public void Menu_show() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setLayout(null);
        setSize(614, 650);

        // 뼈대가 되면 jp 설정
        jp.setSize(614, 610);
        jp.setLocation(0, 40);

        //젤 처음 화면은 main화면으로
        jp = main.show_Main_Board(Menu.this);
        cp.add(jp);

        // main으로 가는 버튼
        JButton main_B = new JButton("main");
        main_B.setLocation(0, 0);
        main_B.setSize(120, 40);
        cp.add(main_B);

        main_B.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.remove(jp); // 원래 있던 JPanel을 삭제 후
                jp = main.show_Main_Board(Menu.this); // 새로운 JPanel을 가져와서 jp에 할당
                cp.add(jp); // 새로운 JPanel 추가
                revalidate(); // 레이아웃을 새로고침
                repaint(); //화면을 다시 그림
            }

        });

        //challenge로 가는 버튼
        JButton chal = new JButton("challenge");
        chal.setLocation(120, 0);
        chal.setSize(120, 40);
        cp.add(chal);
        chal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.remove(jp); // 원래 있던 JPanel을 삭제 후
                jp = challenge.show_Challenge(Menu.this); // 새로운 JPanel을 가져와서 jp에 할당
                cp.add(jp); // 새로운 JPanel 추가
                revalidate(); // 레이아웃을 새로고침
                repaint(); //화면을 다시 그림
            }
        });

        //list로 가는 버튼
        JButton li = new JButton("list");
        li.setLocation(240, 0);
        li.setSize(120, 40);
        cp.add(li);
        li.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.remove(jp); // 원래 있던 JPanel을 삭제 후
                jp = list.show_List(); // 새로운 JPanel을 가져와서 jp에 할당
                cp.add(jp); // 새로운 JPanel 추가
                revalidate(); // 레이아웃을 새로고침
                repaint(); //화면을 다시 그림
            }
        });

        //rank로 가는 버튼
        JButton rank_b = new JButton("rank");
        rank_b.setLocation(360, 0);
        rank_b.setSize(120, 40);
        cp.add(rank_b);
        rank_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cp.remove(jp); //cp에 원래 있던 jp 삭제

                jp = rank.show_Panel();

                cp.add(jp);  //다시 cp에 jp를 추가

                revalidate(); // 레이아웃을 새로고침
                repaint(); // 화면을 다시 그림

            }
        });

        //friend로 가는 버튼
        JButton fri = new JButton("friend");
        fri.setLocation(480, 0);
        fri.setSize(120, 40);
        cp.add(fri);
        fri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cp.remove(jp); //cp에 원래 있던 jp 삭제

                jp = friend.Friend_Panel();

                cp.add(jp);  //다시 cp에 jp를 추가

                revalidate(); // 레이아웃을 새로고침
                repaint(); // 화면을 다시 그림

            }
        });

        setVisible(true);
    }



}
