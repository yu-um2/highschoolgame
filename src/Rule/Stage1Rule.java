package Rule;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import MiniGame.Stage1;
import Frame.StageFrame;
import Frame.MainLifeFrame;

public class Stage1Rule extends JFrame {
	
    public ImageIcon backgroundImage;
    public JLabel backgroundLabel;
    
    public ImageIcon ruleImage;
    public JLabel ruleLabel;

    public JButton subject;

    public ImageIcon slimeIcon;
    public JLabel slimeLabel;
    
    public ImageIcon nextIcon;
    public JButton nextbutton;
    
    
    public Stage1Rule(StageFrame parentFrame) {
        setTitle("Stage 1 Rule");
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

        JLabel ruleTitle = new JLabel("Stage1 게임 룰");
        ruleTitle.setFont(new Font("맑은 고딕", Font.BOLD, 35));
        ruleTitle.setForeground(Color.BLACK);
        ruleTitle.setBounds(360, 240, 400, 40);
        ruleLabel.add(ruleTitle);

        JLabel ruleText = new JLabel("<html>1. 게임은 3개의 목숨으로 시작하며, GAME START 버튼을 눌러 게임을 시작합니다.<br><br>"
                + "2. 기호는 랜덤하게 화면에 표시되며 기호 버튼을 클릭하여 본 순서대로 입력합니다.<br><br>"
                + "3. 기호가 기억이 나지 않을 경우, GAME START 버튼을 눌러 다시 시작할 수 있습니다.<br><br>"
                + "4. 다시 입력하고 싶다면 CLEAR 버튼을 눌러 현재 입력을 초기화합니다.<br><br>"
                + "5. 입력을 완료했다면 CHECK ANSWER 버튼을 눌러 정답을 확인합니다.<br><br>"
                + "6. 정답일 경우, 다음 레벨로 넘어가고 오답일 경우에는 목숨이 1개 감소합니다.<br><br>"
                + "7. 5단계를 통과하거나 남은 목숨이 0이 되면 게임이 종료됩니다.</html>");
        ruleText.setFont(new Font("맑은 고딕", Font.BOLD, 17));
        ruleText.setForeground(Color.BLACK);
        ruleText.setBounds(200, 250, 650, 400);
        ruleLabel.add(ruleText);

        // 교시 버튼
        subject = new JButton("1교시");
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


        // 메인 목숨 하트 MainLifeFrame에서 현재 하트 수 가져옴.
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
                new Stage1();
                dispose();
            }
        });

        setVisible(true);
    }
}
