public class ClientAmerAntithetic {
	public static void main(String[] args) {
		long a = System.currentTimeMillis();
		
		double spot  = 41.0;
		double rate = .08;
		double volatility = 0.3;
		double dividend = 0.00;		
		double strike = 40.0;
		double expiry = 1.0;		
		int steps = 512;
		int paths = 20000;

		MarketData data = new MarketData( rate, spot, volatility, dividend);
		MonteCarlo mc = new AntitheticMonteCarlo(steps, paths, data, expiry);
		
		Payoffs payoff = new Put();
		Option option = new VanillaOption(strike, expiry, payoff);
		Pricers pricer = new LongstaffSchwartz(mc);
		Engine engine = new MonteCarloEngine(steps, paths, pricer);
		Facade option1 = new Facade(option,engine,data);
		
		double price = option1.price();
		System.out.println("The option price is: "+price);
		
		long b = System.currentTimeMillis();
		long c = b-a;
		
		System.out.println("This took "+c+" milliseconds to run");		
	}
}
