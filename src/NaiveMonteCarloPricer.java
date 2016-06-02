import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class NaiveMonteCarloPricer implements Pricers{

	@Override
	public double price(int steps, int paths, Option option, MarketData data) {
		double expiry = option.getexpiry();
		double spot = data.getSpot();
		double volatility = data.getVolatility();
		double rate = data.getRate();
		double dividend = data.getDividend();
		double strike = option.getStrike();
		double dt = expiry / steps;	
		double nudt = (rate - dividend - .5*volatility*volatility)*dt;
		double voldt = volatility * Math.sqrt(dt);
				
		double sum_CT = 0;
		double[] CT = new double[paths];
		double spot_t;
		NormalDistribution rand = new NormalDistribution();
		
		for (int i = 0; i < paths; i++) {
			spot_t = spot;
			for (int j = 0; j < steps; j++) {
				spot_t *= Math.exp(nudt + voldt * rand.sample());
			}
			CT[i] = Math.max(0,option.payoff(strike, spot_t));
			sum_CT += CT[i];
		}
		double price = sum_CT/paths * Math.exp(-rate * expiry);
		StandardDeviation std = new StandardDeviation();
		double stderr = std.evaluate(CT)/Math.sqrt(paths);
		System.out.println("The standard error is: "+stderr);
		return price;
	}

}
