
public class AmerBinomPricer implements Pricers {

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
		double pd = 1 - pu;
		double disc = Math.exp(-rate * dt);
		double dpu = disc * pu;
		double dpd = disc * pd;
		
		double[] ct = new double[nodes];
		double[] st = new double[nodes];
		
		for (int i=0; i < nodes; i++) {
			st[i] = spot * (Math.pow(u, steps-i))*(Math.pow(d, i));
			ct[i] = option.payoff(strike, st[i]);
		}
		
		for (int i=steps-1; i >= 0; i--) {
			for (int j=0; j <= i; j++ ) {
				ct[j]  = dpu * ct[j] + dpd * ct[j+1];
				st[j] = st[j] / u;
				ct[j] = Math.max(ct[j], option.payoff(strike, st[j]));
			}
		}
		
		return ct[0];
	}
	
}