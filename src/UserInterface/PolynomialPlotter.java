package src.UserInterface;

import src.DataStructure.Polynomial;

import javax.swing.*;
import java.awt.*;

/**
 * Class to plot a Polynomial
 */
public class PolynomialPlotter extends JPanel {
    private final Polynomial polynomial;

    /**
     * Constructor to pass the polynomial
     * @param polynomial Polynomial to be plotted
     */
    public PolynomialPlotter(Polynomial polynomial) {
        this.polynomial = polynomial;
    }

    /**
     * Build Graph
     * @param g the <code>Graphics</code> object to protect
     */
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

            int screenY1 = height / 2 - (int) (y1 * scaleY);
            int screenX2 = i + 1;
            int screenY2 = height / 2 - (int) (y2 * scaleY);

            coordinate.drawLine(i, screenY1, screenX2, screenY2);
        }
    }
}
