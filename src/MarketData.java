
public class MarketData {
	double rate, spot, volatility, dividend;
	
	public MarketData(double rate, double spot, double volatility, double dividend) {
		this.rate = rate;
		this.spot = spot;
		this.dividend = dividend;
		this.volatility = volatility;
	}
	
	public double getRate() {
		return rate;
	}

	public void setRate(double newRate) {
		this.rate = newRate;
	}
	
	public double getSpot() {
		return spot;
	}

	public void setSpot(double newSpot) {
		this.spot = newSpot;
	}
	
	public double getVolatility() {
		return volatility;
	}

	public void setVolatility(double newVolatility) {
		this.volatility = newVolatility;
	}
	
	public double getDividend() {
		return dividend;
	}

	public void setDividend(double newDividend) {
		this.dividend = newDividend;
	}


}
