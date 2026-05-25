package Rule;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Frame.MainLifeFrame;
import Frame.StageFrame;
import MiniGame.Stage4;

public class Stage4Rule extends JFrame {
	public ImageIcon backgroundImage;
    public JLabel backgroundLabel;
    
    public ImageIcon ruleImage;
    public JLabel ruleLabel;
    
    public JButton subject;

    public ImageIcon slimeIcon;
    public JLabel slimeLabel;
    
    public ImageIcon nextIcon;
    public JButton nextbutton;
    
    
    public Stage4Rule(StageFrame parentFrame) {
        setTitle("Stage 4 Rule");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);

        // 배경
        backgroundImage = new ImageIcon("image/frame/background.png");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1200, 800);
        
     // 룰 창
        ruleImage = new ImageIcon("image/frame/rule.png");
        ruleLabel = new JLabel(ruleImage);
        ruleLabel.setBounds(250, 0, 1000, 900);

        // 룰 설명 텍스트
        ruleLabel.setLayout(null);

        JLabel ruleTitle = new JLabel("Stage4 게임 룰");
        ruleTitle.setFont(new Font("맑은 고딕", Font.BOLD, 35));
        ruleTitle.setForeground(Color.BLACK);
        ruleTitle.setBounds(360, 240, 400, 40);
        ruleLabel.add(ruleTitle);

        JLabel ruleText = new JLabel("<html>1. 명지를 방향키로 움직여 미션지를 찾으시오.<br><br>"
                + "2. 미션지에 명지가 닿으면 퀴즈가 시작됩니다.<br><br>"
                + "3. 마지막 퀴즈를 풀고 명지를 구해주세요.</html>");
        
        ruleText.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        ruleText.setForeground(Color.BLACK);
        ruleText.setBounds(200, 250, 650, 400);
        ruleLabel.add(ruleText);

        // 교시 버튼
        subject = new JButton("4교시");
        subject.setBackground(Color.GRAY);
        subject.setBounds(20, 20, 100, 50);
        subject.setFont(new Font("Arial", Font.BOLD, 32));
        subject.setForeground(Color.BLACK);

        // 슬라임 이미지
        slimeIcon = new ImageIcon("image/slime/slimeRight.png");
        slimeLabel = new JLabel(slimeIcon);
        slimeLabel.setBounds(-30, 500,
                slimeIcon.getIconWidth(), slimeIcon.getIconHeight());

        // next 버튼
        nextIcon = new ImageIcon("image/frame/nextbutton.png");
        nextbutton = new JButton(nextIcon);
        nextbutton.setBounds(1050, 560, 100, 100);
        nextbutton.setBorderPainted(false);
        nextbutton.setContentAreaFilled(false);
        nextbutton.setFocusPainted(false);

        // 메인 목숨
        MainLifeFrame.renderHearts(backgroundLabel);



        // 컴포넌트 추가
        backgroundLabel.add(slimeLabel);
        c.add(subject);
        c.add(nextbutton);
        c.add(ruleLabel);
        c.add(backgroundLabel);

        // 창 설정
        setSize(1200, 800);
        setVisible(true);
        setResizable(false);

        c.setFocusable(true);
        c.requestFocus();


        nextbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Stage4();
                dispose();
            }
        });

        setVisible(true);
    }
}