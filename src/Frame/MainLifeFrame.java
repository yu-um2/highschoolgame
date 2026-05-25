package Frame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.GameOverFrame;

public class MainLifeFrame {

    private static int life = 3;  // 메인 목숨

    public static int getLife() {
        return life;
    }

    //  메인 목숨 감소 + 자동 GAME OVER 처리
    public static void decreaseLife() {
        if (life > 0) {
            life--;
        }

        //  목숨이 0 이하가 되는 순간 즉시 GameOverFrame 호출
        if (life <= 0) {
            new GameOverFrame();
        }
    }

    public static void resetLife() {
        life = 3;
    }

    // Rule창/Stage창에 목숨 표시 
    public static void renderHearts(JLabel parent) {

        ImageIcon heartIcon = new ImageIcon("image/frame/heart1.png");

        JLabel heart1 = new JLabel(heartIcon);
        heart1.setBounds(70, 70, 1600, 60); //실제 프레임크기대비 좌표값이 안맞는 오류해결 위해 반영된 좌표설정.
        parent.add(heart1);

        JLabel heart2 = new JLabel(heartIcon);
        heart2.setBounds(120, 70, 1700, 60);
        parent.add(heart2);

        JLabel heart3 = new JLabel(heartIcon);
        heart3.setBounds(170, 70, 1800, 60);
        parent.add(heart3);

        int now = getLife();

        heart1.setVisible(now >= 1);
        heart2.setVisible(now >= 2);
        heart3.setVisible(now >= 3);
    }
}
