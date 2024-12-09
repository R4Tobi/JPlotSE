package src.DataStructure;

public class Complex {
    double real, imaginary;

    Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    Complex subtract(Complex other) {
        return new Complex(this.real - other.real, this.imaginary - other.imaginary);
    }

    Complex multiply(Complex other) {
        return new Complex(
                this.real * other.real - this.imaginary * other.imaginary,
                this.real * other.imaginary + this.imaginary * other.real
        );
    }

    Complex divide(Complex other) {
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        return new Complex(
                (this.real * other.real + this.imaginary * other.imaginary) / denominator,
                (this.imaginary * other.real - this.real * other.imaginary) / denominator
        );
    }

    Complex pow(int n) {
        Complex result = new Complex(1, 0);
        for (int i = 0; i < n; i++) {
            result = result.multiply(this);
        }
        return result;
    }

    double magnitude() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    @Override
    public String toString() {
        if (Math.abs(imaginary) < 1e-6) {
            return String.format("%.6f", real);
        }
        return String.format("%.6f + %.6fi", real, imaginary);
    }

}
