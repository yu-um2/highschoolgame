package Frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Function.KeyboardEvent;
import Function.StageListener;

public class StageFrame extends JFrame {

    public ImageIcon backgroundImage;
    public JLabel backgroundLabel;
    
    public ImageIcon subjectImage;
    public JButton subject1, subject2, subject3, subject4;

    public ImageIcon slimeIcon;
    public JLabel slimeLabel;

    public ImageIcon heartImage;
    public JLabel heart1, heart2, heart3;

    private int life = 3; // 목숨 추가

    public StageFrame() {
        setTitle("High School Survival");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);

        // 배경
        backgroundImage = new ImageIcon("image/frame/background.png");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1200, 800);
        c.add(backgroundLabel);

        // 목숨
        heartImage = new ImageIcon("image/frame/heart1.png");
        heart1 = new JLabel(heartImage); heart1.setBounds(70, 70, 1600, 60); backgroundLabel.add(heart1);
        heart2 = new JLabel(heartImage); heart2.setBounds(120, 70, 1700, 60); backgroundLabel.add(heart2);
        heart3 = new JLabel(heartImage); heart3.setBounds(170, 70, 1800, 60); backgroundLabel.add(heart3);

        // 슬라임 이미지
        slimeIcon = new ImageIcon("image/slime/slimeRight.png");
        slimeLabel = new JLabel(slimeIcon);
        slimeLabel.setBounds(130, 500,
                slimeIcon.getIconWidth(), slimeIcon.getIconHeight());
        backgroundLabel.add(slimeLabel);

        // 키보드 이벤트 등록
        KeyboardEvent keyboardEvent = new KeyboardEvent(this, slimeLabel);
        c.addKeyListener(keyboardEvent);
        c.setFocusable(true);
        c.requestFocus();

       
        
        // 교시 버튼(1교시는 칠판에 표시되므로 imageIcon으로 디자인함)
        ImageIcon subjectImage = new ImageIcon("image/frame/subjectboard.png");

        // 버튼 생성 (이미지 + 텍스트)
        subject1 = new JButton("1교시", subjectImage);
        subject1.setFont(new Font("맑은 고딕", Font.BOLD, 40));// 텍스트 색, 폰트
        subject1.setForeground(Color.WHITE);
        // 버튼 위치 & 크기 (이미지에 맞게 조절)
        subject1.setBounds(270, 100, subjectImage.getIconWidth(), subjectImage.getIconHeight());
        // 스타일 (테두리·배경 제거)
        subject1.setBorderPainted(false);
        subject1.setContentAreaFilled(false);
        subject1.setFocusPainted(false);

        // 텍스트가 이미지 위치
        subject1.setHorizontalTextPosition(SwingConstants.CENTER);
        subject1.setVerticalTextPosition(SwingConstants.CENTER);

        subject1.addActionListener(new StageListener(this, 1));

        backgroundLabel.add(subject1);
        
        //다른 교시 버튼들(칠판엔 표시되지 않음)
        subject2 = new JButton("2교시"); 
        subject2.setBounds(530, 280, 100, 50);
        subject2.addActionListener(new StageListener(this, 2));
        subject2.setVisible(false); 
        backgroundLabel.add(subject2);

        subject3 = new JButton("3교시"); 
        subject3.setBounds(530, 280, 100, 50);
        subject3.addActionListener(new StageListener(this, 3));
        subject3.setVisible(false); 
        backgroundLabel.add(subject3);

        subject4 = new JButton("4교시"); 
        subject4.setBounds(530, 280, 100, 50);
        subject4.addActionListener(new StageListener(this, 4));
        subject4.setVisible(false); 
        backgroundLabel.add(subject4);

        // 창 설정
        setSize(1200, 800);
        setVisible(true);
        setResizable(false);
    }

    // StageFrame 목숨 관리
    public void setLife(int newLife) {
        life = newLife;
        heart1.setVisible(life >= 1);
        heart2.setVisible(life >= 2);
        heart3.setVisible(life >= 3);
    }

    public int getLife() { return life; }

    // 다음 교시 버튼 보이게
    public void showNextSubjectButton(int nextStage) {
        switch(nextStage) {
            case 2: subject2.setVisible(true); break;
            case 3: subject3.setVisible(true); break;
            case 4: subject4.setVisible(true); break;
        }
    }

    public static void main(String[] args) {
        new StageFrame();
    }
}