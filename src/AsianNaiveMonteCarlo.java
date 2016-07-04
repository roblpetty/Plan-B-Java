import java.util.Arrays;

import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class AsianNaiveMonteCarlo extends MonteCarlo{

	public AsianNaiveMonteCarlo(int steps, int paths, MarketData data, double expiry){
		super(steps, paths, data, expiry);
	}
	
	@Override
	public double[][] fullPathArray(){
		Mean mean = new Mean();	
		double[][] patharray = new double[paths][steps+1];		
		
		for (int i=0; i<paths; i++) {
			patharray[i][0]= spot;
			for (int j=1; j<=steps; j++) {
				double epsilon = randNorm.sample();
				patharray[i][j] = patharray[i][j-1] * Math.exp(drift + diffusion * epsilon);			
			}
			for (int j=steps; j>=0; j--){
				patharray[i][j] = mean.evaluate(Arrays.copyOfRange(patharray[i], 0, j+1)); 
			}
		}
		return patharray;	
	}
	
	
	@Override
	public double[] endPathArray() {
			
		double[] spot_t = new double[paths];
		double sum;
		
		for (int i = 0; i < paths; i++) {
			spot_t[i] = spot;
			sum = spot;
			for (int j = 0; j < steps; j++) {
				spot_t[i] *= Math.exp(drift + diffusion * randNorm.sample());
				sum += spot_t[i];
			}
			spot_t[i] = sum/(steps+1);
		}
		return spot_t;
	}
	
	

	
	
}
