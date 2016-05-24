
public class BinomialPricingEngine extends Engine{

	public BinomialPricingEngine(int steps, int paths, Pricers pricer) {
		super(steps, paths, pricer);
	}

	public double calculate(Option option, MarketData data, int paths){		
		return pricer.price(steps,paths,option,data);
		
		
	}
	
}
