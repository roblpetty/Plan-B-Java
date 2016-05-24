import org.apache.commons.math3.distribution.NormalDistribution;

public class mcarlo {
	
	public double[][] mcarloArray(int steps, int paths, MarketData data, double dt){
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
	
}
