
public class MonteCarloEngine extends Engine {
	int paths;
	 
	public MonteCarloEngine(int steps, int paths, Pricers pricer) {
		super(steps, paths, pricer);
	}

	@Override
	double calculate(Option option, MarketData data, int paths) {
		return pricer.price(steps,paths,option,data);
	}
	

	

}
