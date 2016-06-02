import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class StratifiedEuroPricer implements Pricers{

	@Override
	public double price(int steps, int paths, Option option, MarketData data) {
		double expiry = option.getexpiry();
		double spot = data.getSpot();
		double volatility = data.getVolatility();
		double rate = data.getRate();
		double dividend = data.getDividend();
		double strike = option.getStrike();
		double nudt = (rate - dividend - .5*volatility*volatility)*expiry;
		double voldt = volatility * Math.sqrt(expiry);

		double[] spot_T = new double[paths];
		double[] payoff_T = new double[paths];
		double sample;
		double stratSample;
		double epsilon;
		UniformRealDistribution rand = new UniformRealDistribution();
		NormalDistribution norm = new NormalDistribution();
		
		for (int i = 0; i<paths; i++) {
			sample = rand.sample();
			stratSample = (i + sample)/paths;
			epsilon = norm.inverseCumulativeProbability(stratSample);
			spot_T[i] = spot*Math.exp(nudt + voldt * epsilon);
			payoff_T[i] = option.payoff(strike,spot_T[i]);
		}
		Mean avg = new Mean();
		double price = avg.evaluate(payoff_T)*Math.exp(-rate*expiry);		
		StandardDeviation std = new StandardDeviation();
		double stderr = std.evaluate(payoff_T)/Math.sqrt(paths);
		System.out.println("The standard error is: "+stderr);
		return price;
	}

}
