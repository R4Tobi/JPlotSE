package src.UserInterface;

import src.DataStructure.Polynomial;
import src.DataStructure.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PolynomialPlotter extends JPanel {
    private final Polynomial polynomial;
    private double scaleX = 50;
    private double translateX = 0;
    private double translateY = 0;
    private Point dragStart;

    public PolynomialPlotter(Polynomial polynomial) {
        this.polynomial = polynomial;
        adjustDisplayInterval();
        addMouseListener(new PanHandler());
        addMouseMotionListener(new PanHandler());
    }

    private void adjustDisplayInterval() {
        List<Double> roots = polynomial.findRoots();
        List<Coordinate> extrema = polynomial.extrema();
        List<Coordinate> inflections = polynomial.inflection();

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (double root : roots) {
            minX = Math.min(minX, root);
            maxX = Math.max(maxX, root);
        }

        for (Coordinate coord : extrema) {
            minX = Math.min(minX, coord.getX());
            maxX = Math.max(maxX, coord.getX());
            minY = Math.min(minY, coord.getY());
            maxY = Math.max(maxY, coord.getY());
        }

        for (Coordinate coord : inflections) {
            minX = Math.min(minX, coord.getX());
            maxX = Math.max(maxX, coord.getX());
            minY = Math.min(minY, coord.getY());
            maxY = Math.max(maxY, coord.getY());
        }

        translateX = -(minX + maxX) / 2 * scaleX + (double) getWidth() / 2;
        translateY = (minY + maxY) / 2 * scaleX - (double) getHeight() / 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D coordinate = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        coordinate.setColor(Color.BLACK);
        coordinate.setStroke(new BasicStroke(2));
        coordinate.drawLine(0, height / 2 + (int) translateY, width, height / 2 + (int) translateY); // x-axis
        coordinate.drawLine(width / 2 + (int) translateX, 0, width / 2 + (int) translateX, height); // y-axis

        for (int i = -100; i <= 100; i++) {
            int x = width / 2 + (int) (i * scaleX + translateX);
            int y = height / 2 - (int) (i * scaleX - translateY);

            if (i != 0) {
                coordinate.drawString(Integer.toString(i), x - 5, height / 2 + 15 + (int) translateY);
                coordinate.drawString(Integer.toString(i), width / 2 + 5 + (int) translateX, y + 5);
            }
        }

        coordinate.setColor(Color.PINK);
        for (int i = 0; i < width; i++) {
            double x1 = (i - (double) width / 2 - translateX) / scaleX;
            double x2 = (i + 1 - (double) width / 2 - translateX) / scaleX;

            double y1 = polynomial.evaluate(x1);
            double y2 = polynomial.evaluate(x2);

            int screenY1 = height / 2 - (int) (y1 * scaleX - translateY);
            int screenX2 = i + 1;
            int screenY2 = height / 2 - (int) (y2 * scaleX - translateY);

            coordinate.drawLine(i, screenY1, screenX2, screenY2);
        }
    }

    private class PanHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            dragStart = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point dragEnd = e.getPoint();
            translateX += dragEnd.getX() - dragStart.getX();
            translateY += dragEnd.getY() - dragStart.getY();
            dragStart = dragEnd;
            repaint();
        }
    }
}