
public class ClientEuroBinom {
	public static void main(String[] args) {
		long a = System.currentTimeMillis();
		
		double spot  = 41.0;
		double rate = .08;
		double volatility = 0.3;
		double dividend = 0.00;		
		double strike = 40.0;
		double expiry = 1.0;		
		int steps = 10000;
		int paths = 200;

		MarketData data = new MarketData( rate, spot, volatility, dividend);
	
		Payoffs payoff = new Put();
		Option option = new VanillaOption(strike, expiry, payoff);
		Pricers pricer = new EuropeanBinomialPricer();
		Engine engine = new BinomialPricingEngine(steps, paths, pricer);
		Facade option1 = new Facade(option,engine,data);
		
		double price = option1.price();
		System.out.print(price);
		
		long b = System.currentTimeMillis();
		long c = b-a;
		System.out.println();
		System.out.println(c);		
	}
}