import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class EuropeanMonteCarloPricer implements Pricers{

	MonteCarlo mc;

	public EuropeanMonteCarloPricer(MonteCarlo mc){
		this.mc = mc;
	}
	
	@Override
	public double price(int steps, int paths, Option option, MarketData data) {
		double expiry = option.getexpiry();
		double rate = data.getRate();
		double strike = option.getStrike();
		double sum_CT = 0;
		
		double[] spot_T = mc.endPathArray();
		paths = spot_T.length;

		for (int i = 0; i < paths; i++) {
			spot_T[i] = Math.max(0,option.payoff(strike, spot_T[i]));
			sum_CT += spot_T[i];
		}
		
		double price = sum_CT/paths * Math.exp(-rate * expiry);
		StandardDeviation std = new StandardDeviation();
		double stderr = std.evaluate(spot_T)/Math.sqrt(paths);
		System.out.println("The standard error is: "+stderr);
		return price;
	}

}
