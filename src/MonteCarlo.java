import org.apache.commons.math3.distribution.NormalDistribution;

abstract class MonteCarlo {
	double spot;
	double rate;
	double volatility;
	double dividend;
	double dt;
	double drift;
	double diffusion;
	double expiry;
	int paths;
	int steps;
	NormalDistribution randNorm;
	
	public MonteCarlo(int steps, int paths, MarketData data, double expiry){
		this.spot = data.getSpot();
		this.rate = data.getRate();
		this.volatility = data.getVolatility();
		this.dividend = data.getDividend();
		this.steps = steps;
		this.paths = paths;
		this.expiry = expiry;
		this.dt = expiry/steps;
		this.drift = (rate - dividend - 0.5 * volatility * volatility) * dt;
		this.diffusion = volatility * Math.sqrt(dt);	
		this.randNorm = new NormalDistribution();
		//randNorm.reseedRandomGenerator(4678L);	
	}
	
	abstract double[][] fullPathArray();
	abstract double[] endPathArray();
}
