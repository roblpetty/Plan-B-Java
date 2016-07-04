import java.util.Arrays;

import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class AsianAntitheticStratifiedMonteCarlo extends MonteCarlo{
	UniformRealDistribution randUnif;
	double sample;
	double epsilon;
	double[] spot_T;
	MarketData data;
	
	public AsianAntitheticStratifiedMonteCarlo(int steps, int paths, MarketData data, double expiry) {
		super(steps, paths, data, expiry);
		this.randUnif = new UniformRealDistribution();
		this.spot_T = new double[paths*2];
		this.diffusion = volatility * Math.sqrt(expiry);
		this.drift = (rate - dividend - 0.5 * volatility * volatility) * expiry;
		this.data = data;
	}
	
	@Override
	public double[][] fullPathArray(){
		Mean mean = new Mean();
		AntitheticStratifiedMonteCarlo mc = new AntitheticStratifiedMonteCarlo(steps, paths, data, expiry);
		double[][] patharray = mc.fullPathArray();
		for (int i=0; i <paths*2;i++){
			for (int j=steps; j>=0; j--){
				patharray[i][j] = mean.evaluate(Arrays.copyOfRange(patharray[i], 0, j+1)); 
			}
		}
		return patharray;
	}

	@Override
	double[] endPathArray() {
		double[] spot_T = new double[paths*2];
		double[][] patharray = fullPathArray();
		for(int i=0; i<paths*2;i++){
			spot_T[i]=patharray[i][steps];
		}
		
		return spot_T;
	}
}
