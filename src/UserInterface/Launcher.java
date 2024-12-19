package src.UserInterface;

import src.DataStructure.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Graphical User Interface for Processing and Plotting Polynomials.
 */
public class Launcher {
    /**
     * launches the GUI for the Plotter
     * @param args not used -> empty string
     */
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Input Processor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
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
        scrollPane.setPreferredSize(new Dimension(500, 400));

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
                                "Polynom: \n" + polynomial.toString() + "\n",
                                "Nullstellen: \n" + polynomial.findRoots() + "\n",
                                "1. Ableitung: \n" + polynomial.derivative().toString() + "\n",
                                "2. Ableitung: \n" + polynomial.derivative().derivative().toString() + "\n",
                                "3. Ableitung: \n" + polynomial.derivative().derivative().derivative().toString() + "\n",
                                "Stammfunktion:\n" + polynomial.integral().toString() + " + c\n",
                                "Extrempunkte: \n" + polynomial.extrema().toString() + "\n",
                                "Wendepunkte:  \n" + polynomial.inflection().toString() + "\n",
                        };
                        StringBuilder outputBuilder = new StringBuilder();
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
}