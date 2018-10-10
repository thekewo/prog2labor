import java.io.*;
import java.util.*;

public class DataReader {
    public int num_labels = -1;
    public int num_features = -1;

    public DataReader() {}

    /*
      Given an input file, return a set of instances
    */
    public Instance[] getInstances(String file) throws IOException {

	ArrayList inst_list = new ArrayList();

	BufferedReader in = new BufferedReader(new FileReader(file));
	String line = in.readLine();

	while(line != null) {

	    int lab = 0;
	    double[] features = new double[num_features];
	    String[] toks = line.split(" ");
	    lab = Integer.parseInt(toks[0]);
	    for(int i = 1; i < toks.length; i++) {
		int f = Integer.parseInt(toks[i].split(":")[0]);
		double v = Double.parseDouble(toks[i].split(":")[1]);
		features[f] = v;
	    }
	    inst_list.add(new Instance(lab,features));

	    line = in.readLine();
	}

	Instance[] list = new Instance[inst_list.size()];
	for(int i = 0; i < list.length; i++)
	    list[i] = (Instance)inst_list.get(i);

	return list;
    }

    /*
      Initialize the reader by setting the number of labels and feature dynamically
    */
    public void init(String[] files) throws IOException {
	int maxFeature = -1;

	for(int j = 0; j < files.length; j++) {
	    BufferedReader in = new BufferedReader(new FileReader(files[j]));
	    String line = in.readLine();
	    while(line != null) {
		String[] toks = line.split(" ");
		int l = Integer.parseInt(toks[0]);
		if(l > num_labels) num_labels = l;
		for(int i = 1; i < toks.length; i++) {
		    int f = Integer.parseInt(toks[i].split(":")[0]);
		    if(f > num_features) num_features = f;
		}
		line = in.readLine();
	    }
	}

	num_labels++;
	num_features++;

	System.out.println("Number of labels: " + num_labels);
	System.out.println("Number of input features: " + num_features);
	Instance.reader = this;
	PerceptronModel.reader = this;
    }

}
