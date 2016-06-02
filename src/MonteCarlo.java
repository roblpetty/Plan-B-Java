import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;



public class MonteCarlo {
	
	public double[][] monteCarloArray(int steps, int paths, MarketData data, double dt){
		double spot = data.getSpot();
		double rate = data.getRate();
		double volatility = data.getVolatility();
		double dividend = data.getDividend();
		NormalDistribution randNorm = new NormalDistribution();
		//randNorm.reseedRandomGenerator(4678L);
		double drift = (rate - dividend - 0.5 * volatility * volatility) * dt;
		double diffusion = volatility * Math.sqrt(dt);				
		double[][] patharray = new double[paths][steps+1];		
		
		for (int i=0; i<paths; i++) {
			patharray[i][0]= spot;
			for (int j=1; j<=steps; j++) {
				double epsilon = randNorm.sample();
				patharray[i][j] = patharray[i][j-1] * Math.exp(drift + diffusion * epsilon);
			}
		}
		return patharray;	
	}
	
	public double[][] stratifiedMonteCarloArray(int steps, int paths, Option option, MarketData data, double dt){
		double spot = data.getSpot();
		double rate = data.getRate();
		double volatility = data.getVolatility();
		double dividend = data.getDividend();
		double expiry = option.getexpiry();
		
		double nudt = expiry/steps;
		double voldt = volatility * Math.sqrt(dt);
		double sample;
		double stratSample;
		double epsilon;
		double[] spot_T = new double[paths];
		double[] path = new double[steps+2];
		double[] pathshort = new double[steps+1];
		double[][] patharray = new double[paths][steps+1];
		UniformRealDistribution rand = new UniformRealDistribution();
		NormalDistribution norm = new NormalDistribution();
		
		double numBisections = Math.log(steps)/Math.log(2);
		if (Math.round(numBisections) != numBisections) {
			System.out.println("Error: numSteps must be a power of 2");
			return patharray;
		}		
		
		for (int i = 0; i<paths; i++) {
			sample = rand.sample();
			stratSample = (i + sample)/paths;
			epsilon = norm.inverseCumulativeProbability(stratSample);
			spot_T[i] = spot*Math.exp(nudt + voldt * epsilon);	
		}
		
		for(int row = 0; row<paths; row++){
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
					path[i] = a + b * rand.sample()*volatility;//should volatility be there?
					patharray[row][i-1] = path[i];
					right += IJump;
					left += IJump;
					i += IJump;
				}
				IJump /= 2;
				TJump /= 2;
			}
		
		}
		return patharray;
	}
	
}
