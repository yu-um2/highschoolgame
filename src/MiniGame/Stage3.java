package MiniGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import Frame.MainLifeFrame;
import Rule.Stage4Rule;

public class Stage3 extends JFrame {

    private JLabel slime;
    private JLabel fallingBook;
    private JLabel fallingTrash;

    private JLabel lifeLabel;
    private ImageIcon[] lifeIcons = new ImageIcon[4];
    private int remainingLives = 3;

    private int slimeX = 520;
    private int slimeY = 520;

    private int slimeSpeed = 22;
    private int bookSpeed = 4;
    private int trashSpeed = 5;

    private Timer gameTimer;
    private Timer countdownTimer;

    private int score = 0;
    private int timeLeft = 30;

    private Random rand = new Random();

    // 여러 실패 원인이 겹쳐도 목숨 -1은 딱 한 번만 하기 위한 플래그
    private boolean failedOnce = false;

    public Stage3() {

        setTitle("Stage3");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);

        // 배경
        JLabel bg = new JLabel(new ImageIcon("image/frame/background.png"));
        bg.setBounds(0, 0, 1200, 800);

        // 목숨 로드
        loadLifeImages();

        lifeLabel = new JLabel();
        lifeLabel.setBounds(950, 20, 200, 80);
        updateLifeDisplay();

        // TIME
        JLabel timerLabel = new JLabel("TIME : " + timeLeft, SwingConstants.RIGHT);
        timerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        timerLabel.setBounds(760, 100, 200, 40);

