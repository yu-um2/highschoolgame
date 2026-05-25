package Function;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Frame.StageFrame;
import Rule.Stage1Rule;
import Rule.Stage2Rule;
import Rule.Stage3Rule;
import Rule.Stage4Rule;

public class StageListener implements ActionListener {

    private StageFrame stageFrame;
    private int stageNumber; // 1~4 교시 구분

    public StageListener(StageFrame frame, int stageNumber) {
        this.stageFrame = frame;
        this.stageNumber = stageNumber;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // 룰창 먼저 띄우고 StageFrame은 숨기기.
        switch(stageNumber) {
            case 1: new Stage1Rule(stageFrame); break;
            case 2: new Stage2Rule(stageFrame); break;
            case 3: new Stage3Rule(stageFrame); break;
            case 4: new Stage4Rule(stageFrame); break;
        }

        stageFrame.setVisible(false); // Rule 열리면 StageFrame 숨김
    }
}