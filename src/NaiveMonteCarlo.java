public class NaiveMonteCarlo implements Pricers{
	mcarlo mc;
	
	public NaiveMonteCarlo(mcarlo mc){
		this.mc = mc;
	}

	@Override
	public double price(int steps, int paths, Option option, MarketData data) {
		double expiry = option.getexpiry();
		double spot = data.getSpot();
		double rate = data.getRate();
		double strike = option.getStrike();
		double dt = expiry / steps;	
		double sum_CT = 0;
			
		double[][] patharray = mc.mcarloArray(steps,paths,data,dt);
		
		for (int i = 0; i < paths; i++) {
			sum_CT += Math.max(0,option.payoff(strike, patharray[i][steps-1]));
		}
		
		double price = sum_CT/paths * Math.exp(-rate * expiry);

		
		
		
		return price;
	}


}
