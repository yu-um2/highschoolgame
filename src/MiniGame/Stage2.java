package MiniGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Frame.MainLifeFrame;
import Rule.Stage3Rule;

public class Stage2 extends JFrame {
	
	// 시간 제한 설정 (100초)
	private static final int TIME_LIMIT_MS =100000;

    private JLabel timerLabel;
    private JTextPane inputPane;
    private StyledDocument doc;
    
    private String currentPassage;
    private int currentPassageIndex = 0;	// 현재 지문의 인덱스
    private int remainingTime;
    private Timer timer;
    private boolean isGameStarted = false;	// 게임 시작 플래그
    private int score = 100;	// 기본점수
    
    private JLabel passageLabel;
    private JLabel preview1;
    private JLabel preview2;
    
    private JLabel backgroundLabel;	// 배경 이미지 라벨
    
    private static final int MIN_SCORE=70;	// 기본 점수

    private String correctPassage;
    
    private JButton nextButton;
        
    public Stage2() {
    	// 초기 상태 설정
    	currentPassageIndex = 0;
    	currentPassage = TypingPassages.getWrong()[currentPassageIndex];	// 틀린 문장
    	correctPassage = TypingPassages.getCorrect()[currentPassageIndex];	// 정답 문장
    	
        remainingTime = TIME_LIMIT_MS / 1000;
        
        // UI 설정
        setTitle("Korean Typing Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        
        // 배경 이미지 설정
        ImageIcon backgroundIcon = new ImageIcon("image/TypingBackground.png");
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1200, 800);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel);
             

