package Frame;

import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import Frame.StartFrame;

public class GameClearFrame extends JFrame {

    public ImageIcon gameclearImage;
    public JLabel gameclearLabel;

    public ImageIcon startImage;
    public JButton startButton;
    
    private ImageIcon slimeIcon;
    private JButton slimeButton;

    public GameClearFrame() {
        setTitle("High School Survival");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);

        // 배경
        gameclearImage = new ImageIcon("image/frame/gameclear.png");
        gameclearLabel = new JLabel(gameclearImage);
        gameclearLabel.setBounds(0, 0, 1200, 800);
        c.add(gameclearLabel);
        
        // 슬라임 이미지
        slimeIcon = new ImageIcon("image/slime/slimeimg.png");
        slimeButton = new JButton(slimeIcon);
        slimeButton.setBounds(350, 350,
                slimeIcon.getIconWidth(), slimeIcon.getIconHeight());
        gameclearLabel.add(slimeButton);
        
        // 버튼의 기본 배경/테두리 제거 (이미지 그대로 보이게)
        slimeButton.setBorderPainted(false);
        slimeButton.setContentAreaFilled(false);
        slimeButton.setFocusPainted(false);
        
        // 슬라임 클릭 시 MainFrame으로 이동
        slimeButton.addActionListener(e -> {
            new StartFrame();   // 메인 프레임 실행
            SwingUtilities.getWindowAncestor(slimeButton).dispose();// 현재 gameclearFrame이 있다면 닫기
        });

        setSize(1200, 800);
        setVisible(true);
    }
}
