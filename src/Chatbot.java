import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import opennlp.tools.tokenize.*;
import java.io.*;

public class Chatbot extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private Tokenizer tokenizer;

    public Chatbot() {
        setTitle("Smart Chatbot");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatArea);
        add(scroll, BorderLayout.CENTER);

        inputField = new JTextField();
        add(inputField, BorderLayout.SOUTH);

        try {
            InputStream modelIn = new FileInputStream("models/en-token.bin");
            TokenizerModel model = new TokenizerModel(modelIn);
            tokenizer = new TokenizerME(model);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Model loading failed: " + e.getMessage());
            System.exit(1);
        }

        inputField.addActionListener(e -> {
            String userText = inputField.getText();
            chatArea.append("You: " + userText + "\n");
            inputField.setText("");
            String response = getBotResponse(userText);
            chatArea.append("Bot: " + response + "\n");
        });
    }

    private String getBotResponse(String userText) {
        String[] tokens = tokenizer.tokenize(userText.toLowerCase());

        if (userText.contains("hello") || userText.contains("hi")) {
            return "Hello! How can I help you?";
        } else if (userText.contains("your name")) {
            return "I’m SmartBot, your assistant!";
        } else if (userText.contains("college")) {
            return "Our college is among the top ranked in India.";
        } else if (userText.contains("bye")) {
            return "Goodbye! Have a great day.";
        } else {
            return "Sorry, I didn’t understand that.";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Chatbot().setVisible(true));
    }
}
