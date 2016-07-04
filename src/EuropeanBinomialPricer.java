import org.apache.commons.math3.distribution.BinomialDistribution;

public class EuropeanBinomialPricer implements Pricers {

	public double price(int steps, int paths, Option option, MarketData data) {
		double expiry = option.getexpiry();
		double spot = data.getSpot();
		double rate = data.getRate();
		double volatility = data.getVolatility();
		double dividend = data.getDividend();
		double strike = option.getStrike();

		int nodes = steps + 1;
		double dt = expiry / steps;
		double u = Math.exp(((rate - dividend) * dt) + volatility * Math.sqrt(dt));
		double d = Math.exp(((rate - dividend) * dt) - volatility * Math.sqrt(dt));
		double pu = (Math.exp((rate - dividend) * dt) - d) / (u -d);
		double disc = Math.exp(-rate * expiry);
		double spotT = 0.0;
		double payoffT =0.0;
		BinomialDistribution pmf = new BinomialDistribution(steps,pu);
		
		for(int i = 0; i < nodes; i++) {
			spotT = spot * Math.pow(u, (steps-i))*(Math.pow(d, i));
			payoffT += option.payoff(strike,spotT) * pmf.probability(steps - i); 
		}
		double price = disc * payoffT;

		return price;
	}
	
}