        // SCORE
        JLabel scoreLabel = new JLabel("SCORE : " + score, SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        scoreLabel.setBounds(950, 100, 200, 40);

        // 3교시 버튼
        JButton subject3 = new JButton("3교시");
        subject3.setBounds(20, 20, 120, 60);
        subject3.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        subject3.setBackground(Color.WHITE);
        subject3.setForeground(Color.BLACK);
        subject3.setFocusable(false);
        subject3.setEnabled(false);
        c.add(subject3);

        // 슬라임
        slime = new JLabel(new ImageIcon("image/slime/slimeRight_300.png"));
        slime.setBounds(slimeX, slimeY, 250, 200);

        // 책
        fallingBook = new JLabel(new ImageIcon("image/stage3img/book2.png"));
        fallingBook.setBounds(0, -150, 80, 80);

        // 휴지
        fallingTrash = new JLabel(new ImageIcon("image/stage3img/trash_100.png"));
        fallingTrash.setBounds(0, -200, 80, 80);

        // 슬라임 이동
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                int k = e.getKeyCode();

                if (k == KeyEvent.VK_LEFT) {
                    slimeX -= slimeSpeed;
                    slime.setIcon(new ImageIcon("image/stage3img/slimeLeft_300.png"));
                }
                else if (k == KeyEvent.VK_RIGHT) {
                    slimeX += slimeSpeed;
                    slime.setIcon(new ImageIcon("image/stage3img/slimeRight_300.png"));
                }
                else if (k == KeyEvent.VK_UP) slimeY -= slimeSpeed;
                else if (k == KeyEvent.VK_DOWN) slimeY += slimeSpeed;

                // 화면 밖 방지
                if (slimeX < 0) slimeX = 0;
                if (slimeX > 1060) slimeX = 1060;
                if (slimeY < 350) slimeY = 350;
                if (slimeY > 620) slimeY = 620;

                slime.setLocation(slimeX, slimeY);
            }
        });

        // 컴포넌트 순서
        c.add(slime);
        c.add(fallingBook);
        c.add(fallingTrash);
        c.add(lifeLabel);
        c.add(timerLabel);
        c.add(scoreLabel);
        c.add(bg);

        // 떨어지는 애니메이션
        gameTimer = new Timer(16, e -> {

            fallingBook.setLocation(fallingBook.getX(), fallingBook.getY() + bookSpeed);
            if (fallingBook.getY() > 900) resetBook();

            fallingTrash.setLocation(fallingTrash.getX(), fallingTrash.getY() + trashSpeed);
            if (fallingTrash.getY() > 900) resetTrash();

            checkCollision(scoreLabel);
        });

        // 1초 감소
        countdownTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("TIME : " + timeLeft);

            if (timeLeft <= 0) {
                // 시간 초과 실패 한 번만 처리
                if (!failedOnce) {
                    MainLifeFrame.decreaseLife();
                    failedOnce = true;
                }

                countdownTimer.stop();
                gameTimer.stop();
                endStage();
            }
        });

        resetBook();
        resetTrash();

        gameTimer.start();
        countdownTimer.start();

        setVisible(true);
        setLocationRelativeTo(null);
        requestFocus();
    }

    // 목숨 이미지 로드
    private void loadLifeImages() {

        String[] files = {
                "image/stage1img/heart0.png",
                "image/stage1img/heart1.png",
                "image/stage1img/heart2.png",
                "image/stage1img/heart3.png"
        };

        for (int i = 0; i < 4; i++) {
            ImageIcon icon = new ImageIcon(files[i]);
            Image img = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            lifeIcons[i] = new ImageIcon(img);
        }
    }

    private void updateLifeDisplay() { //남은 목숨 업데이트.
        lifeLabel.setIcon(lifeIcons[remainingLives]);
    }

    private void resetBook() {
        fallingBook.setLocation(rand.nextInt(1000) + 50, -150);
    }

    private void resetTrash() {
        fallingTrash.setLocation(rand.nextInt(1000) + 50, -200);
    }

    // 충돌
    private void checkCollision(JLabel scoreLabel) {

        Rectangle s = slime.getBounds();
        Rectangle b = fallingBook.getBounds();
        Rectangle t = fallingTrash.getBounds();

        if (s.intersects(b)) { // 슬라임과 책의 충돌 확인.
            score++;
            scoreLabel.setText("SCORE : " + score);
            resetBook();
        }

        if (s.intersects(t)) { //슬라임과 쓰레기 충돌 확인.

            remainingLives--;
            updateLifeDisplay();
            resetTrash();

            if (remainingLives <= 0) {

                // 충돌로 사망해도 딱 한 번만 목숨 깎기
                if (!failedOnce) {
                    MainLifeFrame.decreaseLife();
                    failedOnce = true;
                }

                gameTimer.stop();
                countdownTimer.stop();
                endStage();
            }
        }
    }

    // 점수띄우기
    private void endStage() {

        Container c = getContentPane();
        c.removeAll();
        c.setLayout(null);
        c.setBackground(new Color(0xF8EBD8));

        JLabel msg = new JLabel("", SwingConstants.CENTER);
        msg.setFont(new Font("맑은 고딕", Font.BOLD, 32));
        msg.setBounds(300, 150, 600, 80);
        c.add(msg);

        // 점수 판정
        if (score >= 10) {

            msg.setText("성공! SCORE : " + score);

        } else {

            msg.setText("실패... SCORE : " + score);

            // 충돌이 아니고 시간초과로 이미 깎이지 않았으면 여기서 한 번만 깎기
            if (!failedOnce) {
                MainLifeFrame.decreaseLife();
                failedOnce = true;
            }
        }

        // 다음 스테이지 버튼
        ImageIcon nextBtnIcon = new ImageIcon("image/stage1img/next.png");
        Image nextImg = nextBtnIcon.getImage().getScaledInstance(190, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledNextIcon = new ImageIcon(nextImg);

        JButton nextBtn = new JButton(scaledNextIcon);
        nextBtn.setBounds(475, 400, 250, 60);
        nextBtn.setBorderPainted(false);
        nextBtn.setContentAreaFilled(false);
        nextBtn.setFocusPainted(false);
        nextBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c.add(nextBtn);

        nextBtn.addActionListener(e -> {
            new Stage4Rule(null);
            dispose();
        });

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new Stage3();
    }
}
