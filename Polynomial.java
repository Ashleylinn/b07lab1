import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class Polynomial {
	public double[] coefficients;
	public int[] exponents;

	public Polynomial() {
		this.coefficients = new double[]{0};
		this.exponents = new int[]{0};
	}
	
	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = coefficients;
		this.exponents = exponents;
	}
	
	public Polynomial add(Polynomial other) {
		int maxSize = this.coefficients.length + other.coefficients.length;
		double[] resultCoefficients = new double[maxSize];
        int[] resultExponents = new int[maxSize];
        int index = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            resultCoefficients[index] = this.coefficients[i];
            resultExponents[index] = this.exponents[i];
            index++;
        }
        
        boolean added = false;
        for (int i = 0; i < other.coefficients.length; i++) {
            for (int j = 0; j < index; j++) {
            	if (resultExponents[j] == other.exponents[i]) {
            		resultCoefficients[j] += other.coefficients[i]; 
                    added = true;
                    break;
                }
            }
            
            if (added == false) 
            {
                resultCoefficients[index] = other.coefficients[i];
                resultExponents[index] = other.exponents[i];
                index++;
            }
        }

        int noZero = 0;
        for (int i = 0; i < index; i++) {
            if (resultCoefficients[i] != 0) {
            	noZero++;
            }
        }

        double[] finalCoefficients = new double[noZero];
        int[] finalExponents = new int[noZero];
        int finalIndex = 0;
        for (int i = 0; i < index; i++) {
            if (resultCoefficients[i] != 0) {
                finalCoefficients[finalIndex] = resultCoefficients[i];
                finalExponents[finalIndex] = resultExponents[i];
                finalIndex++;
            }
        }

        return new Polynomial(finalCoefficients, finalExponents);
	}
	
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(x, exponents[i]);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
	
	public Polynomial multiply(Polynomial other) {
        int maxSize = this.coefficients.length * other.coefficients.length;
        double[] resultCoefficients = new double[maxSize];
        int[] resultExponents = new int[maxSize];
        int index = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double newCoefficient = this.coefficients[i] * other.coefficients[j];
                int newExponent = this.exponents[i] + other.exponents[j];

                boolean found = false;
                for (int k = 0; k < index; k++) {
                    if (resultExponents[k] == newExponent) {
                        resultCoefficients[k] += newCoefficient; 
                        found = true;
                        break;
                    }
                }

                if (found == false) {
                    resultCoefficients[index] = newCoefficient;
                    resultExponents[index] = newExponent;
                    index++;
                }
            }
        }

        int noZero = 0;
        for (int i = 0; i < index; i++) {
            if (resultCoefficients[i] != 0) {
            	noZero++;
            }
        }

        double[] finalCoefficients = new double[noZero];
        int[] finalExponents = new int[noZero];
        int finalIndex = 0;
        for (int i = 0; i < index; i++) {
            if (resultCoefficients[i] != 0) {
                finalCoefficients[finalIndex] = resultCoefficients[i];
                finalExponents[finalIndex] = resultExponents[i];
                finalIndex++;
            }
        }

        return new Polynomial(finalCoefficients, finalExponents);
	}

	public Polynomial(File file) throws IOException {
		Scanner scanner = new Scanner(file);
		String line = scanner.nextLine();
		scanner.close();
		
		String[] terms = line.split("?=-+[]()");
		double[] coefficients = new double[terms.length];
		int[] exponents = new int[terms.length];
		int index = 0;
		
		for(String term : terms) {
			term = term.trim();
			if(term.equals("+x") || term.equals("x"))
			{
				coefficients[index] = 1;
				exponents[index] = 1;
			}
			else if(term.equals("-x"))
			{
				coefficients[index] = -1;
				exponents[index] = 1;
			} 
			else if (term.contains("x")) 
			{
				String[] parts = term.split("x");
				coefficients[index] = parts[0].equals("+") ? 1 : (parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]));
				exponents[index] = parts.length > 1 ? Integer.parseInt(parts[1].substring(1)) : 1;
			}
			else
			{
				coefficients[index] = Double.parseDouble(term);
				exponents[index] = 0;
			}
			
			index++;
		}
		
		this.coefficients = coefficients;
		this.exponents = exponents;
	}
	
	public void saveToFile(String filename) throws IOException {
		FileWriter writer = new FileWriter(filename);
		StringBuilder build = new StringBuilder();
		
		for(int i = 0; i<this.coefficients.length; i++)
		{
			if(this.coefficients[i] == 0)
				continue;
			
			if(i > 0 && this.coefficients[i] > 0)
				build.append("+");
			if(this.coefficients[i] == -1 && this.exponents[i] != 0)
				build.append("-");
			if (this.coefficients[i] != 1 || this.exponents[i] == 0) 
				build.append(this.coefficients[i]);
			
			if (this.exponents[i] > 0) {
				build.append("x");
				if (this.exponents[i] > 1) 
					build.append("^").append(this.exponents[i]);
			}
		}
		
		writer.write(build.toString());
		writer.close();
	}
}
