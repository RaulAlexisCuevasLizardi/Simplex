/**
 * University of Puerto Rico at Bayamon
 * Department of Computer Science
 * SICI 4028 - Operations Investigation, Spring 2017
 * Prof. Luis Ortiz
 */

package version1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

    /**
	 * Class that creates the GUI application
	 * Raul Cuevas {@literal <raul.cuevas@upr.edu>}
	 */
@SuppressWarnings("serial")
public class SimplexMinimization extends JFrame implements ActionListener{
	/**First Equation Variables*/
	static double eq1Variables[] = new double[4];
	/**Second Equation Variables*/
	static double eq2Variables[] = new double[4];
	/**Function of the Equation Variables*/
	static double functionEqVariables[] = new double[4];
	/**Result of Objective Function*/
	static double objectiveFunctionResult;
	/**First Equation Result*/
	static double eq1Result;
	/**Second Equation Result*/
	static double eq2Result;
	/**Simplex Table*/
	static SimplexTable table;
	/**Objective Function*/
	static MultiVariableLinearEquation objectiveFunction;
	/**First Equation of the Multi-variable*/
	static MultiVariableLinearEquation eq1;
	/**Second Equation of the Multi-variable*/
	static MultiVariableLinearEquation eq2;
	/**Creating an object type MultiVariableLinearEquation*/
	static MultiVariableLinearEquation[] equations = new MultiVariableLinearEquation[3];
	/**Creating First Textfild*/
	static JTextField tf1;
	/**Creating Second Textfild*/
	static JTextField tf2;
	/**Creating Third Textfild*/
	static JTextField tf3; 
	/**Creating Fourth Textfild*/
	static JTextField tf4; 
	/**Creating Counter*/
	static int counter = 0;
	/**Creating First Window*/
	static SimplexMinimization window1;
	/**Creating Second Window*/
	static SimplexMinimization window2;
	/**Creating Third Window*/
	static SimplexMinimization window3;
	
	/**
	 * Creates a new MultiVariableLinearEquation. This assumes that the third array is the objective function.
	 * @param euqationString The string representation of the equation
	 */
	public SimplexMinimization(String equationString){
		super("Simplex Minimization");
		setLayout(new GridLayout(3, 0, 0, 0));
		GridLayout layout = new GridLayout(4, 2, 30, 30);
		JPanel panel = new JPanel(layout);
		JLabel label = new JLabel(equationString);
		add(label);
		JLabel label2 = new JLabel("Variable 1");
		JLabel label3 = new JLabel("Variable 2");
		JLabel label4 = new JLabel("Variable 3");
		JLabel label5 = new JLabel("Result");
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		panel.add(label2);
		panel.add(tf1);
		panel.add(label3);
		panel.add(tf2);
		panel.add(label4);
		panel.add(tf3);
		panel.add(label5);
		panel.add(tf4);
		add(panel);
		JButton b = new JButton("OK");
		add(b);
		b.addActionListener(this);
		setSize(300, 800);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Method that Runs the application
	 */
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, 
				"Welcome to our Simplex Minimization program.\n\nProject done by:\nRaul A. Cuevas 841-11-1795\nJoel E. Lopez Aviles 841-12-3519\nAlonso O. Hernandez Cruz 841-11-3230\nKevin Rodriguez Oquendo 841-13-7620",
				"Simplex Minimization",
				JOptionPane.PLAIN_MESSAGE);
		window1 = new SimplexMinimization("Enter the objective function's variables");
	}

	/**
	 * Modifieds the events to interact with the client
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		if(counter == 0){
			functionEqVariables[0] = Double.parseDouble(tf1.getText());
			functionEqVariables[1] = Double.parseDouble(tf2.getText());
			functionEqVariables[2] = Double.parseDouble(tf3.getText());
			System.out.println(tf1.getText() + " " + tf2.getText() + " " + tf3.getText() + " " + tf4.getText());
			//This one should always be zero.
			functionEqVariables[3] = 0;
			window2 = new SimplexMinimization("Enter the first equation's variables");
			counter++;
		}else if(counter == 1){
			eq1Variables[0] = Double.parseDouble(tf1.getText());
			eq1Variables[1] = Double.parseDouble(tf2.getText());
			eq1Variables[2] = Double.parseDouble(tf3.getText());
			eq1Variables[3] = Double.parseDouble(tf4.getText());
			System.out.println(tf1.getText() + " " + tf2.getText() + " " + tf3.getText() + " " + tf4.getText());
			window3 = new SimplexMinimization("Enter the second equation's variables");
			counter++;
		}else if(counter == 2){
			eq2Variables[0] = Double.parseDouble(tf1.getText());
			eq2Variables[1] = Double.parseDouble(tf2.getText());
			eq2Variables[2] = Double.parseDouble(tf3.getText());
			eq2Variables[3] = Double.parseDouble(tf4.getText());
			System.out.println(tf1.getText() + " " + tf2.getText() + " " + tf3.getText() + " " + tf4.getText());
			counter++;
			objectiveFunction = new MultiVariableLinearEquation(functionEqVariables);
			eq1 = new MultiVariableLinearEquation(eq1Variables);
			eq2 = new MultiVariableLinearEquation(eq2Variables);
			equations[0] = eq1;
			equations[1] = eq2;
			equations[2] = objectiveFunction;
			table = new SimplexTable(equations);
			table.solveTable();
			JOptionPane.showMessageDialog(null, table.toString());
		}
	}
}
