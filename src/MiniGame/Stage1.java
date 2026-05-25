package MiniGame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Frame.StageFrame;
import Rule.Stage2Rule;
import Frame.MainLifeFrame;




public class Stage1 extends JFrame {

    private final String[] symbolList = {"+", "-", "×", "÷"};	// 기호 목록
    private ArrayList<String> correctSequence = new ArrayList<>(); // NPC가 보여줄 정답 시퀀스
    private ArrayList<String> playerSequence = new ArrayList<>();	// 플레이어의 입력 시퀀스
    
    // 주요 UI 구성요소    
    private JButton startBtn, checkAnswerBtn, resetInputBtn, nextStageBtn;
    private JPanel inputButtonPanel;	// 입력 버튼 패널
    private JPanel centerPanel;	// 입력 영역과 다음 스테이지 버튼 바꿔 보여줄 패널
    private JPanel nextStagePanel;
    
    private JLabel npcDisplayLabel;	// 화면 중앙 표시용 텍스트
    private JLabel statusLabel;	// 상태 메시지 표현 라벨
    private JLabel levelStatusLabel;	// 레벨 표시 라벨
    private JLabel lifeStatusLabel;	// 목숨 표시용 라벨
    private JLabel backgroundLabel;	// 배경 이미지 라벨
    
    private ImageIcon[] lifeIcons = new ImageIcon[4];	// 목숨 이미지 배열
    private ImageIcon[] levelIcons = new ImageIcon[5];	// level1~level5
    
    // 게임 상태 변수
    private int sequenceIndex = 0;	// 현재 시퀀스 인덱스
    private int currentLevel = 1;	// 현재 게임 레벨
    private int remainingLives = 3;	// 남은 목숨
    private boolean wasPreviousWrong = false; 	// 직전 라운드에서 오답 여부 저장
    private boolean canPlayerInput = false; 

