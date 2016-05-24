public class Facade{

	Option option;
	Engine engine;
	MarketData data;
	
	
	public Facade(Option option, Engine engine, MarketData data){

		this.option = option;
		this.engine = engine;
		this.data = data;	
	}
	
	public double price() {
		return this.engine.calculate(this.option, this.data, this.engine.paths);
	}
	
}