import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class CVEuro implements Pricers{
	NormalDistribution rand = new NormalDistribution();
	
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
		double erddt = Math.exp((rate - dividend)*dt);
		double beta = -1.0;
		double[] cashflow_t = new double[paths];
		double price = 0.0;
		double spot_t;
		double t;
		double spot_tn;
		
		double delta;
		
		for (int j=0; j<paths; j++){
			spot_t = spot;
			double convar = 0.0;
			for (int i=0; i<steps; i++) {
				t = i*dt;
				delta = BlackScholesDelta(spot,t,strike,expiry,volatility,rate,dividend);
				spot_tn = spot_t * Math.exp(nudt + voldt*rand.sample());
				convar = convar + delta * (spot_tn - spot_t * erddt);
				spot_t = spot_tn;
			}
			cashflow_t[j]=option.payoff(strike, spot_t) + beta*convar;
		}
		
		Mean mean = new Mean();
		StandardDeviation stdev = new StandardDeviation();
		price = Math.exp(-rate*expiry) * mean.evaluate(cashflow_t);
		double stderr = stdev.evaluate(cashflow_t)/Math.sqrt(paths);
		System.out.println("The standard error is: "+stderr);
		return price;
	}

	private double BlackScholesDelta(double spot,double  t,double strike,double expiry,double volatility,double rate,double dividend){
		double tau = expiry-t;
		double d1 = (Math.log(spot/strike) + (rate-dividend+0.5*volatility*volatility)*tau)/(volatility*Math.sqrt(tau));
		double delta = Math.exp(-dividend*tau)*this.rand.cumulativeProbability(d1);
		return delta;
	}
	
}