    public Stage1() {
        setTitle("Math Symbol Memory Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(null);	// 절대 위치 레이아웃 사용
        
        // 배경 이미지 설정
        ImageIcon backgroundIcon = new ImageIcon("image/stage1img/symbolGameBackground.jpg");	// 배경 이미지 파일
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1200, 800);
        backgroundLabel.setLayout(null);
        add(backgroundLabel);
        
        // 레벨 이미지 로드
        loadLevelImages();
        
        // 목숨 이미지 로드
        loadLifeImages();
        
        // 상단 표시 영역
        npcDisplayLabel = new JLabel("버튼을 눌러 게임을 시작하세요!", SwingConstants.CENTER);
        npcDisplayLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        npcDisplayLabel.setBounds(300, 50, 600, 80);
        npcDisplayLabel.setOpaque(false);
        ImageIcon displayIcon = new ImageIcon("image/stage1img/label.png");
        Image displayImg = displayIcon.getImage().getScaledInstance(600, 180, Image.SCALE_SMOOTH);
        npcDisplayLabel.setIcon(new ImageIcon(displayImg));
        npcDisplayLabel.setHorizontalTextPosition(JLabel.CENTER);
        npcDisplayLabel.setVerticalTextPosition(JLabel.CENTER);
       
        // 글씨 색상
        npcDisplayLabel.setForeground(new Color(68, 45, 34));	// 텍스트 색상 흰색
        
        // 레벨 표시 라벨
        levelStatusLabel = new JLabel();
        levelStatusLabel.setBounds(400, 150, 200, 80);
        updateLevelImage(1);

        // 목숨 표시 라벨 (이미지)
        lifeStatusLabel = new JLabel();
        lifeStatusLabel.setBounds(650, 150, 200, 80);
        updateLifeDisplay();	// 초기 목숨 이미지 설정
        
        // 중앙 버튼 영역 (플레이어 입력 버튼) - 이미지 버튼으로 변경
        inputButtonPanel = new JPanel();
        inputButtonPanel.setLayout(null);	// 절대 위치
        inputButtonPanel.setBounds(350, 250, 500, 300);
        inputButtonPanel.setOpaque(false);	// 투명 배경
        
        // 수학 기호 이미지 버튼 생성
        String[] imageFiles = {"image/stage1img/plus.png", 
        		"image/stage1img/minus.png", 
        		"image/stage1img/multiply.png",
        		"image/stage1img/divide.png"};
        int[][] positions = {{50, 50}, {270, 50}, {50, 180}, {270, 180}};	// 버튼 위치
        
        for (int i = 0; i < symbolList.length; i++) {
            String symbol = symbolList[i];
            
            // 이미지 아이콘 로드
            ImageIcon symbolBtn = new ImageIcon(imageFiles[i]);
            Image symbolImg = symbolBtn.getImage().getScaledInstance(150, 140, Image.SCALE_SMOOTH);	// 이미지 자르기
            ImageIcon scaledIcon = new ImageIcon(symbolImg);
            
            JButton btn = new JButton(scaledIcon);
            btn.setBounds(positions[i][0], positions[i][1], 160, 110);
            btn.setBorderPainted(false);	// 테두리 제거
            btn.setContentAreaFilled(false);	// 배경 제거
            btn.setFocusPainted(false);	// 포커스 테두리 제거
            
            // 마우스 오버 효과 => 마우스 올렸을때 손가락 모양 포인터로 바뀜. 떼면 돌아옴.
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
            
            
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handlePlayerInput(symbol);
                }
            });
            inputButtonPanel.add(btn);
        }

        // 하단 결과 라벨
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
        statusLabel.setBounds(300, 600, 600, 60);
        statusLabel.setOpaque(true);
        ImageIcon statusIcon = new ImageIcon("image/stage1img/text.png");
        Image statusImg = statusIcon.getImage().getScaledInstance(600, 180, Image.SCALE_SMOOTH);
        statusLabel.setIcon(new ImageIcon(statusImg));
        statusLabel.setHorizontalTextPosition(JLabel.CENTER);
        statusLabel.setVerticalTextPosition(JLabel.CENTER);
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setOpaque(false);
        
        // 게임 시작 및 결과 확인 버튼
        ImageIcon startBtnIcon = new ImageIcon("image/stage1img/gamestart.png");
        Image startImg = startBtnIcon.getImage().getScaledInstance(190, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledStartIcon = new ImageIcon(startImg);
        startBtn = new JButton(scaledStartIcon);
        startBtn.setBounds(350, 680, 150, 50);
        startBtn.setBorderPainted(false);
        startBtn.setContentAreaFilled(false);
        startBtn.setFocusPainted(false);
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));	// 마우스 손 모양
        
        ImageIcon checkBtnIcon = new ImageIcon("image/stage1img/checkanswer.png");
        Image checkImg = checkBtnIcon.getImage().getScaledInstance(190, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledCheckIcon = new ImageIcon(checkImg);
        checkAnswerBtn = new JButton(scaledCheckIcon);
        checkAnswerBtn.setBounds(525, 680, 150, 50);
        checkAnswerBtn.setBorderPainted(false);
        checkAnswerBtn.setContentAreaFilled(false);
        checkAnswerBtn.setFocusPainted(false);
        checkAnswerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        ImageIcon resetBtnIcon = new ImageIcon("image/stage1img/clear.png");
        Image resetImg = resetBtnIcon.getImage().getScaledInstance(190, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledResetIcon = new ImageIcon(resetImg);
        resetInputBtn = new JButton(scaledResetIcon);
        resetInputBtn.setBounds(700, 680, 150, 50);
        resetInputBtn.setBorderPainted(false);
        resetInputBtn.setContentAreaFilled(false);
        resetInputBtn.setFocusPainted(false);
        resetInputBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // 다음 스테이지 버튼
        ImageIcon nextBtnIcon = new ImageIcon("image/stage1img/next.png");
        Image nextImg = nextBtnIcon.getImage().getScaledInstance(190, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledNextIcon = new ImageIcon(nextImg);
        nextStageBtn = new JButton(scaledNextIcon);
        nextStageBtn.setBounds(475, 400, 250, 60);
        nextStageBtn.setBorderPainted(false);
        nextStageBtn.setContentAreaFilled(false);
        nextStageBtn.setFocusPainted(false);
        nextStageBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextStageBtn.setVisible(false);
        
        // 시작 버튼 리스너
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        
        // 결과 확인 버튼 리스너
        checkAnswerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        
        // 입력 초기화 버튼 리스너
        resetInputBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetInput();
            }
        });

        // 모든 컴포넌트를 배경 패널에 추가        
        backgroundLabel.add(npcDisplayLabel);
        backgroundLabel.add(levelStatusLabel);
        backgroundLabel.add(lifeStatusLabel);
        backgroundLabel.add(inputButtonPanel);
        backgroundLabel.add(statusLabel);
        backgroundLabel.add(startBtn);
        backgroundLabel.add(checkAnswerBtn);
        backgroundLabel.add(resetInputBtn);
        backgroundLabel.add(nextStageBtn);
        
        setVisible(true);
    }
    
    // 목숨 이미지 로드 메서드
    private void loadLifeImages() {
    	String[] lifeImageFiles = {"image/stage1img/heart0.png", 
    			"image/stage1img/heart1.png", 
    			"image/stage1img/heart2.png", 
    			"image/stage1img/heart3.png"};
    	
    	for (int i = 0; i < 4; i++) {
    			ImageIcon icon = new ImageIcon(lifeImageFiles[i]);
    			// 이미지 크기 조절
    			Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    			lifeIcons[i] = new ImageIcon(img);
    	}
    }
    
    // 목숨 표시 업데이트 메서드
    private void updateLifeDisplay() {
    	lifeStatusLabel.setIcon(lifeIcons[remainingLives]);
    	lifeStatusLabel.setText("");
    	lifeStatusLabel.setOpaque(false);
    	lifeStatusLabel.revalidate();
    	lifeStatusLabel.repaint();
    }
    
    // 레벨 이미지 로드
    private void loadLevelImages() {
    	String[] levelImageFiles = {"image/stage1img/level1.png",
    			"image/stage1img/level2.png", 
    			"image/stage1img/level3.png", 
    			"image/stage1img/level4.png", 
    			"image/stage1img/level5.png"};
    	
    	for (int i= 0; i < 5; i++) {
    		ImageIcon icon = new ImageIcon(levelImageFiles[i]);
    		Image img = icon.getImage().getScaledInstance(220,  100, Image.SCALE_SMOOTH);
    		levelIcons[i] = new ImageIcon(img);
    	}
    }
    
    private void updateLevelImage(int level) {
    	if (level >= 1 && level <= 5) {
    		levelStatusLabel.setIcon(levelIcons[level - 1]);
    		levelStatusLabel.setText("");
    		levelStatusLabel.setOpaque(false);
    		levelStatusLabel.revalidate();
    		levelStatusLabel.repaint();
    	}
    }
    


    // 게임 시작
    private void startGame() {
        if (wasPreviousWrong) {
            remainingLives--;
            wasPreviousWrong = false;
            updateLifeDisplay();	// 이미지 갱신
            
            if (remainingLives <= 0) {

                // 메인 목숨 -1
                MainLifeFrame.decreaseLife();

                hideAllButtons();
                inputButtonPanel.setVisible(false);
                nextStageBtn.setVisible(true);

                npcDisplayLabel.setText("GAME OVER"); //유지보수용.
                statusLabel.setText("게임 오버! 다음 스테이지로 넘어갑니다.");

                canPlayerInput = false;

                // Stage2 Rule 화면 띄우기.
                new Stage2Rule(null);

                dispose();   
                return;
            }

        }
        
        
        showDefaultButtons();
        showInputButtons();
        
        canPlayerInput = false;
        statusLabel.setText("기호를 기억하세요!");
        correctSequence.clear();
        playerSequence.clear();
        
        // 기호 깜빡이
        int display = 1000;
        int blank = 500;
        int sequenceLength = 3;
        
        switch (currentLevel) {
            case 1: display = 1000; blank = 500; sequenceLength = 3; break;
            case 2: display = 800; blank = 400; sequenceLength = 3; break;
            case 3: display = 800; blank = 400; sequenceLength = 4; break;
            case 4: display = 600; blank = 300; sequenceLength = 4; break;
            case 5: display = 600; blank = 300; sequenceLength = 5; break;
            default:
                statusLabel.setText("모든 단계를 클리어했습니다!");
                hideAllButtons();
                return;
        }

        // 기호 랜덤으로 띄우기.
        Random rand = new Random();
        for (int i = 0; i < sequenceLength; i++) {
            correctSequence.add(symbolList[rand.nextInt(symbolList.length)]);
        }

        sequenceIndex = 0;
        Timer showSymbolTimer = new Timer(display, null);
        Timer symbolBlankTimer = new Timer(blank, null);

        symbolBlankTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                symbolBlankTimer.stop();
                
                if (sequenceIndex < correctSequence.size()) {
                    npcDisplayLabel.setText(correctSequence.get(sequenceIndex));
                    sequenceIndex++;
                    showSymbolTimer.start();
                } else {
                    npcDisplayLabel.setText("이제 버튼을 눌러 입력하세요!");
                    canPlayerInput = true;
                    statusLabel.setText("입력을 완료하고 결과를 확인하세요.");
                }
            }
        });
        
        showSymbolTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSymbolTimer.stop();
                npcDisplayLabel.setText("");
                symbolBlankTimer.start();
            }
        });
            
        npcDisplayLabel.setText(correctSequence.get(sequenceIndex));
        sequenceIndex++;
        showSymbolTimer.start();
    }
    
    private void handlePlayerInput(String input) {
        if (!canPlayerInput) { 
            return;
        }
        
        playerSequence.add(input);
        npcDisplayLabel.setText("입력: " + String.join(" ", playerSequence));
    }
    
    private void checkAnswer() {
        canPlayerInput = false;
        
        if (correctSequence.isEmpty()) {
            statusLabel.setText("게임을 먼저 시작하세요!");
            return;
        }

        if (playerSequence.size() != correctSequence.size()) {
            wasPreviousWrong = true;
            statusLabel.setText("<html>오답! 입력된 길이가 달라요<br>정답은 " + String.join(" ", correctSequence) + " 이었어요.</html>");
            playerSequence.clear();
            return;
        }
        
        if (playerSequence.equals(correctSequence)) {
            wasPreviousWrong = false;
            
            if (currentLevel < 5) {
                currentLevel++;
                updateLevelImage(currentLevel);
                statusLabel.setText("정답입니다! 다음 라운드를 시작하세요.");
                showDefaultButtons();
                showInputButtons();
            } else {
                hideAllButtons();
                inputButtonPanel.setVisible(false);
                nextStageBtn.setVisible(true);
                statusLabel.setText("<html>축하합니다! 모든 레벨을 클리어했습니다!<br>다음 스테이지를 진행하세요 </html>");//유지보수용.
                canPlayerInput = false;
                
                new Stage2Rule(null); //Stage2Rule 띄우기.

               dispose();   
                return;
            }
        } else {
            wasPreviousWrong = true;
            statusLabel.setText("<html>오답! 정답은 " + String.join(" ", correctSequence) + " 이었어요.");
        }
        
        playerSequence.clear();
    } 

    private void resetInput() {
        if (!canPlayerInput) {
            statusLabel.setText("지금은 입력할 수 없습니다!");
            return;
        }
        playerSequence.clear();
        npcDisplayLabel.setText("초기화 완료! 다시 입력하세요.");
    }
    
    private void hideAllButtons() {
        startBtn.setVisible(false);
        checkAnswerBtn.setVisible(false);
        resetInputBtn.setVisible(false);
    }
    
    private void showDefaultButtons() {
        startBtn.setVisible(true);
        checkAnswerBtn.setVisible(true);
        resetInputBtn.setVisible(true);
    }
    
    private void showInputButtons() {
        inputButtonPanel.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Stage1();
    }
}