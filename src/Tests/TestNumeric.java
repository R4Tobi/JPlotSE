package src.Tests;

import src.DataStructure.Polynomial;

import java.util.Arrays;

public class TestNumeric {
    private static final Polynomial[] testPolynomials = new Polynomial[] {
            new Polynomial("x^2"),
            new Polynomial("x^3 + 1"),
            new Polynomial("x^4 +     x^4 -       x^6    + 3"),
            new Polynomial("x^2 + x^3 - x^3"),
            new Polynomial("x^2 + x^4 - x^4 - x^2"),
            new Polynomial("2,4x"),
            new Polynomial("2.6x"),
            new Polynomial(new double[]{2,2,3,4})
    };

    public static void main(String[] args) {
        testConstructor();
        testToString();
    }

    public static void testConstructor(){
        System.out.println("-------------------");
        System.out.println("Testing constructor");
        System.out.println("-------------------");
        Polynomial[] polynomials = testPolynomials;
        //Expected String Output
        Double[][] polynomialsOutput = new Double[][] {
                new Double[]{0.0, 0.0, 1.0},
                new Double[]{1.0, 0.0, 0.0, 1.0},
                new Double[]{3.0, 0.0, 0.0, 0.0, 2.0, 0.0, -1.0},
                new Double[]{0.0, 0.0, 1.0, 0.0},
                new Double[]{0.0, 0.0, 0.0, 0.0, 0.0},
                new Double[]{0.0, 2.4},
                new Double[]{0.0, 2.6},
                new Double[]{2.0, 2.0, 3.0, 4.0},
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
    }
}
