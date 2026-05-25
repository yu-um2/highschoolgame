package MiniGame;

import java.awt.Color;
import java.awt.Font;
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

import Frame.GameClearFrame;   
import Frame.GameOverFrame;

public class Quiz extends JFrame {

    private JPanel contentPanel;
    private JLabel questionLabel;
    private JTextPane answerPane;
    private JButton checkButton;

    private JLabel quizBackgroundLabel;

    private String answer = "크롱"; // 정답

    public Quiz() {
        setTitle("퀴즈");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(null);

        ImageIcon quizBackgroundIcon = new ImageIcon("image/stage4img/quizBackground.png");
        quizBackgroundLabel = new JLabel(quizBackgroundIcon);
        quizBackgroundLabel.setBounds(0, 0, 1200, 800);
        quizBackgroundLabel.setLayout(null);
        add(quizBackgroundLabel);

        contentPanel = new JPanel(null);
        contentPanel.setOpaque(false);
        contentPanel.setBounds(0, 0, 1200, 800);
        quizBackgroundLabel.add(contentPanel);

        quizScreen();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void quizScreen() {
        contentPanel.removeAll();

        questionLabel = new JLabel("뽀로로의 공룡 친구 이름은?", SwingConstants.CENTER);
        questionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        questionLabel.setForeground(new Color(60, 40, 20));
        questionLabel.setBounds(300, 250, 600, 50);
        contentPanel.add(questionLabel);

        answerPane = new JTextPane();
        answerPane.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        JScrollPane answerScroll = new JScrollPane(answerPane);
        answerScroll.setBounds(550, 400, 120, 50);
        contentPanel.add(answerScroll);

        answerPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    checkAnswer();
                }
            }
        });

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void checkAnswer() {
        String userInput = answerPane.getText().trim();

        if (userInput.equals(answer)) {
            showResultScreen(true);
        } else {
            showResultScreen(false);
        }
    }

    private void showResultScreen(boolean success) {

        contentPanel.removeAll();

        JLabel endLabel = new JLabel("", SwingConstants.CENTER);
        endLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        endLabel.setBounds(200, 150, 800, 80);
        endLabel.setForeground(new Color(60, 40, 20));

        if (success) {
            endLabel.setText("정답입니다! 최종 결과를 확인하세요!");
        } else {
            endLabel.setText("오답입니다. 최종 결과를 확인하세요!");
        }

        contentPanel.add(endLabel);

        JButton nextButton = new JButton("결과 확인");
        nextButton.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        nextButton.setForeground(new Color(40, 25, 15));
        nextButton.setBackground(new Color(219, 189, 132));
        nextButton.setBorder(BorderFactory.createLineBorder(new Color(100, 70, 40), 4));
        nextButton.setFocusPainted(false);
        nextButton.setOpaque(true);
        nextButton.setBounds(450, 350, 300, 80);

        // 성공/실패 
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (success) {
                    new GameClearFrame();   // 정답 -> 엔딩
                } else {
                    new GameOverFrame();    // 오답 -> 게임 오버
                }

                dispose(); // Quiz 창 닫기
            }
        });

        contentPanel.add(nextButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }


    public static void main(String[] args) {
        new Quiz();
    }
}
