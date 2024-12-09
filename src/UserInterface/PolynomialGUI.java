package src.UserInterface;

import src.DataStructure.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PolynomialGUI {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Input Processor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        // Create the input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel inputLabel = new JLabel("Enter text:");
        JTextField inputField = new JTextField(20);
        JButton processButton = new JButton("Process");

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(processButton);

        // Create the output area
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(380, 100));

        // Add components to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add button action listener
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();

                if (inputText.isEmpty()) {
                    outputArea.setText("Please enter some text.");
                } else {
                    // Process the input into separate strings
                    Polynomial polynomial = new Polynomial(inputText);
                    String[] outputString = new String[]{
                            "Polynom: " + polynomial.toString(),
                            "Nullstellen: " + polynomial.findRoots(),
                            "1. Ableitung: " + polynomial.derivative().toString(),
                            "2. Ableitung: " + polynomial.derivative().derivative().toString(),
                            "3. Ableitung: " + polynomial.derivative().derivative().derivative().toString(),
                    };
                    StringBuilder outputBuilder = new StringBuilder(polynomial.toString() + "\n");
                    for (String str : outputString) {
                        outputBuilder.append(str).append("\n");
                    }
                    outputArea.setText(outputBuilder.toString());
                }
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
}