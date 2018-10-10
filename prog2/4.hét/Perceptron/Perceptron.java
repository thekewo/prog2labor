import java.io.*;

public class Perceptron {
    public static void main(String[] args) throws IOException {

	DataReader reader = new DataReader();
	reader.init(args);

	Instance[] train_data = reader.getInstances(args[0]);
	Instance[] test_data = reader.getInstances(args[1]);

	PerceptronModel model = learn(train_data, 5);

	System.out.println("\n\nRegular Perceptron======================================================");
	System.out.println("Final Training Accuracy: " + model.accuracy(train_data));
	System.out.println("Final Testing Accuracy: " + model.accuracy(test_data));
	System.out.println("======================================================");

	model.setToAvgWeights();
	System.out.println("\n\nAveraged Perceptron\n======================================================");
	System.out.println("Final Training Accuracy: " + model.accuracy(train_data));
	System.out.println("Final Testing Accuracy: " + model.accuracy(test_data));
	System.out.println("======================================================");
    }

    public static PerceptronModel learn(Instance[] train_data, int num_iters) {
	PerceptronModel model = new PerceptronModel();

	/*

	  Fill in the main body of the perceptron algorithm

	*/

	return model; // Returns a model with all weights = 0;
    }

}
