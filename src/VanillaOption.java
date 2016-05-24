
public class VanillaOption extends Option{

	public VanillaOption(double strike, double expiry, payoffs payoffType){
		super(strike, expiry, payoffType);	

	}

	public double payoff(double strike, double spot){
		return payoffType.payoff(strike, spot);
	}



}
	
	

