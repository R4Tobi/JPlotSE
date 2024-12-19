package src.Tests;

import src.DataStructure.Polynomial;

import java.util.ArrayList;
import java.util.Arrays;

public class TestPolynomial {
    private static final Polynomial[] testPolynomials = new Polynomial[] {
            new Polynomial("x^2"),
            new Polynomial("x^3 + 1"),
            new Polynomial("x^4 +     x^4 -       x^6    + 3"),
            new Polynomial("x^2 + x^3 - x^3"),
            new Polynomial("x^2 + x^4 - x^4 - x^2"),
            new Polynomial("2,4x"),
            new Polynomial("2.6x"),
            new Polynomial(new double[]{2,2,-3,4})
    };

    public static void main(String[] args) {
        testConstructor();
        testToString();
        testEvaluate();
    }

    public static void testConstructor(){
        System.out.println("-------------------");
        System.out.println("Testing constructor");
        System.out.println("-------------------");
        Polynomial[] polynomials = testPolynomials;
        //Expected Values
        Double[][] polynomialsOutput = new Double[][] {
                new Double[]{0.0, 0.0, 1.0},
                new Double[]{1.0, 0.0, 0.0, 1.0},
                new Double[]{3.0, 0.0, 0.0, 0.0, 2.0, 0.0, -1.0},
                new Double[]{0.0, 0.0, 1.0, 0.0},
                new Double[]{0.0, 0.0, 0.0, 0.0, 0.0},
                new Double[]{0.0, 2.4},
                new Double[]{0.0, 2.6},
                new Double[]{2.0, 2.0, -3.0, 4.0},
        };
        boolean passed = true;
        for(int i = 0; i < polynomials.length; i++ ){
            System.out.println(Arrays.toString(polynomials[i].getCoeffients()) + " Expected: " + Arrays.toString(polynomialsOutput[i]));
            for(int j = 0; j < polynomials[i].getCoeffients().length; j++){
                if(polynomials[i].getCoeffients()[j] != polynomialsOutput[i][j]){
                    passed = false;
                }
            }
        }
        if(passed){
            System.out.println("Passed!");
        }else{
            System.out.println("Failed!");
        }
    }

    public static void testToString(){
        System.out.println("-------------------");
        System.out.println("Testing toString");
        System.out.println("-------------------");
        Polynomial[] polynomials = testPolynomials;
        String[] polynomialOutput = new String[]{
                "x^2",
                "x^3 + 1.0",
                "-x^6 + 2.0x^4 + 3.0",
                "x^2",
                "",
                "2.4x",
                "2.6x",
                "4.0x^3 - 3.0x^2 + 2.0x + 2.0"
        };
        boolean passed = true;
        for(int i = 0; i < polynomials.length; i++ ){
            System.out.println(polynomials[i].toString() + " Expected: " + polynomialOutput[i]);
            if(!polynomials[i].toString().equals(polynomialOutput[i])){
                passed = false;
            }
        }
        if(passed){
            System.out.println("Passed!");
        }
        else{
            System.out.println("Failed!");
        }
    }

    public static void testEvaluate(){
        System.out.println("-------------------");
        System.out.println("Testing evaluate");
        System.out.println("-------------------");
        Polynomial[] polynomials = testPolynomials;
        double[] xValues = new double[]{0.3, 2, 3, 4, 5, 6, 7, 8};
        double[] expectedValues = new double[]{0.09, 9.0, -564, 16.0, 0.0, 14.399999999999999, 18.2, 1874.0};
        boolean passed = true;
        for(int i = 0; i < polynomials.length; i++){
            System.out.println(polynomials[i].evaluate(xValues[i]) + " Expected: " + expectedValues[i]);
            if(polynomials[i].evaluate(xValues[i]) != expectedValues[i]){
                passed = false;
            }
        }
        if(passed){
            System.out.println("Passed!");
        }
        else{
            System.out.println("Failed!");
        }
    }

    public static void testFindRoots() {
        System.out.println("-------------------");
        System.out.println("Testing findRoots");
        System.out.println("-------------------");
    }
}
