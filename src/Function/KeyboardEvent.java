package Function;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class KeyboardEvent extends KeyAdapter {

    private JLabel slime;
    private int speed = 10;

    private ImageIcon slimeimg;
    private ImageIcon slimeLeft;
    private ImageIcon slimeRight;

    public KeyboardEvent(JFrame frame, JLabel slime) {
        this.slime = slime;

        // 이미지 로드
        slimeimg = new ImageIcon("images/slime/slimeimg");
        slimeLeft = new ImageIcon("image/slime/slimeLeft.png");
        slimeRight = new ImageIcon("image/slime/slimeRight.png");

        slime.setIcon(slimeRight);  

        // 키 이벤트 등록
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int x = slime.getX();
        int y = slime.getY();

        //키보드 방향키 명지 움직이기.
        switch (key) {
            case KeyEvent.VK_LEFT:
                slime.setIcon(slimeLeft);      // 왼쪽 이미지
                slime.setLocation(x - speed, y);
                break;

            case KeyEvent.VK_RIGHT:
                slime.setIcon(slimeRight);     // 오른쪽 이미지
                slime.setLocation(x + speed, y);
                break;

            case KeyEvent.VK_UP:
                if(y - speed > -70) {
                    slime.setLocation(x, y - speed);
                }
                break;

            case KeyEvent.VK_DOWN:
                if (y + speed <= 500) {
                    slime.setLocation(x, y + speed);
                }
                break;
        }
    }
}