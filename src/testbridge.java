import org.apache.commons.math3.distribution.NormalDistribution;

public class testbridge {

	public static double[][] bridge(double spot, double T, int numSteps,double volatility, double rate, double dividend) {
		NormalDistribution rand = new NormalDistribution();
		
		double numBisections = Math.log(numSteps)/Math.log(2);
		if (Math.round(numBisections) != numBisections) {
			System.out.println("Error: numSteps must be a power of 2");
			double[][] patharray = new double[2][2];
			return patharray;
		}
		double[] path = new double[numSteps+2];
		path[1] = spot;
		double drift = (rate - dividend - .5*volatility*volatility)*T;
		double diffusion = rand.sample()*volatility*Math.sqrt(T);
		path[numSteps+1] = spot * Math.exp(drift + diffusion);
		double TJump = T;
		int IJump = numSteps;
		for(int k = 1; k <= (int)numBisections;k++) {
			int left = 1;
			int i = IJump/2+1;
			int right = IJump+1;
			for(int j=1; j<= Math.pow(2, k-1); j++) {
				double a = .5 * (path[left] + path[right]);
				double b = .5 * Math.sqrt(TJump);
				path[i] = a + b * rand.sample();//should volatility be there?
				right += IJump;
				left += IJump;
				i += IJump;
			}
			IJump /= 2;
			TJump /= 2;
		}
		System.out.println(path);
		
		double[][] patharray = new double[2][2];
		return patharray;
	}
	
	public static void main(String[] args){
		bridge(41,1,16,.3,.08,.01);
	}

}
