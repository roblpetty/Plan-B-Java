import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class threadedamerican {
	private static class test implements Runnable{
		double spot;
		double rate;
		double volatility;
		double dividend;	
		double strike;
		double expiry;		
		int steps;
		static int paths;
		static long start;	
		static long end;
		static ArrayList<Double> prices;
		static int threads;
		
		
		public void run(){
			spot  = 41.0;
			rate = .08;
			volatility = 0.3;
			dividend = 0.00;		
			strike = 40.0;
			expiry = 1.0;		
			steps = 256;
				
			MarketData data = new MarketData( rate, spot, volatility, dividend);
			MonteCarlo mc = new NaiveMonteCarlo(steps, paths, data, expiry);
			
			Payoffs payoff = new Put();
			Option option = new VanillaOption(strike, expiry, payoff);
			Pricers pricer = new LongstaffSchwartz(mc);
			Engine engine = new MonteCarloEngine(steps, paths, pricer);
			Facade option1 = new Facade(option,engine,data);
			
			double price = option1.price();
//			System.out.println(price);
			prices.add(price);
			end = System.currentTimeMillis();
			System.out.println(end - start);
//			System.out.println(prices);
			price = 0;
			if (prices.size()==threads){
				for (int j=0; j<threads;j++){
					price += prices.get(j);
				}			
			price/=threads;  
			System.out.println(price);
			}
		}
		
	}
	
	public static void main(String[] args) {
		test.prices = new ArrayList<Double>();
		test.threads = 50;
		test.start = System.currentTimeMillis();
		test.paths = Math.round(100000/test.threads);
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for (int i=0; i<test.threads; i++){
			threadPool.execute(new test());
			System.out.println("this: "+Thread.activeCount());
		}
		threadPool.shutdown();
		
	}
}
