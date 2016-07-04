import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class GUIClient {
	
	public static double client (int steps, int paths, double spot, double rate, double volatility, double  dividend, double strike, double expiry, int threads, int exerciseType,int payoff, int pricer, int MCType) {
		Pricers pricers;
		Option option;
		Payoffs payofftype;
		Engine engine;
		MonteCarlo mc;
		MarketData data = new MarketData(rate, spot, volatility, dividend);
		ArrayList<Double> prices;
		
/*		System.out.println("strike: "+strike);
		System.out.println("price: "+spot );
		System.out.println("expiry: "+expiry);
		System.out.println("steps: "+strike);
		System.out.println("paths: "+paths);
		System.out.println("rate: "+rate);
		System.out.println("volatility: "+ volatility);
		System.out.println("divided: "+ dividend);
*/

		
		steps = steps/threads;
		
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
		switch (exerciseType) {
		case 0:
		case 1:
			option = new VanillaOption(strike, expiry, payofftype);
			break;
		}

		switch(MCType){
		case 0:
			mc = new NaiveMonteCarlo(steps, paths, data, expiry);
			break;
		case 1:
			mc = new AntitheticMonteCarlo(steps, paths, data, expiry);
			break;
		case 2:
			mc = new StratifiedMonteCarlo(steps, paths, data, expiry);
			break;
		case 3:
			mc = new ControlVariateMonteCarlo(steps, paths, data, expiry);
			break;
		case 4:
			mc = new AntitheticStratifiedMonteCarlo(steps, paths, data, expiry);
			break;
	/*	case 5:
			mc = new Antithetic_CVMonteCarlo(steps, paths, data, expiry);
			break;
		case 6:
			mc = new Stratified_CVMonteCarlo(steps, paths, data, expiry);
			break;
		case 7:
			mc = new AllMonteCarlo(steps, paths, data, expiry);
			break; */
		case 8:
			mc = new AsianNaiveMonteCarlo(steps,paths,data,expiry);
			break;
		case 9:
			mc = new AsianAntitheticMonteCarlo(steps,paths,data,expiry);
			break;
		case 10:
			mc = new AsianStratifiedMonteCarlo(steps,paths,data,expiry);
			break;
		case 11:
			mc = new AsianAntitheticStratifiedMonteCarlo(steps,paths,data,expiry);
			break;
			
		default:
			mc = new NaiveMonteCarlo(steps, paths, data, expiry);
			break;
		}
		
		switch (pricer){
		case 0:
			pricers = new AmerBinomPricer();
			engine = new BinomialPricingEngine(steps, paths, pricers);
			break;
		case 1:
			pricers = new EuropeanBinomialPricer();
			engine = new BinomialPricingEngine(steps, paths, pricers);
			break;
		case 2:
			pricers = new LongstaffSchwartz(mc);
			engine = new MonteCarloEngine(steps, paths, pricers);
			break;
		case 3:
			pricers = new EuropeanMonteCarloPricer(mc);
			engine = new MonteCarloEngine(steps, paths, pricers);
			break;
		case 4:
			pricers = new LongstaffSchwartz(mc);
			engine = new MonteCarloEngine(steps, paths, pricers);
			break;
		case 5:
			pricers = new EuropeanMonteCarloPricer(mc);
			engine = new MonteCarloEngine(steps, paths, pricers);
			break;
		default:
			pricers = new AmerBinomPricer();
			engine = new BinomialPricingEngine(steps, paths, pricers);
			break;
		}
		
		System.out.println(spot);
		System.out.println(rate);
		System.out.println(volatility);
		System.out.println(dividend);
		System.out.println(strike);
		System.out.println(steps);
		System.out.println(paths);
		
		
		
		
		System.out.println(mc);
		System.out.println(payofftype);
		System.out.println(option);
		System.out.println(pricers);
		System.out.println(engine);

		
		
		
		
		
		Facade option1 = new Facade(option,engine,data);
		double price = option1.price();

		return price;
	}

}
