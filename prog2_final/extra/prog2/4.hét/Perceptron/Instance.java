public class Instance {
    public static DataReader reader;
    public int real_label = 0;
    public double[] feature_vals;

    public Instance(int real_label, double[] feature_vals) {
	this.real_label = real_label;
	this.feature_vals = feature_vals;
    }

    /*
      Given a set of weights and a possible label, return the score
      w * f(x,y), where x is this instance and y = label
     */
    public double getScore(double[] weights, int label) {
	double[] block_feature_vals = getFeatures(label);

	double score = 0.0;
	for(int i = 0; i < weights.length; i++)
	    score += weights[i] * block_feature_vals[i];

	return score;
    }

    /*
      Get a block feature representation f(x,y)
     */
    public double[] getFeatures(int label) {
	double[] block_feature_vals = new double[reader.num_features * reader.num_labels];
	for(int i = 0; i < feature_vals.length; i++) {
	    block_feature_vals[i + (label*reader.num_features)] = feature_vals[i];
	}

	return block_feature_vals;
    }

}
