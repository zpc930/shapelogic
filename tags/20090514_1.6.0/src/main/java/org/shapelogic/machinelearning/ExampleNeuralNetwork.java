package org.shapelogic.machinelearning;

import java.io.Reader;
import java.io.StringReader;

/** ExampleNeuralNetwork for use in default neural network.<br />
 *
 * @author Sami Badawi
 */
public class ExampleNeuralNetwork {

    public static double[][] makeSmallerThanNeuralNetwork(double limit) {
        return new double[][] {
            {
                limit, //Bias first hidden layer

                -1.
            }
        };
    }

    public static double[][] makeGreaterThanNeuralNetwork(double limit) {
        return new double[][] {
            {
                -limit, //Bias first hidden layer

                1.
            }
        };
    }

    public static double[][] makeSmallerThanGreaterThanNeuralNetwork(double limit) {
        return new double[][] {
            {
                limit, -limit, //Bias first hidden layer

                -1., 1
            },
        };
    }
    
	public final static String DEFAULT_NETWORK = 
		"========== FEATURES\n" +
		"aspect\n" +
		"========== RESULTS\n" +
		"Tall\n" +
		"Flat\n" +
		"========== WEIGHTS\n" +
		"1. -1. \n" +
		"-1. 1. \n";


}
