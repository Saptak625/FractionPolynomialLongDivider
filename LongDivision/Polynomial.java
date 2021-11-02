package com.saptakdas.misc.LongDivision;

import java.util.Arrays;
import java.util.Scanner;

public class Polynomial {
    private static Scanner sc=new Scanner(System.in);
    public int degree;
    public Fraction[] coefficients;
    
    public Polynomial(int degree, Fraction[] coefficients){
        this.degree=degree;
        this.coefficients=coefficients;
    }
    
    public Polynomial(String prompt){
        System.out.print("Degree of "+prompt+": ");
        this.degree = sc.nextInt();
        this.coefficients = new Fraction[this.degree+1];
        System.out.println();
        for(int i=0; i<this.coefficients.length; i++){
            System.out.println("Degree "+ (this.degree - i)+":");
            System.out.print("Fraction: ");
            String fractionString = sc.next();
            Integer numerator = null;
            Integer denominator = null;
            if(fractionString.contains("/")){
                String[] splitString = fractionString.split("/");
                numerator = Integer.parseInt(splitString[0]);
                denominator = Integer.parseInt(splitString[1]);
            }else{
                numerator = Integer.parseInt(fractionString);
                denominator = 1;
            }
            this.coefficients[i] = new Fraction(numerator, denominator);
            System.out.println();
        }
    }

    public Polynomial copy(){
        Fraction[] newFractionArr = new Fraction[this.coefficients.length];
        for(int i=0; i<this.coefficients.length; i++){
            newFractionArr[i] = new Fraction(this.coefficients[i].numerator, this.coefficients[i].denominator);
        }
        return new Polynomial(this.degree, newFractionArr);
    }

    public static Polynomial negate(Polynomial p){
        Polynomial copy = p.copy();
        for(int i=0; i<p.coefficients.length; i++){
            copy.coefficients[i] = Fraction.negate(copy.coefficients[i]);
        }
        return copy;
    }

    public static Polynomial add(Polynomial p1, Polynomial p2){
        //Assumes that p1 and p2 are of same degree.
        Polynomial result = new Polynomial(p1.degree, new Fraction[p1.coefficients.length]);
        for(int i=0; i<p1.coefficients.length; i++){
            result.coefficients[i] = Fraction.add(p1.coefficients[i], p2.coefficients[i]);
        }
        return result;
    }

    public Polynomial multiply(Fraction scalar) {
        Polynomial copy = this.copy();
        for(int i=0; i<copy.coefficients.length; i++){
            copy.coefficients[i] = Fraction.multiply(copy.coefficients[i], scalar);
        }
        return copy;
    }

    public Polynomial multiply(int degree) {
        //Pad result to correct polynomial size
        Polynomial copy = this.copy();
        Fraction[] paddedCoefficients = new Fraction[copy.coefficients.length+degree];
        for(int i=0; i<paddedCoefficients.length; i++){
            paddedCoefficients[i] = new Fraction(0, 1);
        }
        for(int i=0; i<copy.coefficients.length; i++){
            paddedCoefficients[i] = copy.coefficients[i];
        }
        copy.coefficients = paddedCoefficients;
        copy.degree += degree;
        return copy;
    }

    public void simplify(){
        int trimLength = 0;
        for(int i=0; i<this.coefficients.length-1; i++){
            if(this.coefficients[i].equals(new Fraction(0, 1))){
                trimLength++;
            }else{
                break;
            }
        }
        this.coefficients = Arrays.copyOfRange(this.coefficients, trimLength, this.degree+1);
        this.degree-=trimLength;
    }

    @Override
    public String toString() {
        StringBuilder polynomialString = new StringBuilder();
        for(int i=0; i<this.coefficients.length; i++){
            polynomialString.append("("+this.coefficients[i].toString()+")");
            int d=this.degree-i;
            if(d > 0){
                polynomialString.append("x");
                if(d > 1){
                    polynomialString.append("^").append(d);
                }
                polynomialString.append("+");
            }
        }
        return polynomialString.toString();
    }
}
