import java.util.Arrays;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import org.apache.commons.math3.distribution.UniformRealDistribution;

public class AsianAntitheticMonteCarlo extends MonteCarlo{
	UniformRealDistribution randUnif;
	double sample;
	double epsilon;
	double[] spot_T;
	MarketData data;
	
	public AsianAntitheticMonteCarlo(int steps, int paths, MarketData data, double expiry) {
		super(steps, paths, data, expiry);
		this.data = data;
		this.randUnif = new UniformRealDistribution();
		this.spot_T = new double[paths*2];
		this.diffusion = volatility * Math.sqrt(expiry);
		this.drift = (rate - dividend - 0.5 * volatility * volatility) * expiry;
	}
	
	@Override
	public double[][] fullPathArray(){
		Mean mean = new Mean();
		AntitheticMonteCarlo mc = new AntitheticMonteCarlo(steps, paths, data, expiry);
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
		
		double[] spot_t = new double[paths*2];
		double sum;
		double sum2;
		
		for (int i = 0; i < paths; i++) {
			spot_t[i] = spot;
			spot_t[i+paths] = spot;
			sum = spot;
			sum2 = spot;
			for (int j = 0; j < steps; j++) {
				epsilon = randNorm.sample();
				spot_t[i] *= Math.exp(drift + diffusion * epsilon);
				spot_t[i+paths] *= Math.exp(drift + diffusion * -epsilon);
				sum += spot_t[i];
				sum2 += spot_t[i+paths];
			}
			spot_t[i] = sum/(steps+1);
			spot_t[i+paths]=sum2/(steps+1);
		}
		return spot_t;
	}
}
