/**
 * University of Puerto Rico at Bayamon
 * Department of Computer Science
 * SICI 4028 - Operations Investigation, Spring 2017
 * Prof. Luis Ortiz
 */

package version1;

import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 * Class that Creates a table for the simplex method
 * Raul Cuevas {@literal <raul.cuevas@upr.edu>}
 */
public class SimplexTable {

	/**Array type MultiVariableLinearEquation */
	private MultiVariableLinearEquation[] equations;
	private static int counter = 0;
	
	/**
	 * Creates a new MultiVariableLinearEquation. This assumes that the third array is the objective function.
	 * @param euqationString The string representation of the equation
	 */
	public SimplexTable(MultiVariableLinearEquation[] equations) {
		super();
		this.equations = equations;
	}

	/**
	 * Generates a string representation of the array
	 * @return The string representation
	 */
	@Override
	public String toString() {
		return "SimplexTable [equations=" + Arrays.toString(equations) + "]";
	}

	/**
	 * Getter that retrieves the variable
	 * @return the equations to solve
	 */
	public MultiVariableLinearEquation[] getEquations() {
		return equations;
	}
	
	/**
	 * Setting the equation corresponding values
	 * @param equationss The array type MultiVariableLinearEquation
	 */
	public void setEquations(MultiVariableLinearEquation[] equations) {
		this.equations = equations;
	}
	
	/**
	 * Solves the simplex equation
	 */
	public void solveTable(){
		JOptionPane.showMessageDialog(null, "Original matrix...\n" + this.toString());
		transpose();
		addSlackVariables();
		solveMaximization();
	}

	/**
	 * Adding The corresponding variables for the equation
	 */
	private void addSlackVariables() {
		double temporaryVariables[][] = new double[4][6];
		
		for(int i = 0; i < 3; i++){
			temporaryVariables[0][i] = this.equations[0].getVariable(i);
		}
		for(int i = 0; i < 3; i++){
			temporaryVariables[1][i] = this.equations[1].getVariable(i);
		}
		for(int i = 0; i < 3; i++){
			temporaryVariables[2][i] = this.equations[2].getVariable(i);
		}
		for(int i = 0; i < 3; i++){
			temporaryVariables[3][i] = this.equations[3].getVariable(i);
		}
		
		double tempVar1 = temporaryVariables[0][2];
		double tempVar2 = temporaryVariables[1][2];
		double tempVar3 = temporaryVariables[2][2];
		double tempVar4 = temporaryVariables[3][2];
		
		temporaryVariables[0][2] = 1;
		temporaryVariables[0][3] = 0;
		temporaryVariables[0][4] = 0;
		temporaryVariables[0][5] = tempVar1;
		
		temporaryVariables[1][2] = 0;
		temporaryVariables[1][3] = 1;
		temporaryVariables[1][4] = 0;
		temporaryVariables[1][5] = tempVar2;
		
		temporaryVariables[2][2] = 0;
		temporaryVariables[2][3] = 0;
		temporaryVariables[2][4] = 1;
		temporaryVariables[2][5] = tempVar3;
		
		temporaryVariables[3][2] = 0;
		temporaryVariables[3][3] = 0;
		temporaryVariables[3][4] = 0;
		temporaryVariables[3][5] = tempVar4;
		
		MultiVariableLinearEquation newEquations[] = new MultiVariableLinearEquation[4];
		newEquations[0] = new MultiVariableLinearEquation(temporaryVariables[0]);
		newEquations[1] = new MultiVariableLinearEquation(temporaryVariables[1]);
		newEquations[2] = new MultiVariableLinearEquation(temporaryVariables[2]);
		newEquations[3] = new MultiVariableLinearEquation(temporaryVariables[3]);
		this.equations = newEquations;
		JOptionPane.showMessageDialog(null, "Adding slack variables...\n\n" + this.toString());
	}

	/**
	 * Solving the maximazation equation
	 */
	private void solveMaximization() {
		negObjectiveFunction();
		while(!isOptimal()){
			pivot();
			counter++;
			if(counter==1){
				JOptionPane.showMessageDialog(null, "After 1st pivot...\n\n" + this.toString());
			}else if(counter==2){
				JOptionPane.showMessageDialog(null, "After 2nd pivot...\n\n" + this.toString());
			}else if(counter==3){
				JOptionPane.showMessageDialog(null, "After 3rd pivot...\n\n" + this.toString());
			}else if(counter>=4){
				JOptionPane.showMessageDialog(null, "After " + counter + "th pivot...\n\n" + this.toString());
			}
		}
		System.out.println(this.toString());
	}

