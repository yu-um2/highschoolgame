package MiniGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import Frame.MainLifeFrame;
import Rule.Stage4Rule;

public class Stage4 extends JFrame {

    private JLabel player;          
    private JLabel missionLabel;    
    private JLabel heart1, heart2, heart3;   // 메인 목숨

    private JButton subject4;       

    private int playerX = 550;
    private int playerY = 520;
    private int playerSpeed = 18;

    private Random rand = new Random();

    public Stage4() {

        setTitle("Stage 4 - Rescue Mission");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);

        // 배경
        JLabel backgroundLabel = new JLabel(new ImageIcon("image/frame/background.png"));
        backgroundLabel.setBounds(0, 0, 1200, 800);
        backgroundLabel.setLayout(null);


        // 4교시 버튼 
        subject4 = new JButton("4교시");
        subject4.setBounds(20, 20, 120, 60);
        subject4.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        subject4.setBackground(Color.WHITE);
        subject4.setForeground(Color.BLACK);
        subject4.setFocusable(false);
        subject4.setEnabled(false);
        backgroundLabel.add(subject4);

        // 메인 목숨 연동 
        ImageIcon heartImage = new ImageIcon("image/frame/heart1.png");

        heart1 = new JLabel(heartImage);
        heart1.setBounds(70, 70, 1600, 60);
        backgroundLabel.add(heart1);

        heart2 = new JLabel(heartImage);
        heart2.setBounds(120, 70, 1700, 60);
        backgroundLabel.add(heart2);

        heart3 = new JLabel(heartImage);
        heart3.setBounds(170, 70, 1800, 60);
        backgroundLabel.add(heart3);

        updateHeartsByMainLife();

        // 명지
        player = new JLabel(new ImageIcon("image/slime/slimeRight.png"));
        player.setBounds(playerX, playerY, 260, 110);
        backgroundLabel.add(player);


        // 미션지 랜덤 배치
        ImageIcon missionIcon = new ImageIcon("image/stage4img/mission.png");
        missionLabel = new JLabel(missionIcon);

        int missionX = rand.nextInt(900) + 100;
        int missionY = rand.nextInt(450) + 250;
        missionLabel.setBounds(missionX, missionY,
                missionIcon.getIconWidth(), missionIcon.getIconHeight());

        backgroundLabel.add(missionLabel);

        // 명지 움직이기.
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                int k = e.getKeyCode();
                if (k == KeyEvent.VK_LEFT) {
                    playerX -= playerSpeed;
                    player.setIcon(new ImageIcon("image/slime/slimeLeft.png"));
                } 
                else if (k == KeyEvent.VK_RIGHT) {
                    playerX += playerSpeed;
                    player.setIcon(new ImageIcon("image/slime/slimeRight.png"));
                } 
                else if (k == KeyEvent.VK_UP) playerY -= playerSpeed;
                else if (k == KeyEvent.VK_DOWN) playerY += playerSpeed;

                // 화면 밀림 방지
                if (playerX < 0) playerX = 0;
                if (playerX > 1050) playerX = 1050;
                if (playerY < 300) playerY = 300;
                if (playerY > 620) playerY = 620;

                player.setLocation(playerX, playerY);

                // 충돌 체크
                checkMissionCollision();
            }
        });

        c.add(backgroundLabel);

        setVisible(true);
        setLocationRelativeTo(null);
        requestFocus();
    }

    // 메인 목숨연동
    private void updateHeartsByMainLife() {

        int life = MainLifeFrame.getLife();

        heart1.setVisible(life >= 1);
        heart2.setVisible(life >= 2);
        heart3.setVisible(life >= 3);
    }

    // Stage4Quiz 실행
    private void checkMissionCollision() {

        Rectangle p = player.getBounds();
        Rectangle m = missionLabel.getBounds();

        if (p.intersects(m)) {

            // 미션 닿으면 퀴즈 시작
            new Quiz();
            dispose();
        }
    }

    public static void main(String[] args) {
        new Stage4();
    }
}
