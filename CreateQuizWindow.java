import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateQuizWindow {

    JFrame CreateQuizFrame = new JFrame("Make Quiz");
    String enterQustion;
    String opt1;
    String opt2;
    String opt3;
    String opt4;
    String correctOpt;
    int questionNumber;

    CreateQuizWindow(){
        CreateQuizFrame.setLayout(null);
        CreateQuizFrame.setSize(1920, 1080);
        CreateQuizFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CreateQuizFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Container container = CreateQuizFrame.getContentPane();
        container.setBackground(new Color(40, 56, 126));
    JButton save = new JButton("Save");
        save.setForeground(new Color(0, 0, 0));
        save.setFont(new Font("Times New Roman", Font.BOLD, 24));

        save.setBounds(830, 550, 120, 50);
        save.setBackground(new Color(253, 253, 253));
        Color defaultColor = save.getBackground();
        Color hover=new Color(10, 228, 221, 255);
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                save.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                save.setBackground(defaultColor);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(990, 550, 120, 50);
        backButton.setBackground(new Color(253, 253, 253));
        backButton.setForeground(new Color(0, 0, 0));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 24));

        Color defaultColor2 = backButton.getBackground();
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                backButton.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                backButton.setBackground(defaultColor2);
            }
        });


        JLabel l6=new JLabel("Generate Quiz ");
        JLabel l7=new JLabel("MCQ's NO:");
        JLabel l8=new JLabel("Option 1 :");
        JLabel l9=new JLabel("Option 2 :");
        JLabel l10=new JLabel("Option 3 :");
        JLabel l11=new JLabel("Option 4 :");
        JLabel l12=new JLabel("Correct Option:");




        l6.setFont(new Font("Arial", Font.BOLD, 60));

        l6.setBounds(400, 30, 850, 80);
        l6.setForeground(new Color( 122, 122, 241));

        l7.setBounds(50,140,200,50);
        l7.setFont(new Font("Times New Roman", Font.BOLD, 24));
        l7.setForeground(new Color(253, 253, 253));

        l8.setBounds(50,270,200,50);
        l8.setFont(new Font("Times New Roman", Font.BOLD, 24));
        l8.setForeground(new Color(253, 253, 253));

        l9.setBounds(50,370,200,50);
        l9.setFont(new Font("Times New Roman", Font.BOLD, 24));
        l9.setForeground(new Color(253, 253, 253));

        l10.setBounds(700,270,200,50);
        l10.setFont(new Font("Times New Roman", Font.BOLD, 24));
        l10.setForeground(new Color(253, 253, 253));

        l11.setBounds(700,370,200,50);
        l11.setFont(new Font("Times New Roman", Font.BOLD, 24));
        l11.setForeground(new Color(253, 253, 253));

        l12.setBounds(50,550,200,50);
        l12.setFont(new Font("Times New Roman", Font.BOLD, 24));
        l12.setForeground(new Color(253, 253, 253));





        JTextArea questionTxtField=new JTextArea("Enter question here");
        JTextArea option1TxtField=new JTextArea();
        JTextArea option2TxtField=new JTextArea();
        JTextArea option3TxtField=new JTextArea();
        JTextArea option4TxtField=new JTextArea();
        JTextArea correctOptionTxtField=new JTextArea();
       JTextField mcqNoField=new JTextField();




        questionTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 24));

        questionTxtField.setLineWrap(true);
        questionTxtField.setWrapStyleWord(true);
        option1TxtField.setBounds(180,270,280,50);
        option1TxtField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        option2TxtField.setBounds(180,370,280,50);
        option2TxtField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        option3TxtField.setBounds(820,270,280,50);
        option3TxtField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        option4TxtField.setBounds(820,370,280,50);
        option4TxtField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        correctOptionTxtField.setBounds(240,550,280,50);
        correctOptionTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        mcqNoField.setBounds(230,150,30,30);
        mcqNoField.setFont(new Font("Times New Roman", Font.BOLD, 24));

        CreateQuizFrame.add(l6);
        CreateQuizFrame.add(l7);
        CreateQuizFrame.add(l8);
        CreateQuizFrame.add(l9);
        CreateQuizFrame.add(l10);
        CreateQuizFrame.add(l11);
        CreateQuizFrame.add(l12);

        JScrollPane scrollPane = new JScrollPane(questionTxtField);
        scrollPane.setBounds(330, 130, 600, 100);

        CreateQuizFrame.add(save);
        CreateQuizFrame.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateQuizFrame.dispose();
                ShowQuiz showQuiz=new ShowQuiz();
            }
        });
save.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        questionNumber = Integer.parseInt(mcqNoField.getText());

        enterQustion=questionTxtField.getText();
        opt1=option1TxtField.getText();
        opt2=option2TxtField.getText();
        opt3=option3TxtField.getText();
        opt4=option4TxtField.getText();
        correctOpt=correctOptionTxtField.getText();

        insertData( enterQustion,opt1, opt2, opt3,opt4,correctOpt, questionNumber);
        questionTxtField.setText("");
        option1TxtField.setText("");
        option2TxtField.setText("");
        option3TxtField.setText("");
        option4TxtField.setText("");
        correctOptionTxtField.setText("");
        mcqNoField.setText("");

    }
});

        CreateQuizFrame.add(scrollPane);
        CreateQuizFrame.add(option1TxtField);
        CreateQuizFrame.add(option2TxtField);
        CreateQuizFrame.add(option3TxtField);
        CreateQuizFrame.add(option4TxtField);
        CreateQuizFrame.add(correctOptionTxtField);
        CreateQuizFrame.add(mcqNoField);

        CreateQuizFrame.setVisible(true);
}
void insertData(String enterQustion, String opt1, String opt2, String opt3, String opt4, String correctOpt, int questionNumber){
    String name = "root";
    String password = "t480s";
    String url = "jdbc:mysql://localhost:3306/quiz_db";
    String query = "INSERT INTO questions(Q_No,question,option1,option2,option3,option4,correctOption)"+"VALUES(?,?,?,?,?,?,?)";
    try {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("driver loaded");
    } catch (ClassNotFoundException e) {
        System.out.println(e.getMessage());
    }
    try {
        Connection connection= DriverManager.getConnection(url,name,password);
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1,questionNumber);
        preparedStatement.setString(2,enterQustion);
        preparedStatement.setString(3,opt1);
        preparedStatement.setString(4,opt2);
        preparedStatement.setString(5,opt3);
        preparedStatement.setString(6,opt4);
        preparedStatement.setString(7,correctOpt);
        preparedStatement.executeUpdate();

    }catch (SQLException e){
        System.out.println(e.getMessage());
    }

}

}



