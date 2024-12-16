package src.DataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import src.Utils.*;
import src.DataStructure.Coordinate;

public class Polynomial {
    private final List<Double> coefficients;

    /**
     * Constructor for Polynomial
     * @param coeffs The coefficients of the polynomial in a simple Array
     */
    public Polynomial(double[] coeffs) {
        coefficients = new ArrayList<>();
        for (double c : coeffs) {
            coefficients.add(c);
        }
    }

    /**
     * Constructor for Polynomial out of a String
     * @param polynom
     */
    public Polynomial(String polynom) {
        //remove whitespaces from String using RegEx Pattern
        polynom = polynom.replaceAll("\\s+", "").replaceAll("\\+-", "-").replaceAll(",", ".");

        //create Regex Pattern for Expressions like x^2, 2x^4, -3, 4x
        Pattern termPattern = Pattern.compile("([+-]?\\d*\\.?\\d*)x?(?:\\^(\\d+))?"); // This Pattern was created using AI: model GPT-4o, prompt: "Create a regex pattern for expressions like x^2, 2x^4, -3, 4x"

        //create Matcher for the given Polynomial to find expressions
        Matcher matcher = termPattern.matcher(polynom);
        //iterate through all expressions from the Polynomial and determine the degree of the polynom
        int maxDegree = 0;
        while (matcher.find()) {
            if (matcher.group(2) != null) {
                int exponent = Integer.parseInt(matcher.group(2));
                maxDegree = Math.max(maxDegree, exponent);
            }else if (matcher.group(0).contains("x")){
                maxDegree = Math.max(maxDegree, 1);
            }
        }

        //initialize coefficients Array with the size of the degree
        double[] coeffs = new double[maxDegree + 1];
        Arrays.fill(coeffs, 0);

        //reset matcher to find expressions and put them into the array
        matcher.reset();

        while(matcher.find()) {
            String coeffStr;
            //positive coeff
            if (matcher.group(1).isEmpty() || matcher.group(1).equals("+")) {
                coeffStr = "1";
            //negative coeff
            } else if (matcher.group(1).equals("-")) {
                coeffStr = "-1";
            } else {
                coeffStr = matcher.group(1);
            }
            double coeff = Double.parseDouble(coeffStr);
            //get degree to set the coefficient at the right position in the array
            int degree;
            if (matcher.group(2) != null) {
                degree = Integer.parseInt(matcher.group(2)); // Explicit power like x^2
            } else if (matcher.group(0).contains("x")) {
                degree = 1; // Linear term like "x"
            } else {
                degree = 0; // Constant term
            }
            coeffs[degree] += coeff;
        }
        //fix for too high coefficients, because the matcher appends one value at last iteration, where all Groups are blank or null, so its needs to removed
        coeffs[0] = coeffs[0] - 1;
        coefficients = new ArrayList<>();
        for (double c : coeffs) {
            coefficients.add(c);
        }
    }
    public double[] getCoeffients(){
        double[] coeffs = new double[coefficients.size()];
        for (int i = 0; i < coeffs.length; i++) {
            coeffs[i] = coefficients.get(i);
        }
        return coeffs;
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.size(); i++) {
            result += coefficients.get(i) * Math.pow(x, i);
        }
        return result;
    }

    // Berechnet die Ableitung des Polynoms als neues Polynomial-Objekt
    public Polynomial derivative() {
        if (coefficients.size() <= 1) {
            return new Polynomial(new double[]{0});
        }
        double[] derivativeCoeffs = new double[coefficients.size() - 1];
        for (int i = 1; i < coefficients.size(); i++) {
            derivativeCoeffs[i - 1] = coefficients.get(i) * i;
        }
        return new Polynomial(derivativeCoeffs);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            double coeff = coefficients.get(i);
            if (coeff == 0) continue;
            if (!sb.isEmpty()) {
                sb.append(coeff < 0 ? " + " : " - ");
            }
            if (Math.abs(coeff) != 1 || i == 0) {
                sb.append(Math.abs(coeff));
            }
            if (i > 0) {
                sb.append("x");
                if (i > 1) {
                    sb.append("^").append(i);
                }
            }
        }
        return sb.toString();
    }

    public List<Double> findRoots() {
        List<Double> roots = new ArrayList<>();
        Polynomial current = this;

        while (current.coefficients.size() > 2) {
            // Use Newton's method to find a root
            double root = findRoot(current);

            // Normalize -0.0 to 0.0
            root = (root == -0.0) ? 0.0 : root;

            // Avoid adding duplicate roots
            if (!roots.contains(root)) {
                roots.add(root);
            }

            // Perform polynomial division to get the reduced polynomial
            current = current.divideByLinearFactor(root);
        }

        // Handle the final quadratic or linear case
        if (current.coefficients.size() == 2) {
            // Linear case ax + b = 0 -> root = -b/a
            double a = current.coefficients.get(1);
            double b = current.coefficients.get(0);
            double root = -b / a;

            // Normalize and add
            root = (root == -0.0) ? 0.0 : root;
            if (!roots.contains(root)) {
                roots.add(root);
            }
        } else {
            // Quadratic case ax^2 + bx + c = 0
            List<Double> quadraticRoots = solveQuadratic(current);
            for (double root : quadraticRoots) {
                root = (root == -0.0) ? 0.0 : root;
                if (!roots.contains(root)) {
                    roots.add(root);
                }
            }
        }

        roots = ListUtils.removeDuplicates(roots);
        roots = validateRoots(roots);
        return roots;
    }

    // Helper function: Perform polynomial division by (x - r)
    private Polynomial divideByLinearFactor(double root) {
        int degree = this.coefficients.size() - 1;
        double[] newCoefficients = new double[degree]; // Reduced degree

        newCoefficients[degree - 1] = coefficients.get(degree);
        for (int i = degree - 2; i >= 0; i--) {
            newCoefficients[i] = coefficients.get(i + 1) + root * newCoefficients[i + 1];
        }

        return new Polynomial(newCoefficients);
    }

    // Helper function: Solve quadratic equation ax^2 + bx + c = 0
    private List<Double> solveQuadratic(Polynomial poly) {
        List<Double> roots = new ArrayList<>();
        double a = poly.coefficients.get(2);
        double b = poly.coefficients.get(1);
        double c = poly.coefficients.get(0);

        double discriminant = b * b - 4 * a * c;
        if (discriminant >= 0) {
            double sqrtD = Math.sqrt(discriminant);
            roots.add((-b + sqrtD) / (2 * a));
            roots.add((-b - sqrtD) / (2 * a));
        }
        return roots;
    }

    // Helper function: Find a single root using Newton's method
    private double findRoot(Polynomial poly) {
        double x0 = findInitialGuess(poly);
        double tolerance = 1e-12;
        int maxIterations = 10000;

        for (int i = 0; i < maxIterations; i++) {
            double fx = poly.evaluate(x0);
            double fPrimeX = poly.derivative().evaluate(x0);

            if (Math.abs(fx) < tolerance) {
                return Math.round(x0 * 1e8) / 1e8; // Root found
            }

            if (Math.abs(fPrimeX) < tolerance) {
                return 0;
            }

            x0 = x0 - fx / fPrimeX; // Newton's iteration
        }

        return 0; // No root found
    }

    private double findInitialGuess(Polynomial polynomial) {
        double lower = -10; // Starting lower bound for search
        double upper = 10;  // Starting upper bound for search
        double stepSize = 0.1; // Step size for finding initial guesses

        // Evaluate the polynomial at different points and look for a sign change
        double prevValue = polynomial.evaluate(lower);
        for (double x = lower + stepSize; x <= upper; x += stepSize) {
            double currentValue = polynomial.evaluate(x);

            // Check for a sign change
            if (Math.signum(prevValue) != Math.signum(currentValue)) {
                // Sign change detected, return the midpoint of the interval
                return (x - stepSize + x) / 2.0;
            }

            prevValue = currentValue;
        }

        // If no sign change found, return a default guess (e.g., middle of the interval)
        return (lower + upper) / 2.0;
    }

    private List<Double> validateRoots(List<Double> roots) {
        List<Double> newRoots = new ArrayList<>();
        for(Double root : roots) {
            if(Math.abs(evaluate(root)) < 1e-6){
                newRoots.add(root);
            }
        }
        return newRoots;
    }

    public List<Coordinate> extrema() {
        Polynomial poly = this;
        Polynomial firstDerivative = poly.derivative();
        Polynomial secondDerivative = firstDerivative.derivative();
        List<Coordinate> result = new ArrayList<Coordinate>();
        if(firstDerivative.coefficients.size() >= 2) {
            List<Double> roots = firstDerivative.findRoots();
            for(double root : roots) {
                if(secondDerivative.evaluate(root) > 0){
                    result.add(new Coordinate(root, poly.evaluate(root), "Tiefpunkt"));
                }else if(secondDerivative.evaluate(root) < 0){
                    result.add(new Coordinate(root, poly.evaluate(root), "Höhepunkt"));
                }
            }
        }
        return result;
    }

    public List<Coordinate> inflection() {
        Polynomial poly = this;
        Polynomial secondDerivative = poly.derivative().derivative();
        Polynomial thirdDerivative = secondDerivative.derivative();

        List<Coordinate> result = new ArrayList<Coordinate>();
        if(secondDerivative.coefficients.size() > 1) {
            List<Double> roots = secondDerivative.findRoots();
            for(double root : roots) {
                if(thirdDerivative.evaluate(root) > 0){
                    result.add(new Coordinate(root, poly.evaluate(root), "R-L-Wendepunkt"));
                }else if(thirdDerivative.evaluate(root) < 0){
                    result.add(new Coordinate(root, poly.evaluate(root), "L-R-Wendepunkt"));
                }
            }
        }
        return result;
    }
}
