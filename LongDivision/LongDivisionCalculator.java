package com.saptakdas.misc.LongDivision;

public class LongDivisionCalculator {
    public static Polynomial quotient = null;
    public static Polynomial remainder = null;

    public static void main(String[] args) {
        //Getting Input for Long Division
        //Dividend
        Polynomial dividendPolynomial = new Polynomial("Dividend");
        //Divisor
        Polynomial divisorPolynomial = new Polynomial("Divisor");
        //Do actual Long Division Process
        //Initialization and Preprocessing
        Polynomial dividend = dividendPolynomial.copy();
        LongDivisionCalculator.quotient = new Polynomial(dividend.degree-divisorPolynomial.degree, new Fraction[dividend.degree-divisorPolynomial.degree+1]);
        longDivision(dividend, divisorPolynomial);
        System.out.println("\nAnswer: ");
        System.out.println("Quotient: "+LongDivisionCalculator.quotient.toString());
        System.out.println("Remainder: "+LongDivisionCalculator.remainder.toString());
    }

    public static void longDivision(Polynomial dividend, Polynomial divisor){
        //Simplifying the polynomial
        dividend.simplify();
        //Checking if further iteration is necessary
        if(dividend.degree < divisor.degree){
            //The remaining bit is remainder.
            LongDivisionCalculator.remainder = dividend;
            return;
        }
        //Subtract off the copy
        Fraction scalarCoefficient = Fraction.multiply(dividend.coefficients[0], new Fraction(divisor.coefficients[0].denominator, divisor.coefficients[0].numerator));
        LongDivisionCalculator.quotient.coefficients[LongDivisionCalculator.quotient.degree-(dividend.degree-divisor.degree)] = scalarCoefficient;
        Polynomial subtractPolynomial = divisor.multiply(scalarCoefficient).multiply(dividend.degree-divisor.degree);
        Polynomial result = Polynomial.add(dividend, Polynomial.negate(subtractPolynomial));
        //Continue next iteration as dividend is not fully divided yet.
        longDivision(result, divisor);
    }
}