	/**
	 * Searching for the pivot value
	 */
	private void pivot() {
		int enteringVariableColumn = 0;
		int departingVariableRow = 0;
		double departingVariable = Double.MAX_VALUE;
		double enteringVariable = this.equations[3].getVariable(0);
		for(int i = 0; i < this.equations[3].getvariableAmount(); i++){
			if(this.equations[3].getVariable(i) < enteringVariable){
				enteringVariable = this.equations[3].getVariable(i);
				enteringVariableColumn = i;
			}
		}
		for(int i = 0; i < this.equations.length-1; i++){
			if( (this.equations[i].getVariable(5)/this.equations[i].getVariable(enteringVariableColumn) ) < departingVariable){
				departingVariable = this.equations[i].getVariable(5)/this.equations[i].getVariable(enteringVariableColumn);
				departingVariableRow = i;
			}
		}
		gaussJordanElimination(departingVariableRow, enteringVariableColumn);
	}

	/**
	 * Eliminating using the Gauss Jordan Method
	 * @param departingVariablerow Eliminates the value
	 * @param enteringVariableColumn Enter the value
	 */
	private void gaussJordanElimination(int departingVariableRow, int enteringVariableColumn) {
		  updatePivotRow(departingVariableRow, enteringVariableColumn);
		  updateOtherRows(departingVariableRow, enteringVariableColumn);
	}
	
	/**
	 * Update the row of the variables
	 * @param departingVariablerow Eliminates the value
	 * @param enteringVariableColumn Enter the value
	 */
	private void updateOtherRows(int departingVariableRow, int enteringVariableColumn) {
		for(int i = 0; i < this.equations.length; i++){
			double pivotElement = this.equations[departingVariableRow].getVariable(enteringVariableColumn);
			double k = pivotElement * -1 * this.equations[i].getVariable(enteringVariableColumn);
			for(int j = 0; j < this.equations[i].getvariableAmount(); j++){
				double changeValue = k * this.equations[departingVariableRow].getVariable(j);
				if(i != departingVariableRow){
				this.equations[i].setVariable(this.equations[i].getVariable(j) + changeValue ,  j);
				}
			}
		}
	}

	/**
	 * Update the pivot value of the variables
	 * @param departingVariablerow Eliminates the value
	 * @param enteringVariableColumn Enter the value
	 */
	private void updatePivotRow(int departingVariableRow, int enteringVariableColumn) {
		double pivotElement = this.equations[departingVariableRow].getVariable(enteringVariableColumn);
		for(int i = 0; i < this.equations[departingVariableRow].getvariableAmount(); i++){
			this.equations[departingVariableRow].setVariable(this.equations[departingVariableRow].getVariable(i)/pivotElement, i);
		}
	}

	/**
	 * Locates the Optimal value
	 */
	private boolean isOptimal() {
		for(int i = 0; i < this.equations[3].getvariableAmount(); i++){
			if(this.equations[3].getVariable(i) < 0)
				return false;
		}
		return true;
	}

	/**
	 * Determines the negative objectives functions
	 */
	private void negObjectiveFunction() {
		for(int i = 0; i < this.equations[3].getvariableAmount(); i++){
			if(this.equations[3].getVariable(i) != 0)
				this.equations[3].setVariable((this.equations[3].getVariable(i)) * -1, i);
		}
		JOptionPane.showMessageDialog(null, "Solving objective function....\n\n" + this.toString());
	}

	/**
	 * Sets temporarly variables to switch them to the solved equation
	 */
	private void transpose(){
		double temporaryVariables[][] = new double[equations.length][3];
		temporaryVariables[0] = this.equations[0].getVariables();
		temporaryVariables[1] = this.equations[1].getVariables();
		temporaryVariables[2] = this.equations[2].getVariables();
		double transposedVariables[][] = new double[4][equations.length];
		for(int i = 0; i < temporaryVariables.length; i++){
			transposedVariables[0][i] = temporaryVariables[i][0];
		}
		for(int i = 0; i < temporaryVariables.length; i++){
			transposedVariables[1][i] = temporaryVariables[i][1];
		}
		for(int i = 0; i < temporaryVariables.length; i++){
			transposedVariables[2][i] = temporaryVariables[i][2];
		}
		for(int i = 0; i < temporaryVariables.length; i++){
			transposedVariables[3][i] = temporaryVariables[i][3];
		}
		System.out.println("Transposed variables");
		for(int i = 0; i < transposedVariables.length; i++){
			for(int j = 0; j < transposedVariables[i].length; j++){
				System.out.print(transposedVariables[i][j] + " ");
			}
			System.out.println();
		}
		MultiVariableLinearEquation newEquations[] = new MultiVariableLinearEquation[4];
		newEquations[0] = new MultiVariableLinearEquation(transposedVariables[0]);
		newEquations[1] = new MultiVariableLinearEquation(transposedVariables[1]);
		newEquations[2] = new MultiVariableLinearEquation(transposedVariables[2]);
		newEquations[3] = new MultiVariableLinearEquation(transposedVariables[3]);
		this.equations = newEquations;
	    JOptionPane.showMessageDialog(null, "Transposing...\n \n" + this.toString());
	}
	
}
