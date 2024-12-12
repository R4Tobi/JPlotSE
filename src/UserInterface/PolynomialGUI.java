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
        JButton plotButton = new JButton("Plot");

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(processButton);
        inputPanel.add(plotButton);

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

        // Variables for the current polynomial
        final Polynomial[] currentPolynomial = new Polynomial[1];

        // Add button action listener
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();

                if (inputText.isEmpty()) {
                    outputArea.setText("Please enter some text.");
                } else {
                    try {
                        // Process the input into separate strings
                        Polynomial polynomial = new Polynomial(inputText);
                        currentPolynomial[0] = polynomial;
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
                    } catch (Exception ex) {
                        outputArea.setText("Invalid input. Please enter a valid polynomial.");
                    }
                }
            }
        });

        plotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPolynomial[0] == null) {
                    JOptionPane.showMessageDialog(frame, "Please process a polynomial first.");
                    return;
                }

                // Create a new frame for the plot
                JFrame plotFrame = new JFrame("Polynomial Plot");
                plotFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                plotFrame.setSize(800, 600);

                // Add the plotter to the frame
                plotFrame.add(new PolynomialPlotter(currentPolynomial[0]));

                plotFrame.setVisible(true);
            }
        });

        frame.setVisible(true);
    }

    static class PolynomialPlotter extends JPanel {
        private Polynomial polynomial;

        public PolynomialPlotter(Polynomial polynomial) {
            this.polynomial = polynomial;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D coordinate = (Graphics2D) g;

            int width = getWidth();
            int height = getHeight();

            coordinate.setColor(Color.BLACK);

            coordinate.setStroke(new BasicStroke(2));
            coordinate.drawLine(0, height / 2, width, height / 2); //x-axis
            coordinate.drawLine(width / 2, 0, width / 2, height); //y-axis

            double scaleX = 50;
            double scaleY = 50;

            for (int i = -10; i <= 10; i++) {
                int x = width / 2 + (int) (i * scaleX);
                int y = height / 2 - (int) (i * scaleY);

                // label x-Axis
                if (i != 0) {
                    coordinate.drawString(Integer.toString(i), x - 5, height / 2 + 15);
                }

                // label y-Axis
                if (i != 0) {
                    coordinate.drawString(Integer.toString(i), width / 2 + 5, y + 5);
                }
            }

            coordinate.setColor(Color.PINK);
            for (int i = 0; i < width; i++) {
                double x1 = (i - (double) width / 2) / scaleX;
                double x2 = (i + 1 - (double) width / 2) / scaleX;

                double y1 = polynomial.evaluate(x1);
                double y2 = polynomial.evaluate(x2);

                int screenX1 = i;
                int screenY1 = height / 2 - (int) (y1 * scaleY);
                int screenX2 = i + 1;
                int screenY2 = height / 2 - (int) (y2 * scaleY);

                coordinate.drawLine(screenX1, screenY1, screenX2, screenY2);
            }
        }
    }
}