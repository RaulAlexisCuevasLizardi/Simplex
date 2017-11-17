/**
 * University of Puerto Rico at Bayamon
 * Department of Computer Science
 * SICI 4028 - Operations Investigation, Spring 2017
 * Prof. Luis Ortiz
 */

package version1;
import java.util.Arrays;

    /**
	 * Class that implements a multi variables linear equations
	 * Raul Cuevas {@literal <raul.cuevas@upr.edu>}
	 */
public class MultiVariableLinearEquation {
	/**Creates an array of variables */
	private double[] variables;
	/**Counter of the variables */
	private int variableAmount;
	
	/**
	 * Creates a new MultiVariableLinearEquation. This assumes that the third array is the objective function.
	 * @param variables The array of variables
	 */
	public MultiVariableLinearEquation(double[] variables){
		super();
		this.variables = variables;
		this.variableAmount = variables.length;
	}
	
	/**
	 * Constructor that initializes the fields
	 */
	public MultiVariableLinearEquation(){
		this.variables = new double[0];
		this.variableAmount = 0;
	}
	
	/**
	 * Getter that retrieves the variable
	 * @param index The variable location
	 * @return the value of the variable
	 */
	public double getVariable(int index){
		return this.variables[index];
	}

	/**
	 * Copies the variables to an array an return the values
	 * @return the array of values
	 */
	public double[] getVariables() {
		double variables[] = new double[variableAmount];
		for(int i = 0; i < variables.length; i++)
			variables[i] = this.variables[i];
		return variables;
	}
	
	/**
	 * Setting the values to their respective one
	 */
	public void setVariables(double[] newVariables){
		for(int i = 0; i < this.variables.length; i++){
			this.variables[i] = newVariables[i];
		}
	}
	
	/**
	 * Setting the values with the variable
	 * @param var The variable in the array
	 * @param index The value of the variable
	 */
	public void setVariable(double var, int index){
		this.variables[index] = var;
	}

	/**
	 * Getter that retrieves the variable
	 */
	public int getvariableAmount() { 
		return variableAmount;
	}

	/**
	 * Generates a string representation of the array
	 * @return The string representation
	 */
	@Override
	public String toString() {
		return "\nMultiVariableLinearEquation [variables=" + Arrays.toString(variables) + ", variableAmount="
				+ variableAmount + "]";
	}
}
