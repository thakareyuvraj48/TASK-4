pimport javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApp {
    private static String[][] questions = {
        {"What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome"},
        {"What is 5 + 7?", "10", "11", "12", "13"},
        {"Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Venus"}
    };
    private static String[] answers = {"Paris", "12", "Mars"};
    
    private static int index = 0;
    private static int score = 0;
    private static int timeLimit = 10;
    private static Timer timer = new Timer();
    private static int timeLeft;

    private static JLabel questionLabel;
    private static JRadioButton[] options = new JRadioButton[4];
    private static JButton submitButton;
    private static JLabel timerLabel;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create components
        questionLabel = new JLabel();
        ButtonGroup group = new ButtonGroup();
        
        JPanel optionsPanel = new JPanel();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            optionsPanel.add(options[i]);
        }

        submitButton = new JButton("Submit");
        timerLabel = new JLabel("Time left: " + timeLimit);
        
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });

        // Add components to frame
        frame.add(questionLabel, "North");
        frame.add(optionsPanel, "Center");
        frame.add(submitButton, "South");
        frame.add(timerLabel, "West");

        // Initialize first question
        loadNextQuestion();

        frame.setVisible(true);
    }

    private static void loadNextQuestion() {
        if (index >= questions.length) {
            showResults();
            return;
        }

        questionLabel.setText(questions[index][0]);
        for (int i = 0; i < 4; i++) {
            options[i].setText(questions[index][i + 1]);
            options[i].setSelected(false); // Clear previous selection
        }

        timeLeft = timeLimit;
        timerLabel.setText("Time left: " + timeLeft);
        
        // Start the countdown timer
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time left: " + timeLeft);
                } else {
                    timer.cancel(); // Stop the timer
                    submitAnswer(); // Auto-submit when time runs out
                }
            }
        }, 1000, 1000);
    }

    private static void submitAnswer() {
        timer.cancel(); // Stop the timer

        // Check selected answer
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected() && options[i].getText().equals(answers[index])) {
                score++;
            }
        }

        index++;
        timer = new Timer(); // Reset the timer for the next question
        loadNextQuestion(); // Load the next question
    }

    private static void showResults() {
        // Display the final score and exit
        JOptionPane.showMessageDialog(null, "Quiz over! Your score is: " + score + "/" + questions.length);
        System.exit(0);
    }
}