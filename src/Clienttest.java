public class Clienttest {
	public static void main(String[] args) {
		
		for(int i=0;i<10;i++){
		long a = System.currentTimeMillis();
			System.out.println("time: " + a);
			
			double spot  = 41.0;
			double rate = .08;
			double volatility = 0.3;
			double dividend = 0.00;		
			double strike = 40.0;
			double expiry = 1.0;		
			int steps = 256;
			int paths = 100000;
	
			MarketData data = new MarketData( rate, spot, volatility, dividend);
			MonteCarlo mc = new NaiveMonteCarlo(steps, paths, data, expiry);
			
			Payoffs payoff = new Put();
			Option option = new VanillaOption(strike, expiry, payoff);
			Pricers pricer = new LongstaffSchwartz(mc);
			Engine engine = new MonteCarloEngine(steps, paths, pricer);
			Facade option1 = new Facade(option,engine,data);
			
			double price = option1.price();
			System.out.println(price);
			
			long b = System.currentTimeMillis();
			System.out.println("time: "+b);
			long c = b-a;
			System.out.println("iteration: "+i );
			System.out.println(c);

		}
	}
}
