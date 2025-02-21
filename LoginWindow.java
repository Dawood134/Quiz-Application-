import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.border.AbstractBorder;


 class RoundedBorder extends AbstractBorder {
     private int cornerRadius;
     private int thickness;

     public RoundedBorder(int cornerRadius, int thickness) {
         this.cornerRadius = cornerRadius;
         this.thickness = thickness;
     }

     @Override
     public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
         Graphics2D g2 = (Graphics2D) g;
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

         // Set the stroke thickness
         g2.setStroke(new BasicStroke(thickness));
         g2.setColor(Color.WHITE); // Border color

         // Draw the rounded rectangle
         g2.drawRoundRect(
                 x + thickness / 2,
                 y + thickness / 2,
                 width - thickness,
                 height - thickness,
                 cornerRadius,
                 cornerRadius
         );
     }

     @Override
     public Insets getBorderInsets(Component c) {
         return new Insets(thickness, thickness, thickness, thickness);
     }

     @Override
     public Insets getBorderInsets(Component c, Insets insets) {
         insets.left = thickness;
         insets.top = thickness;
         insets.right = thickness;
         insets.bottom = thickness;
         return insets;
     }
 }


public class LoginWindow {
    JFrame loginFrame;
    JPanel panel;
    JLabel l1,  l3;
    JButton loginButton, studentButton;
    JTextField userNameTextField;
    String userName, password;
JPasswordField  passwordTextField;
    LoginWindow() {
        loginFrame = new JFrame("Quiz Application");
        loginFrame.setLayout(null);
        loginFrame.setSize(1920, 1080);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Container container = loginFrame.getContentPane();
        container.setBackground(new Color(40, 56, 126));
        JLabel textQuizWelcome = new JLabel("Quizard");

        textQuizWelcome.setFont(new Font("Arial", Font.BOLD, 78));


        textQuizWelcome.setBounds(150, 160, 300, 100);
        textQuizWelcome.setForeground(new Color(244, 244, 246));

        JLabel textQuizWelcome2 = new JLabel("Test your knowledge, challenge your mind, and have");
        textQuizWelcome2.setBounds(120, 310, 750, 80);
        textQuizWelcome2.setForeground(new Color(244, 244, 246));
        textQuizWelcome2.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel textQuizWelcome3 = new JLabel(" fun while learning with engaging trivia questions!");
        textQuizWelcome3.setBounds(120, 370, 750, 80);
        textQuizWelcome3.setForeground(new Color(244, 244, 246));
        textQuizWelcome3.setFont(new Font("Arial", Font.BOLD, 20));

        loginFrame.add(textQuizWelcome);
        loginFrame.add(textQuizWelcome2);
        loginFrame.add(textQuizWelcome3);
        panel = new JPanel();
        userNameTextField = new JTextField();

        passwordTextField= new JPasswordField();
        loginButton = new JButton("Login");
        loginButton.setBounds(70, 350, 100, 50);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 24));
        loginButton.setBackground(Color.white);
        loginButton.setForeground(new Color(0, 0, 0));

        Color defaultColor = loginButton.getBackground();
        Color hover=new Color(10, 228, 221, 255);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                loginButton.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                loginButton.setBackground(defaultColor);
            }
        });
        studentButton = new JButton("Start Quiz");
        studentButton.setBounds(210, 350, 130, 50);
        studentButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        studentButton.setBackground(Color.white);
        studentButton.setForeground(new Color(0, 0, 0));

        studentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                studentButton.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                studentButton.setBackground(defaultColor);
            }
        });


        userNameTextField.setBounds(70, 110, 250, 35);
        userNameTextField.setFont(new Font("Times New Roman", Font.BOLD, 18));

        Border border = BorderFactory.createLineBorder(new Color(6, 12, 48), 4);
        userNameTextField.setBorder(border);

        passwordTextField.setBounds(70, 240, 250, 35);
        passwordTextField.setBorder(border);
        l1 = new JLabel("User Name:");
        l3 = new JLabel("Enter Password:");

        l1.setBounds(70, 80, 200, 20);
        l1.setFont(new Font("Arial", Font.BOLD, 18));
         l1.setForeground(new Color(244, 244, 246));
        l3.setBounds(70, 210, 200, 20);
        l3.setFont(new Font("Arial", Font.BOLD, 18));
        l3.setForeground(new Color(244, 244, 246));

        // Make the panel non-opaque so the background is visible

        panel.setOpaque(false);

        panel.setBorder(new RoundedBorder(100,7));
        panel.setLayout(null);
        panel.setBounds(770, 100, 400, 450);
        panel.add(userNameTextField);
        panel.add(passwordTextField);
        panel.add(l1);
        panel.add(l3);
        panel.add(loginButton);
        panel.add(studentButton);
        loginFrame.add(panel);

        loginFrame.setVisible(true);

        // Login Button Logic
        loginButton.addActionListener(e -> {
            userName = userNameTextField.getText();
            password = passwordTextField.getText();
            if (userName.equals("") && password.equals("")) {
                loginFrame.dispose();
                ShowQuiz showQuiz=new ShowQuiz();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Credentials");
                userNameTextField.setText("");
                passwordTextField.setText("");
            }
        });

        // Student Button Logic
        studentButton.addActionListener(e -> openStudentWindow());
    }

    // Method to open Teacher Frame
    private void openCreateQuizWindow() {
        loginFrame.dispose();
        CreateQuizWindow createQuizWindow = new CreateQuizWindow();
    }

    // Method to open Student Frame
    private void openStudentWindow() {
        loginFrame.dispose();
        StudentWindow studentWindow = new StudentWindow();
    }
}
