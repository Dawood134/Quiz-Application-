import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class StudentWindow {
    JFrame studentFrame = new JFrame("Quiz Here");
    int qNum = 1;
    String enterQustion;
    int totalQuestions;
    String opt1;
    String opt2;
    String opt3;
    String opt4;
    String correctOpt;
    int studentScore;
    ButtonGroup buttonGroup;
    JLabel questionNumberLable,timerLable;
    JRadioButton radioButton1;
    JRadioButton radioButton2;
    JRadioButton radioButton3;
    JRadioButton radioButton4;
    JLabel questionLable;
    public static int timeLeft = 15;
    Timer timer;
    StudentWindow() {
        showQuestions();
        studentFrame.setLayout(null);
        studentFrame.setSize(1920, 1080);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = studentFrame.getContentPane();
        container.setBackground(new Color(40, 56, 126));
        timerLable=new JLabel();
        timerLable.setFont(new Font("Arial", Font.BOLD, 28));
        timerLable.setBounds(940,30,300,80);
        timerLable.setForeground(Color.cyan);

        studentFrame.add(timerLable);

        timer=new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;

            timerLable.setText("Time Left :"+timeLeft+" seconds");
            if (timeLeft<=0){
                JOptionPane.showMessageDialog(studentFrame, "Time's up for this question!");
                qNum++; // Move to the next question
                showQuestions(); // Load the next question from the database
                updateQuestion(); // Update the UI with the new question
                buttonGroup.clearSelection();
                timeLeft = 15;

            }

            }
        });
        timer.start();

        JLabel l6 = new JLabel("Quizard");
        l6.setBounds(50, 20, 300, 60);
        l6.setFont(new Font("Times New Roman", Font.BOLD, 64));
        l6.setForeground(new Color(122, 122, 241));
        questionLable = new JLabel();
        questionLable.setText(enterQustion);
        questionLable.setBounds(170, 40, 1000, 200);
        questionLable.setFont(new Font("Times New Roman", Font.BOLD, 34));
        questionLable.setForeground(Color.white);
        JLabel questionNo = new JLabel("Q.No:");
        questionNo.setBounds(20, 110, 100, 60);
        questionNo.setFont(new Font("Times New Roman", Font.BOLD, 34));
        questionNo.setForeground(Color.white);
        questionNumberLable = new JLabel(Integer.toString(qNum));
        questionNumberLable.setBounds(120, 125, 40, 30);
        questionNumberLable.setFont(new Font("Times New Roman", Font.BOLD, 34));
        questionNumberLable.setForeground(Color.white);
        radioButton1 = new JRadioButton(opt1);
        radioButton1.setActionCommand(opt1);
        radioButton1.setBounds(140, 250, 600, 50);
        radioButton1.setFont(new Font("Times New Roman", Font.BOLD, 34));

        radioButton2 = new JRadioButton(opt2);
        radioButton2.setActionCommand(opt2);
        radioButton2.setBounds(140, 350, 600, 50);
        radioButton2.setFont(new Font("Times New Roman", Font.BOLD, 34));

        radioButton3 = new JRadioButton(opt3);
        radioButton3.setActionCommand(opt3);
        radioButton3.setBounds(140, 450, 600, 50);
        radioButton3.setFont(new Font("Times New Roman", Font.BOLD, 34));

        radioButton4 = new JRadioButton(opt4);
        radioButton4.setActionCommand(opt4);
        radioButton4.setBounds(140, 550, 600, 50);
        radioButton4.setFont(new Font("Times New Roman", Font.BOLD, 34));

         buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        JButton buttonNext = new JButton("Next");
        buttonNext.setBounds(1000, 550, 120, 50);
        buttonNext.setFont(new Font("Times New Roman", Font.BOLD, 24));
        buttonNext.setBackground(Color.white);
        buttonNext.setForeground(new Color(0, 0, 0));
        Color defaultColor = buttonNext.getBackground();
        Color hover=new Color(10, 228, 221, 255);
        buttonNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                buttonNext.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                buttonNext.setBackground(defaultColor);
            }
        });

        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(buttonGroup.getSelection() != null){
                    String selectedOption = buttonGroup.getSelection().getActionCommand();
                    System.out.println(selectedOption);
                    timer.stop();

                    if(selectedOption.equals(correctOpt)){
                        studentScore++;
                        JOptionPane.showMessageDialog(studentFrame, "Correct Answer! "+"Score = "+studentScore+".");
                     timeLeft=15;

                    }
                    else {
                        JOptionPane.showMessageDialog(studentFrame, "Wrong Answer (correct = "+correctOpt+").");
                      timeLeft=15;
                    }
                    qNum++;

                    showQuestions();

                    updateQuestion();
                    buttonGroup.clearSelection();
                    timer.restart();

                }
                else {
                    JOptionPane.showMessageDialog(studentFrame, "No option selected.");

                }
            }
        });

        studentFrame.add(l6);
        studentFrame.add(questionLable);
        studentFrame.add(questionNo);
        studentFrame.add(questionNumberLable);
        studentFrame.add(radioButton1);
        studentFrame.add(radioButton2);
        studentFrame.add(radioButton3);
        studentFrame.add(radioButton4);
        studentFrame.add(buttonNext);
        studentFrame.setVisible(true);
    }

    void showQuestions() {
        String name = "root";
        String password = "t480s";
        String url = "jdbc:mysql://localhost:3306/quiz_db";

        String query = "select Q_No,question,option1,option2,option3,option4,correctOption from questions where Q_No= ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, name, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            String countQuery = "SELECT COUNT(*) AS totalQuestions FROM questions";
            try (Statement statement = connection.createStatement();
                 ResultSet countResult = statement.executeQuery(countQuery)) {

                if (countResult.next()) {
                    totalQuestions = countResult.getInt("totalQuestions");
                    System.out.println("Total Questions: " + totalQuestions);
                }
            }

            preparedStatement.setInt(1, qNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                enterQustion = resultSet.getString("question");
                opt1 = resultSet.getString("option1");
                opt2 = resultSet.getString("option2");
                opt3 = resultSet.getString("option3");
                opt4 = resultSet.getString("option4");
                correctOpt = resultSet.getString("correctOption");
            }
            System.out.println("Connection Successful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void updateQuestion(){
        if(qNum>totalQuestions) {
            timer.stop(); // Stop the timer
            JOptionPane.showMessageDialog(studentFrame, "Quiz Completed! Your final score is: " + studentScore);
            studentFrame.dispose(); // Close the JFrame
            System.exit(0);

        }
        questionLable.setText(enterQustion);
        radioButton1.setText(opt1);
        radioButton1.setActionCommand(opt1);
        radioButton2.setText(opt2);
        radioButton2.setActionCommand(opt2);
        radioButton3.setText(opt3);
        radioButton3.setActionCommand(opt3);
        radioButton4.setText(opt4);
        radioButton4.setActionCommand(opt4);
        questionNumberLable.setText(Integer.toString(qNum));

    }
}
