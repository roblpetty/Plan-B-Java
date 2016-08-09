import java.util.ArrayList;
import org.apache.commons.math3.stat.regression.*;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class LongstaffSchwartz implements Pricers{
	
	MonteCarlo mc;
	
	public LongstaffSchwartz(MonteCarlo mc){
		this.mc = mc;
	}
	
	public double price(int steps, int paths, Option option, MarketData data) {
		double expiry = option.getexpiry();
		double rate = data.getRate();
		double strike = option.strike;
		
		double dt = expiry / steps;
		double[][] pricearray = mc.fullPathArray();
		//paths=pricearray.length;
		
		double[][] cashflowarray = new double[paths][steps+1];
		for(int row = 0; row<paths; row++){
			for(int col=0; col<=steps; col++) {
				cashflowarray[row][col] = option.payoff(strike, pricearray[row][col]);
			}
		}

		double[][] exercisearray = new double[paths][steps + 1];
		for(int row = 0; row<paths; row++){
			if (cashflowarray[row][steps] > 0) {
				exercisearray[row][steps] = 1;
			} else {exercisearray[row][steps] = 0;}
		}

//----------Done Setting up arrays-----------------------------------------------------------		
		OLSMultipleLinearRegression regress = new OLSMultipleLinearRegression();
	
		int T;
		for(int i=steps; i>1; i--) {

			double[] cashflowvect = new double[paths];
			for(int row=0; row<paths; row++) {
				for(int col=0; col<=steps; col++) {
					if (exercisearray[row][col]==1.0) {
						T = col-(i-1);
						cashflowvect[row]= cashflowarray[row][col]*Math.exp(-rate*dt*T);
						break;
					} else {cashflowvect[row] = 0;}
				}
			}
			ArrayList<Double> Y = new ArrayList<Double>();
			ArrayList<Double> X = new ArrayList<Double>();
			for(int row=0; row<paths; row++) {
				if (option.payoff(strike, pricearray[row][i-1])>0) { 
				X.add(pricearray[row][i-1]);
				Y.add(cashflowvect[row]);
				}
			}
			double[] Ynew = new double[Y.size()];
			double[][] Xnew = new double[X.size()][2];
			for (int row=0;row<Xnew.length; row++) {
				Xnew[row][0] = X.get(row);
				Xnew[row][1] = Xnew[row][0] * Xnew[row][0];
				Ynew[row] = Y.get(row);
			}
			double[] regresults = {0,0,0};
			try{
				regress.newSampleData(Ynew, Xnew);
				regresults = regress.estimateRegressionParameters();
			}
			catch(Exception e){   //TODO : get more specific
			}
	
			double[] continuation = new double[paths]; 
			for (int row=0; row<paths; row++) {
				if(cashflowarray[row][i-1] == 0) {
					continuation[row] = 0;
				} else {
					double x = pricearray[row][i-1];
					continuation[row] = regresults[0]+regresults[1]*x +regresults[2]*x*x;
				}
				if (cashflowarray[row][i-1] > continuation[row]){
					exercisearray[row][i-1] = 1;
				} else {
					exercisearray[row][i-1] = 0;
				}
				if (exercisearray[row][i-1] == 1) {
					for(int col=i; col <= steps; col++) {
						exercisearray[row][col] = 0; 
					}
				}
			}
		}
		
		double[] discountvect = new double[steps+1];
		for(int col=0; col <= steps; col++){
			discountvect[col] = Math.exp(-rate*dt*col);
		}

		double price = 0;
		double[] cf = new double[paths];
		for(int row=0; row<paths;row++){
			cf[row]=0;
			for(int col=0; col<=steps; col++) {
				double cflow =cashflowarray[row][col] * exercisearray[row][col] * discountvect[col]; 
				price += cflow;
						
				if (cashflowarray[row][col] * exercisearray[row][col] * discountvect[col]>0){
					cf[row] = cflow;
				}		
				
			}
		}
		price /= paths;
		StandardDeviation stdev = new StandardDeviation();
		double stderr = stdev.evaluate(cf)/Math.sqrt(paths);
		System.out.println("The standard error is: "+stderr);
		return price;
	}

}
