package Function;

import Frame.Game;
import Frame.StartFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartButtonListener extends MouseAdapter {
    private StartFrame startFrame;

    public StartButtonListener(StartFrame startFrame) {
        this.startFrame = startFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Game.startStage1();    // 스테이지 1 시작
        startFrame.dispose();  // 시작 화면 닫기
    }
} 