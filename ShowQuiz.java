import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ShowQuiz {
    JButton Next, Back, DeleteButton;
    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;

    ShowQuiz() {
        // Create and set up the frame
        JFrame showQuizFrame = new JFrame("Teacher Frame");
        showQuizFrame.setLayout(null);
        showQuizFrame.setVisible(true);
        showQuizFrame.setSize(1920, 1080);
        showQuizFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showQuizFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Container and background color
        Container container = showQuizFrame.getContentPane();
        container.setBackground(new Color(40, 56, 126));

        // Title label
        JLabel textShowQuiz = new JLabel("Review Quiz MCQs");
        textShowQuiz.setFont(new Font("Arial", Font.BOLD, 60));
        textShowQuiz.setBounds(380, 60, 900, 80);
        textShowQuiz.setForeground(new Color(244, 244, 246));

        // Table setup
        tableModel = new DefaultTableModel();
        tableModel.addColumn("MCQ No");
        tableModel.addColumn("Question");
        tableModel.addColumn("Option 1");
        tableModel.addColumn("Option 2");
        tableModel.addColumn("Option 3");
        tableModel.addColumn("Option 4");
        tableModel.addColumn("Correct Answer");

        table = new JTable(tableModel);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        table.setBackground(new Color(253, 253, 253));

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(200, 200, 900, 300);

        // "Create Quiz" button
        Next = new JButton("Create Quiz");
        Next.setBounds(380, 550, 160, 50);
        Next.setFont(new Font("Times New Roman", Font.BOLD, 24));
        Next.setBackground(Color.cyan);

        // Hover effect for "Create Quiz" button
        Color defaultColor = Next.getBackground();
        Color hoverColor = new Color(10, 228, 221, 255);
        Next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Next.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Next.setBackground(defaultColor);
            }
        });

        // "Back" button
        Back = new JButton("Back");
        Back.setBounds(590, 550, 120, 50);
        Back.setFont(new Font("Times New Roman", Font.BOLD, 24));
        Back.setBackground(Color.cyan);

        // Hover effect for "Back" button
        Back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Back.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Back.setBackground(defaultColor);
            }
        });

        // "Delete All" button
        DeleteButton = new JButton("Delete");
        DeleteButton.setBounds(750, 550, 120, 50);
        DeleteButton.setFont(new Font("Times New Roman", Font.BOLD, 24));
        DeleteButton.setBackground(Color.cyan);

        // Hover effect for "Delete All" button
        DeleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                DeleteButton.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                DeleteButton.setBackground(defaultColor);
            }
        });

        // Add components to the frame
        showQuizFrame.add(textShowQuiz);
        showQuizFrame.add(Next);
        showQuizFrame.add(DeleteButton);
        showQuizFrame.add(Back);
        showQuizFrame.add(scrollPane);

        // Fetch questions to populate the table
        fetchQuestions();

        // Button action listeners
        Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showQuizFrame.dispose();
                new CreateQuizWindow();
            }
        });

        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showQuizFrame.dispose();
                new LoginWindow();
            }
        });

        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteQuestions();
            }
        });
    }

    // Method to fetch questions and populate the JTable
    public void fetchQuestions() {
        String name = "root";
        String password = "t480s";
        String url = "jdbc:mysql://localhost:3306/quiz_db";
        String query = "SELECT * FROM questions";

        try {
            Connection connection = DriverManager.getConnection(url, name, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Add rows to the table model
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("Q_No"),
                        resultSet.getString("question"),
                        resultSet.getString("option1"),
                        resultSet.getString("option2"),
                        resultSet.getString("option3"),
                        resultSet.getString("option4"),
                        resultSet.getString("correctOption"),
                });
            }

            connection.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    // Method to delete all records from the database and clear the JTable
    public void deleteQuestions() {
        String name = "root";
        String password = "t480s";
        String url = "jdbc:mysql://localhost:3306/quiz_db";
        String query = "DELETE FROM questions";

        try {
            Connection connection = DriverManager.getConnection(url, name, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            tableModel.setRowCount(0);

            connection.close();
        } catch ( SQLException e) {
            e.getMessage();
        }
    }
}
