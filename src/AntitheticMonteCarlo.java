public class AntitheticMonteCarlo extends MonteCarlo{
	
	public AntitheticMonteCarlo(int steps, int paths, MarketData data, double expiry){
		super(steps, paths, data, expiry);
	}
	
	@Override
	public double[][] fullPathArray(){
				
		double[][] patharray = new double[paths*2][steps+1];		
		
		for (int i=0; i<paths; i++) {
			patharray[i][0]= spot;
			patharray[i+paths][0] = spot;
			for (int j=1; j<=steps; j++) {
				double epsilon = randNorm.sample();
				patharray[i][j] = patharray[i][j-1] * Math.exp(drift + diffusion * epsilon);
				patharray[i+paths][j] = patharray[i+paths][j-1] * Math.exp(drift - diffusion * epsilon);
			}
		}
		return patharray;	
	}

	@Override
	public double[] endPathArray() {
			
		double[] spot_t = new double[paths*2];
		
		for (int i = 0; i < paths; i++) {
			spot_t[i] = spot;
			spot_t[i+paths] = spot;
			for (int j = 0; j < steps; j++) {
				spot_t[i] *= Math.exp(drift + diffusion * randNorm.sample());
				spot_t[i+paths] *= Math.exp(drift - diffusion * randNorm.sample());
			}
		}
		return spot_t;
	}
}