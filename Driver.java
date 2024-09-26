import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String[] args) throws IOException{
		Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3)); 
        
        double[] c1 = {6, -2, 5};
        int[] e1 = {0, 1, 3};
        Polynomial p1 = new Polynomial(c1, e1);
        
        double[] c2 = {-9, 3, 2};  
        int[] e2 = {0, 2, 1};
        Polynomial p2 = new Polynomial(c2, e2);
        
        Polynomial s = p1.add(p2);
        System.out.println(s.evaluate(0.1));
        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }
        
        Polynomial product = p1.multiply(p2);
        System.out.println(product.evaluate(1));
        
        File polynomialFile = new File("polynomial.txt");
        Polynomial pFile = new Polynomial(polynomialFile);
        System.out.println("Polynomial from file evaluated at x = 1: " + pFile.evaluate(1));
        
        pFile.saveToFile("saved_polynomial.txt");
    }
}