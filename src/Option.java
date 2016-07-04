
abstract class Option {
	double expiry;	
	double strike;
	Payoffs payoffType;
	
	public Option(double strike, double expiry, Payoffs payoffType) {
		setExpiry(expiry);
		setStrike(strike);
		this.payoffType = payoffType;
	}
	
	double getexpiry(){
		return expiry;
	}
	
	void setExpiry(double newExpiry){
		this.expiry = newExpiry;
	}
	
	double getStrike(){
		return strike;
	}
	
	void setStrike(double newStrike) {
		this.strike = newStrike;
	}
	
	abstract double payoff(double strike, double spot);
}
