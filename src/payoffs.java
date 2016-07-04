public interface Payoffs{
	double payoff(double strike, double spot);
}

class Call implements Payoffs{	

	public double payoff(double strike, double spot) {
		return Math.max(spot - strike, 0);
	}
}

class Put implements Payoffs{
	double strike;
	
	public double payoff(double strike, double spot) {
		return Math.max(strike - spot, 0);
	}
}

class Weird implements Payoffs{
	double strike;
	
	public double payoff(double strike, double spot) {
		return spot*spot;
	}
}