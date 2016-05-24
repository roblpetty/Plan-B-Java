
public class GUIClient {
	
	
	public static double client (int steps, int paths, double spot, double rate, double volatility, double  dividend, double strike, double expiry, int optionType,int payoff, int pricer, boolean antithetic, boolean stratified, boolean cv) {
		Pricers pricers;
		Option option;
		payoffs payofftype;
		Engine engine;
		mcarlo mc;
		
		MarketData data = new MarketData(rate, spot, volatility, dividend);
		
		payofftype = new Call();
		switch (payoff){
		case 0:
			payofftype = new Call();
			break;
		case 1:
			payofftype = new Put();
			break;
		case 2:
			payofftype = new Weird();
			break;
		}
		
		option = new VanillaOption(strike, expiry, payofftype);
		switch (optionType) {
		case 0:
		case 1:
			option = new VanillaOption(strike, expiry, payofftype);
			break;		
		case 2:
			//option = new AsianOptoin(strike, expiry, payofftype);
			break;
		}

		pricers = new AmerBinomPricer();
		engine = new BinomialPricingEngine(steps, paths, pricers);
		switch (pricer){
		case 0:
			pricers = new AmerBinomPricer();
			engine = new BinomialPricingEngine(steps, paths, pricers);
			break;
		case 1:
			mc = new mcarlo();
			pricers = new LongstaffSchwartz(mc);
			engine = new MonteCarloEngine(steps, paths, pricers);
			break;
		case 2:
			pricers = new EuroBinomPricer();
			engine = new BinomialPricingEngine(steps, paths, pricers);
			break;
		case 3:
			mc = new mcarlo();
			pricers = new NaiveMonteCarlo(mc);
			engine = new MonteCarloEngine(steps, paths, pricers);
			break;
		case 4:
			//pricers = AsianPricer();
			//engine = new MonteCarloEngine();
			break;
		}
		
		Facade option1 = new Facade(option,engine,data);
		
		double price = option1.price();
		

		return price;
	}

}
