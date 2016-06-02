
public class ClientStratifiedAmer {
	public static void main(String[] args) {
		long a = System.currentTimeMillis();
		
		double spot  = 41.0;
		double rate = .08;
		double volatility = 0.3;
		double dividend = 0.00;		
		double strike = 40.0;
		double expiry = 1.0;		
		int steps = 8;
		int paths = 10;

		MarketData data = new MarketData( rate, spot, volatility, dividend);
		MonteCarlo mc = new MonteCarlo();
		
		payoffs payoff = new Put();
		Option option = new VanillaOption(strike, expiry, payoff);
		Pricers pricer = new StratifiedAmer(mc);
		Engine engine = new MonteCarloEngine(steps, paths, pricer);
		Facade option1 = new Facade(option,engine,data);
		
		double price = option1.price();
		System.out.println(price);
		
		long b = System.currentTimeMillis();
		long c = b-a;
		System.out.println();
		System.out.println(c);		
	}
}
