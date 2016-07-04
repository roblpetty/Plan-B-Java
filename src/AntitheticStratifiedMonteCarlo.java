import org.apache.commons.math3.distribution.UniformRealDistribution;

public class AntitheticStratifiedMonteCarlo extends MonteCarlo{
	UniformRealDistribution randUnif;
	double sample;
	double epsilon;
	double[] spot_T;
	
	public AntitheticStratifiedMonteCarlo(int steps, int paths, MarketData data, double expiry) {
		super(steps, paths, data, expiry);
		this.randUnif = new UniformRealDistribution();
		this.spot_T = new double[paths*2];
		this.diffusion = volatility * Math.sqrt(expiry);
		this.drift = (rate - dividend - 0.5 * volatility * volatility) * expiry;
	}
	
	@Override
	public double[][] fullPathArray(){

		double[] path = new double[steps+2];
		double[][] patharray = new double[paths*2][steps+1];
		
		double numBisections = Math.log(steps)/Math.log(2);
		if (Math.round(numBisections) != numBisections) {
			System.out.println("Error: numSteps must be a power of 2");
			return patharray;
		}		
		
		for (int i = 0; i<paths; i++) {
			sample = randUnif.sample();
			sample = (i + sample)/paths;
			epsilon = randNorm.inverseCumulativeProbability(sample);
			spot_T[i] = spot*Math.exp(drift + diffusion * epsilon);	
			spot_T[i+paths] = spot*Math.exp(drift + diffusion * -epsilon);	
		}
		
		for(int row = 0; row<paths*2; row++){
			path[1] = spot;
			path[steps+1] = spot_T[row];
			patharray[row][0] = spot; 
			patharray[row][steps] = spot_T[row];

			double TJump = expiry;
			int IJump = steps;
			for(int k = 1; k <= (int)numBisections;k++) {
				int left = 1;
				int i = IJump/2+1;
				int right = IJump+1;
				for(int j=1; j<= Math.pow(2, k-1); j++) {
					double a = .5 * (path[left] + path[right]);
					double b = .5 * Math.sqrt(TJump);
					path[i] = a + b * randNorm.sample();
					patharray[row][i-1] = path[i];
					right += IJump;
					left += IJump;
					i += IJump;
				}
				IJump /= 2;
				TJump /= 2;
			}
		
		}
		System.out.println(patharray);
		return patharray;
	}

	@Override
	double[] endPathArray() {		
		for (int i = 0; i<paths; i++) {
			sample = randUnif.sample();
			sample = (i + sample)/paths;
			epsilon = randNorm.inverseCumulativeProbability(sample);
			spot_T[i] = spot*Math.exp(drift + diffusion * epsilon);
			spot_T[i+paths] = spot*Math.exp(drift + diffusion * -epsilon);
		}
		
		return spot_T;
	}
}
