package Frame;

import Function.StartButtonListener;
import java.awt.Container;
import javax.swing.*;

public class StartFrame extends JFrame {

    public ImageIcon backgroundImage;
    public JLabel backgroundLabel;

    public ImageIcon startImage;
    public JButton startButton;

    public StartFrame() {
        setTitle("High School Survival");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);

        // 배경
        backgroundImage = new ImageIcon("image/frame/startimg.png");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1200, 800);

        // 시작 버튼
        startImage = new ImageIcon("image/frame/startbutton.png");
        startButton = new JButton(startImage);
        startButton.setBounds(450, 460, startImage.getIconWidth(), startImage.getIconHeight());
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);

        // 리스너 연결
        startButton.addMouseListener(new StartButtonListener(this));
        backgroundLabel.add(startButton);
        c.add(backgroundLabel);

        setSize(1200, 800);
        setVisible(true);
    }
}