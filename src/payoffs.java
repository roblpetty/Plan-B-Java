public interface payoffs{
	double payoff(double strike, double spot);
}

class Call implements payoffs{	

	public double payoff(double strike, double spot) {
		return Math.max(spot - strike, 0);
	}
}

class Put implements payoffs{
	double strike;
	
	public double payoff(double strike, double spot) {
		return Math.max(strike - spot, 0);
	}
}

class Weird implements payoffs{
	double strike;
	
	public double payoff(double strike, double spot) {
		return spot*spot;
	}
}