        // 상단: 타이머        
        timerLabel = new JLabel("Time: " + remainingTime + "초 Score: " + score + "점", SwingConstants.RIGHT);
        timerLabel.setFont(new Font("함초롱바탕", Font.BOLD, 20));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(45, 0, 10, 220));
        backgroundLabel.add(timerLabel, BorderLayout.NORTH);
        
        // 중앙: 지문
        // 중앙 패널 생성
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 0, 100));

        
        // 현재 지문
        passageLabel = new JLabel(currentPassage, SwingConstants.LEFT);
        passageLabel.setFont(new Font("함초롱바탕", Font.BOLD, 22));
        passageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        centerPanel.add(passageLabel, BorderLayout.NORTH);
        
        // 미리보기 박스      
        JPanel previewBox = new JPanel(new GridLayout(2, 1, 5, 5));
        previewBox.setBackground(Color.WHITE);
        previewBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        preview1 = new JLabel("", SwingConstants.LEFT);
        preview1.setFont(new Font("함초롱바탕", Font.PLAIN, 18));
        preview1.setForeground(Color.GRAY);
        
        preview2 = new JLabel("", SwingConstants.LEFT);
        preview2.setFont(new Font("함초롱바탕", Font.PLAIN, 18));
        preview2.setForeground(Color.GRAY);
        
        previewBox.add(preview1);
        previewBox.add(preview2);
        
        centerPanel.add(previewBox, BorderLayout.CENTER);
        
        // 중앙 패널을 화면 중앙에 추가
        add(centerPanel, BorderLayout.CENTER);
        
        // 하단: 입력창
        inputPane = new JTextPane();
        inputPane.setFont(new Font("함초롱바탕", Font.PLAIN, 24));
        inputPane.setBorder(BorderFactory.createEmptyBorder(10, 60, 40, 60));
        JScrollPane inputScroll = new JScrollPane(inputPane);
        
        doc = inputPane.getStyledDocument();
        
        add(inputScroll, BorderLayout.SOUTH);
        
        // 이벤트 처리 (엔터 키 입력 시)
        inputPane.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			e.consume();
        			// 줄바꿈 방지
        			checkFullInput();
        		}
        	}
        });
        
        // 실시간 비교
        inputPane.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        	private void startTimerIfNeeded() {
        		if (!isGameStarted && inputPane.getText().length() > 0) {
        			timer.start();
        			isGameStarted = true;
        		}
        	}
        	
        	public void insertUpdate(DocumentEvent e) {
        		startTimerIfNeeded();
        		SwingUtilities.invokeLater(new Runnable() {	// .invokeLater를 안 쓰고 싶었는데 안 쓰면 오류남
        													// 작업을 UI 스레드 대기열에 넣기 기능이라 조금 나중에, 안전하게 실행해주는 역할								
        			@Override
        			public void run() {
        				checkRealtime();
        			}
        		});
        	}
        	
        	public void removeUpdate(DocumentEvent e) {
        		SwingUtilities.invokeLater(new Runnable() {
        			@Override
        			public void run() {
        				checkRealtime();
        			}
        		});
        	}
        	public void changedUpdate(DocumentEvent e) {}
        });
        
        // 타이머 설정
        timer = new Timer(1000, new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
                remainingTime--;
                timerLabel.setText("Time: " + remainingTime + "초 Score: " + score + "점");
                if (remainingTime <= 0) {
                    timer.stop();
                    endGame(false, "시간 종료");	// 시간 종료

                }
            }
        });
        
        // 모든 컴포넌트를 배경 패널에 추가        
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.add(timerLabel, BorderLayout.NORTH);
        backgroundLabel.add(centerPanel, BorderLayout.CENTER);
        backgroundLabel.add(new JScrollPane(inputPane), BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
        	@Override
        	public void run() {
        		inputPane.requestFocusInWindow();
        	}
        });
        
        updatePassageLines();
    }
    
        // 실시간 비교 및 틀린 글자 빨갛게 표시
        private void checkRealtime() {
        	String user = inputPane.getText();
        	String correct = correctPassage;
        	
        	SimpleAttributeSet normal = new SimpleAttributeSet();
        	StyleConstants.setForeground(normal, Color.BLACK);
        	
        	SimpleAttributeSet wrong = new SimpleAttributeSet();
        	StyleConstants.setForeground(wrong, Color.RED);
        	
        	int len = user.length();
        	
        	// 전체를 정상으로 초기화
        	if (len > 0) {
        		doc.setCharacterAttributes(0, len, normal, true);
        	}
        	
        	// 틀린 곳만 빨간색
        	for (int i = 0; i < len; i++) {
        		if (i >= correct.length()) {
        			// target 보다 길면 입력 초과-> 틀림
        			doc.setCharacterAttributes(i, 1, wrong, true);
        			continue;
        		}
        		
        		// 맞춤법 고침
        		if (user.charAt(i) == correct.charAt(i)) {
        			doc.setCharacterAttributes(i,  1,  normal,  true);
        			
        		} else {
        			// 아직 틀림
        			doc.setCharacterAttributes(i, 1, wrong, true);
        		}
        	}
     
        }
        
        private void updatePassageLines() {
        	String wrong = TypingPassages.getWrong()[currentPassageIndex];
        	String correct = TypingPassages.getCorrect()[currentPassageIndex];
        	
        	StringBuilder sb =new StringBuilder("<html>");
        	
        	for (int i = 0; i < wrong.length(); i++) {
        		if (i >= correct.length()) {
        			sb.append("<font color='red'>").append(wrong.charAt(i)).append("</font>");
        			continue;
        		}
        		if (wrong.charAt(i) != correct.charAt(i)) {
        			sb.append("<font color='red'>").append(wrong.charAt(i)).append("</font>");
        		} else {
        			sb.append(wrong.charAt(i));
        		}
        	}
        	
        	sb.append("</html>");
        	
        	passageLabel.setText(sb.toString());
        	
        	// 미리보기
        	String[] wrongArr = TypingPassages.getWrong();
        	
        	preview1.setText(currentPassageIndex + 1 < wrongArr.length ? wrongArr[currentPassageIndex + 1] : "");
        	preview2.setText(currentPassageIndex + 2 < wrongArr.length ? wrongArr[currentPassageIndex + 2] : "");
        	
        }
     
       
        // 엔터로 문장 정답 체크
        private void checkFullInput() {
        	String user = inputPane.getText().trim();
        	String target = correctPassage.trim();
        	
        	boolean hasWrong = false; 	// 틀린 글자 남아있는지 체크
        	int len = Math.min(user.length(), target.length());
        	for (int i = 0; i < len; i++) {
        		if (user.charAt(i) != target.charAt(i)) {
        			hasWrong = true;
        			break;
        		}
        	}	
        	// 길이가 다르면 이것도 틀린 상태로 간주
        	if (user.length() != target.length()) {
        		hasWrong = true;
        	}
        	// 틀렸으면 엔터시 추가 패널티
        	if (hasWrong) {
        		score -= 5;	// 패널티 점수 수치 조정 가능
        		remainingTime -= 2;	// 패널티 시간 수치 조정 가능
        		timerLabel.setText("Time: " + remainingTime + "초 Score: " + score + "점");
        	}
        	
        	// 맞춤법 맞으면 보너스 점수
        	if (user.equals(correctPassage)) {
        		score += 5;
        	}
        	// 무조건 다음 문장으로 넘어감
        	goToNextPassage();
        }
        
        
        // 다음 문장으로 이동
        private void goToNextPassage() {
        	currentPassageIndex++;
        	if (currentPassageIndex >= TypingPassages.getWrong().length) {
        		// 기준 점수 판단
        		if (score >= MIN_SCORE) {
        			// 기준 점수 통과 -> 성공
        			endGame(true, "");
        		} else {
        			// 기준 점수 미달 -> 실패
        			endGame(false, "점수 미달");
        		}
        		return;
        	}
        	// 다음 지문으로 이동
        	currentPassage = TypingPassages.getWrong()[currentPassageIndex];
        	correctPassage = TypingPassages.getCorrect()[currentPassageIndex];
          	updatePassageLines();
        	inputPane.setText("");
        	inputPane.requestFocusInWindow();
        }
        
        private void endGame(boolean success, String reason) {

            // 입력 및 타이머 종료
            timer.stop();
            inputPane.setEnabled(false);

            // backgroundLabel 초기화
            backgroundLabel.removeAll();
            backgroundLabel.setLayout(new BorderLayout());

            // 결과 텍스트
            JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
            resultLabel.setFont(new Font("함초롱바탕", Font.BOLD, 28));
            resultLabel.setBorder(BorderFactory.createEmptyBorder(100, 100, 0, 100));

            // 성공 / 실패 메시지
            if (success) {
                resultLabel.setText("성공! 최종 점수: " + score + "점");
            } else {
                resultLabel.setText("실패(" + reason + ")  최종 점수: " + score + "점");
                MainLifeFrame.decreaseLife();   // 실패 시 메인 목숨 감소
            }

            backgroundLabel.add(resultLabel, BorderLayout.NORTH);

            // 다음단계 버튼
            JButton endButton = new JButton("다음 단계로");
            endButton.setFont(new Font("함초롱바탕", Font.BOLD, 35));
            endButton.setPreferredSize(new Dimension(300, 120));
            endButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            endButton.setFocusPainted(false);

            // 중앙 정렬 패널
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setOpaque(false);
            centerPanel.add(endButton);

            backgroundLabel.add(centerPanel, BorderLayout.CENTER);

            // Stage3Rule로 이동
            endButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Stage3Rule(null);
                    dispose();
                }
            });

            revalidate();
            repaint();
        }

               
    public static void main(String[] args) {
        new Stage2();
    }
}