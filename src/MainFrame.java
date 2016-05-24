
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ItemListener{
	JRadioButton americanVanilla,europeanVanilla, asian;
	JRadioButton call,put,weird;
	JRadioButton binomial, monteCarlo;
	JCheckBox stratified,antithetic, controlVariate;
	JCheckBox webSpot, webInterestRate, webVolatility, webDividend;
	JTextField strike, expiry, steps, paths;
	JTextField spot, interestRate, volatility, dividend;
	JLabel strikelab, expirylab, stepslab, pathslab;
	JLabel spotlab, interestRatelab, volatilitylab, dividendlab;
	JButton calc;
	JLabel price;

	
	MainFrame() {
		
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setTitle("Options Pricer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon("resources\\aamoney.png");
		this.setIconImage(icon.getImage());
		JPanel mainPanel = new JPanel();
		
		//Option Type Buttons
		americanVanilla = new JRadioButton("American Vanilla");
		europeanVanilla = new JRadioButton("European Vanilla");
		asian = new JRadioButton("Asian");
		asian.setEnabled(false);
		
		ButtonGroup optionTypeGroup = new ButtonGroup();
		optionTypeGroup.add(americanVanilla);
		optionTypeGroup.add(europeanVanilla);
		optionTypeGroup.add(asian);
		
		JPanel optionTypePanel = new JPanel();
		Border optionTypeBorder = BorderFactory.createTitledBorder("Option Type");
		optionTypePanel.setBorder(optionTypeBorder);
		americanVanilla.setSelected(true);
		mainPanel.add(optionTypePanel);
		
		optionTypePanel.add(americanVanilla);
		optionTypePanel.add(europeanVanilla);
		optionTypePanel.add(asian);
		
		americanVanilla.addItemListener(this);
		europeanVanilla.addItemListener(this);
		asian.addItemListener(this);
		
		this.add(mainPanel);
		this.setVisible(true);
		
		//Payoff Type Buttons
		call = new JRadioButton("Call");
		put = new JRadioButton("Put");
		weird = new JRadioButton("weird (spot^2)");
		
		ButtonGroup payoffTypeGroup = new ButtonGroup();
		payoffTypeGroup.add(call);
		payoffTypeGroup.add(put);
		payoffTypeGroup.add(weird);
		
		JPanel payoffTypePanel = new JPanel();
		Border payoffTypeBorder = BorderFactory.createTitledBorder("Payoff Type");
		payoffTypePanel.setBorder(payoffTypeBorder);
		call.setSelected(true);
		mainPanel.add(payoffTypePanel);
		
		payoffTypePanel.add(call);
		payoffTypePanel.add(put);
		payoffTypePanel.add(weird);
		
		this.add(mainPanel);
		this.setVisible(true);

		//Pricer Type Buttons
		binomial = new JRadioButton("Binomial");
		monteCarlo = new JRadioButton("Monte Carlo");
		
		ButtonGroup pricerTypeGroup = new ButtonGroup();
		pricerTypeGroup.add(binomial);
		pricerTypeGroup.add(monteCarlo);
		
		JPanel pricerTypePanel = new JPanel();
		Border pricerTypeBorder = BorderFactory.createTitledBorder("Pricing Method");
		pricerTypePanel.setBorder(pricerTypeBorder);
		binomial.setSelected(true);
		mainPanel.add(pricerTypePanel);
		
		pricerTypePanel.add(binomial);
		pricerTypePanel.add(monteCarlo);
		
		binomial.addItemListener(this);
		monteCarlo.addItemListener(this);

		//MCarlo pricer check boxes
		JPanel mCarloTypePanel = new JPanel();
		Border mCarloTypeBorder = BorderFactory.createTitledBorder("Monte Carlo Enhancements");
		mCarloTypePanel.setBorder(mCarloTypeBorder);
		mainPanel.add(mCarloTypePanel);
		
		
		antithetic = new JCheckBox ("Antithetic");
		stratified = new JCheckBox ("Stratified");
		controlVariate = new JCheckBox ("Control Variate");
		
		
		mCarloTypePanel.add(antithetic);
		mCarloTypePanel.add(stratified);
		mCarloTypePanel.add(controlVariate);
		
		
		antithetic.setEnabled(false);
		stratified.setEnabled(false);
		controlVariate.setEnabled(false);
		
		//Option setup info
		
		JPanel optionInfoPanel = new JPanel();
		Border optionInfoBorder = BorderFactory.createTitledBorder("Option Info");
		optionInfoPanel.setBorder(optionInfoBorder);
		mainPanel.add(optionInfoPanel);
		
		strike = new JTextField(5);
		expiry = new JTextField(5);
		steps = new JTextField(5);
		paths = new JTextField(5);
		strikelab = new JLabel("Strike");
		expirylab = new JLabel("Expiry");
		stepslab = new JLabel("Steps");
		pathslab = new JLabel("Paths");
		
		optionInfoPanel.add(strikelab);
		optionInfoPanel.add(strike);
		optionInfoPanel.add(expirylab);
		optionInfoPanel.add(expiry);
		optionInfoPanel.add(stepslab);
		optionInfoPanel.add(steps);
		optionInfoPanel.add(pathslab);
		optionInfoPanel.add(paths);
		
		paths.setEnabled(false);
		paths.setText("1");
		
		//Market data web pull check boxes
		JPanel dataScrapePanel = new JPanel();
		Border dataScrapeBorder = BorderFactory.createTitledBorder("Web Data Scrape");
		dataScrapePanel.setBorder(dataScrapeBorder);
		mainPanel.add(dataScrapePanel);
		
		webInterestRate = new JCheckBox("Interest Rate");
		webSpot = new JCheckBox ("Spot Price");
		webVolatility = new JCheckBox ("Volatility");
		webDividend = new JCheckBox ("Dividend Yeild");

		dataScrapePanel.add(webSpot);		
		dataScrapePanel.add(webInterestRate);
		dataScrapePanel.add(webVolatility);
		dataScrapePanel.add(webDividend);
		
		webSpot.addItemListener(this);
		webInterestRate.addItemListener(this);
		webVolatility.addItemListener(this);
		webDividend.addItemListener(this);
		
		//Manual Data setup info
		
		JPanel dataPanel = new JPanel();
		Border dataBorder = BorderFactory.createTitledBorder("Manual Market Data");
		dataPanel.setBorder(dataBorder);
		mainPanel.add(dataPanel);
		
		spot = new JTextField(5);
		interestRate = new JTextField(5);
		volatility = new JTextField(5);
		dividend = new JTextField(5);
		spotlab = new JLabel("Spot");
		interestRatelab = new JLabel("Interest Rate");
		volatilitylab = new JLabel("Volatility");
		dividendlab = new JLabel("Dividend");
		
		dataPanel.add(spotlab);
		dataPanel.add(spot);
		dataPanel.add(interestRatelab);
		dataPanel.add(interestRate);
		dataPanel.add(volatilitylab);
		dataPanel.add(volatility);
		dataPanel.add(dividendlab);
		dataPanel.add(dividend);
			
		//Price output Panel
		
		JPanel pricePanel = new JPanel();
	
		Border priceBorder = BorderFactory.createTitledBorder("Price");		
		pricePanel.setBorder(priceBorder);
		mainPanel.add(pricePanel);
		
		calc = new JButton("Calculate");
		mainPanel.add(calc);
		
		ButtonListener buttlstnr = new ButtonListener();
		calc.addActionListener(buttlstnr);		

		price = new JLabel("Unknown");
		pricePanel.add(price);
		

		
		this.add(mainPanel);
		this.setVisible(true);
	}		

	// item listener actions
	@Override
	public void itemStateChanged(ItemEvent e) {
			
		if(e.getSource().equals(americanVanilla) && americanVanilla.isSelected()){
			binomial.setEnabled(true);
			monteCarlo.setEnabled(true);
			if (monteCarlo.isSelected()){
				paths.setEnabled(true);
			
				antithetic.setEnabled(true);
				stratified.setEnabled(true);
				controlVariate.setEnabled(true);
			}else{
				paths.setText("1");
				paths.setEnabled(false);
			
				antithetic.setEnabled(false);
				stratified.setEnabled(false);
				controlVariate.setEnabled(false);
			}
		} else if (e.getSource().equals(europeanVanilla) && europeanVanilla.isSelected()){
			binomial.setEnabled(true);
			monteCarlo.setEnabled(true);

			antithetic.setEnabled(false);
			stratified.setEnabled(false);
			controlVariate.setEnabled(false);
			if (monteCarlo.isSelected()){
				paths.setEnabled(true);

				antithetic.setEnabled(true);
				stratified.setEnabled(true);
				controlVariate.setEnabled(true);
			}else{
				paths.setText("1");
				paths.setEnabled(false);
		
				antithetic.setEnabled(false);
				stratified.setEnabled(false);
				controlVariate.setEnabled(false);
			}
		} else if (e.getSource().equals(asian) && asian.isSelected()){
			binomial.setEnabled(false);
			monteCarlo.setSelected(true);
			monteCarlo.setEnabled(true);
	
			antithetic.setEnabled(true);
			stratified.setEnabled(true);
			controlVariate.setEnabled(true);
			paths.setEnabled(true);
		} else if (e.getSource().equals(binomial) && binomial.isSelected()){
		
			antithetic.setEnabled(false);
			stratified.setEnabled(false);
			controlVariate.setEnabled(false);

			antithetic.setSelected(false);
			stratified.setSelected(false);
			controlVariate.setSelected(false);
			paths.setEnabled(false);
			paths.setText("1");
		} else if (e.getSource().equals(monteCarlo) && monteCarlo.isSelected()){

			antithetic.setEnabled(true);
			stratified.setEnabled(true);
			controlVariate.setEnabled(true);
			paths.setEnabled(true);
		} else if (e.getSource().equals(webSpot)){
			if(webSpot.isSelected()){
				spot.setEnabled(false);
			} else {
				spot.setEnabled(true);
			}
		} else if (e.getSource().equals(webInterestRate)){
			if(webInterestRate.isSelected()){
				interestRate.setEnabled(false);
			} else {
				interestRate.setEnabled(true);
			}
		} else if (e.getSource().equals(webVolatility)){
			if(webVolatility.isSelected()){
				volatility.setEnabled(false);
			} else {
				volatility.setEnabled(true);
			}
		} else if (e.getSource().equals(webDividend)){
			if(webDividend.isSelected()){
				dividend.setEnabled(false);
			} else {
				dividend.setEnabled(true);
			}
			
		}
	}

	public class ButtonListener implements ActionListener {
		double strikeVal;
		double expiryVal;
		int stepsVal;
		double spotVal;
		double interestRateVal;
		double volatilityVal;
		double dividendVal;
		double priceVal;
		int pathsVal;

		
		
		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource().equals(calc)) {

				
				
				
				try{
					strikeVal = Double.parseDouble(strike.getText());
					expiryVal = Double.parseDouble(expiry.getText());
					stepsVal = Integer.parseInt(steps.getText());
					spotVal = Double.parseDouble(spot.getText());
					interestRateVal = Double.parseDouble(interestRate.getText());
					volatilityVal = Double.parseDouble(volatility.getText());
					dividendVal = Double.parseDouble(dividend.getText());
					pathsVal = Integer.parseInt(paths.getText());
					// TODO : Make these actual exceptions			
					if(strikeVal < 0){
						JOptionPane.showMessageDialog(MainFrame.this, "Strike must be a number greater than or equal to 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					if(expiryVal <= 0){
						JOptionPane.showMessageDialog(MainFrame.this, "Expiry must be a number greater than 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					if(stepsVal <= 0){
						JOptionPane.showMessageDialog(MainFrame.this, "Steps must be an Interger greater than 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					if(spotVal < 0){
						JOptionPane.showMessageDialog(MainFrame.this, "Spot must be a greater than or equal to 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					if(volatilityVal <= 0){
						JOptionPane.showMessageDialog(MainFrame.this, "Volatility must be greater than 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					if(dividendVal < 0){
						JOptionPane.showMessageDialog(MainFrame.this, "Dividend Yield must be greater than or equal to 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					if(pathsVal <= 0){
						JOptionPane.showMessageDialog(MainFrame.this, "Dividend Yield must be greater 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}					
					// ----------------
					
					int opType  = 8;
					if (americanVanilla.isSelected()) {opType = 0;}
					if (europeanVanilla.isSelected()) {opType = 1;}
					if (asian.isSelected()) {opType = 2;}
					
					int payoffType = 8;
					if (call.isSelected()) {payoffType = 0;}
					if (put.isSelected()) {payoffType = 1;}
					if (weird.isSelected()) {payoffType = 2;}
					
					int pricertype = 8;
					if (americanVanilla.isSelected() && binomial.isSelected()) {pricertype = 0;}
					if (americanVanilla.isSelected() && monteCarlo.isSelected()) {pricertype = 1;}
					if (europeanVanilla.isSelected() && binomial.isSelected()) {pricertype = 2;}
					if (europeanVanilla.isSelected() && monteCarlo.isSelected()) {pricertype = 3;}
					if (asian.isSelected()) {pricertype = 4;}
					
					
					
					priceVal = GUIClient.client(stepsVal, pathsVal, spotVal, interestRateVal, volatilityVal, dividendVal, strikeVal, expiryVal,opType,payoffType,pricertype,antithetic.isSelected(),stratified.isSelected(),controlVariate.isSelected());
					
					price.setText(Double.toString(priceVal));
				}
				catch(NumberFormatException excep){
					JOptionPane.showMessageDialog(MainFrame.this, "Please Enter the Correct Info\n\nCannot leave activefields blank.\nOnly number can be entered.\nSteps and Paths fields must be integers. ", "ERROR", JOptionPane.ERROR_MESSAGE);
					
				}
			}
			
		}
		
	}
	
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
