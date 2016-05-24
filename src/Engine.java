
abstract class Engine {
	int steps, paths;
	public Pricers pricer;

	public Engine(int steps, int paths, Pricers pricer){
		this.steps = steps;
		this.paths = paths;
		this.pricer = pricer;
	}
	
	abstract double calculate(Option option, MarketData data, int paths);
	
	int getSteps(){
		return steps;
	}
	
	void setSteps(int newSteps){
		this.steps = newSteps;
	} 
	
	int getPaths(){
		return paths;
	}
	
	void setPaths(int newPaths){
		this.paths = newPaths;
	} 
	
	
}
