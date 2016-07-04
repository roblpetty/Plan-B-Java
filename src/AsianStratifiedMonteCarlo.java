import java.util.Arrays;

import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class AsianStratifiedMonteCarlo extends MonteCarlo{
	UniformRealDistribution randUnif;
	double sample;
	double epsilon;
	double[] spot_T;
	MarketData data;
	
	public AsianStratifiedMonteCarlo(int steps, int paths, MarketData data, double expiry) {
		super(steps, paths, data, expiry);
		this.randUnif = new UniformRealDistribution();
		this.spot_T = new double[paths];
		this.diffusion = volatility * Math.sqrt(expiry);
		this.drift = (rate - dividend - 0.5 * volatility * volatility) * expiry;
		this.data = data;
	}
	
	@Override
	public double[][] fullPathArray(){
		Mean mean = new Mean();
		StratifiedMonteCarlo mc = new StratifiedMonteCarlo(steps, paths, data, expiry);
		double[][] patharray = mc.fullPathArray();
		for (int i=0; i <paths;i++){
			for (int j=steps; j>=0; j--){
				patharray[i][j] = mean.evaluate(Arrays.copyOfRange(patharray[i], 0, j+1)); 
			}
		}
		return patharray;
	}

	@Override
	double[] endPathArray() {
		double[] spot_T = new double[paths];
		double[][] patharray = fullPathArray();
		for(int i=0; i<paths;i++){
			spot_T[i]=patharray[i][steps];
		}
		
		return spot_T;
	}
}
