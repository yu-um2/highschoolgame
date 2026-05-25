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
import MiniGame.Stage2;

public class Stage2Rule extends JFrame {

    public ImageIcon backgroundImage;
    public JLabel backgroundLabel;

    public ImageIcon ruleImage;
    public JLabel ruleLabel;

    public JButton subject;

    public ImageIcon slimeIcon;
    public JLabel slimeLabel;

    public ImageIcon nextIcon;
    public JButton nextbutton;

    public Stage2Rule(StageFrame parentFrame) {
        setTitle("Stage 2 Rule");
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
        ruleLabel.setLayout(null);

        // 룰 제목
        JLabel ruleTitle = new JLabel("Stage2 게임 룰");
        ruleTitle.setFont(new Font("맑은 고딕", Font.BOLD, 35));
        ruleTitle.setForeground(Color.BLACK);
        ruleTitle.setBounds(360, 240, 400, 40);
        ruleLabel.add(ruleTitle);

        // 룰 설명
        JLabel ruleText = new JLabel("<html>1. 제한 시간 100초 안에 문장을 올바르게 입력해 목표 점수 70점  도달해야 합니다.<br><br>" +
                "2. 화면에 맞춤법이 틀린 부분 문장을 올바르게 입력하면 가산점 5점을 획득합니다.<br><br>" +
                "3. 입력 시 오타가 발생하면 해당 부분이 입력창에 빨간색으로 표시됩니다.<br><br>" +
                "4. 틀린 부분이 있으면 5점이 감소되고 3초가 감소됩니다.<br><br>" +
                "5. 모든 문장을 제한 시간안에 입력하고 기준 점수를 넘기면 게임에 성공합니다.<br><br>" +
                "6. 제한 시간 종료 또는 기준 점수가 미달이 되면 게임에 실패합니다.<br><br>" +
                "7. 게임을 실패하거나 게임에 성공하면 게임이 종료됩니다.</html>");
        ruleText.setFont(new Font("맑은 고딕", Font.BOLD, 17));
        ruleText.setForeground(Color.BLACK);
        ruleText.setBounds(200, 250, 650, 400);
        ruleLabel.add(ruleText);

        // 교시 버튼
        subject = new JButton("2교시");
        subject.setBackground(Color.GRAY);
        subject.setBounds(20, 20, 100, 50);
        subject.setFont(new Font("Arial", Font.BOLD, 32));
        subject.setForeground(Color.BLACK);

        // 슬라임 이미지
        slimeIcon = new ImageIcon("image/slime/slimeRight.png");
        slimeLabel = new JLabel(slimeIcon);
        slimeLabel.setBounds(-30, 500,
                slimeIcon.getIconWidth(), slimeIcon.getIconHeight());

        // Next 버튼
        nextIcon = new ImageIcon("image/frame/nextbutton.png");
        nextbutton = new JButton(nextIcon);
        nextbutton.setBounds(1050, 560, 100, 100);
        nextbutton.setBorderPainted(false);
        nextbutton.setContentAreaFilled(false);
        nextbutton.setFocusPainted(false);

        // 메인 목숨 연동
        MainLifeFrame.renderHearts(backgroundLabel);

  
        backgroundLabel.add(slimeLabel);
        c.add(subject);
        c.add(nextbutton);
        c.add(ruleLabel);
        c.add(backgroundLabel);

        
        setSize(1200, 800);
        setVisible(true);
        setResizable(false);

        c.setFocusable(true);
        c.requestFocus();


        // next 버튼으로도 Stage2 실행
        nextbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Stage2();
                dispose();
            }
        });

        setVisible(true);
    }
}
