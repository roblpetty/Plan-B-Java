
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.*;

public class MainFrame extends JFrame implements ItemListener{
	JRadioButton american,european, asian, vanilla ;
	JRadioButton call,put,weird;
	JRadioButton binomial, monteCarlo;
	JCheckBox stratified,antithetic, controlVariate;
	JCheckBox webSpot, webInterestRate, webVolatility, webDividend;
	JTextField strike, expiry, steps, paths, threads;
	JTextField spot, interestRate, volatility, dividend;
	JLabel strikelab, expirylab, stepslab, pathslab, stepslab2, threadslab, threadslab2;
	JLabel spotlab, interestRatelab, volatilitylab, dividendlab;
	JButton calc;
	JLabel price;

	
	MainFrame() {
		
		this.setSize(510, 450);
		this.setLocationRelativeTo(null);
		this.setTitle("Options Pricer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon("resources\\aamoney.png");
		this.setIconImage(icon.getImage());
		JPanel mainPanel = new JPanel();
		
		//Option Type Buttons
		vanilla = new JRadioButton("Vanilla");
		asian = new JRadioButton("Asian");
		
		ButtonGroup optionTypeGroup = new ButtonGroup();
		optionTypeGroup.add(vanilla);
		optionTypeGroup.add(asian);
		
		JPanel optionTypePanel = new JPanel();
		Border optionTypeBorder = BorderFactory.createTitledBorder("Option Type");
		optionTypePanel.setBorder(optionTypeBorder);
		vanilla.setSelected(true);
		mainPanel.add(optionTypePanel);
		
		optionTypePanel.add(vanilla);
		optionTypePanel.add(asian);
		
		vanilla.addItemListener(this);
		asian.addItemListener(this);
		
		this.add(mainPanel);
		
		
		//Exercise Type Buttons
		american = new JRadioButton("American");
		european= new JRadioButton("European");
		
		ButtonGroup exerciseTypeGroup = new ButtonGroup();
		exerciseTypeGroup.add(american);
		exerciseTypeGroup.add(european);
		
		JPanel exerciseTypePanel = new JPanel();
		Border exerciseTypeBorder = BorderFactory.createTitledBorder("Exercise Type");
		exerciseTypePanel.setBorder(exerciseTypeBorder);
		american.setSelected(true);
		mainPanel.add(exerciseTypePanel);
		
		exerciseTypePanel.add(american);
		exerciseTypePanel.add(european);
		
		american.addItemListener(this);
		european.addItemListener(this);
		
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
		controlVariate.setVisible(false);
		
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
		strike.setText("40");
		expiry = new JTextField(5);
		expiry.setText("1");
		steps = new JTextField(5);
		steps.setText("8");
		paths = new JTextField(5);
		paths.setText("1000");
		threads = new JTextField(5);
		threads.setText("0");
		strikelab = new JLabel("Strike");
		expirylab = new JLabel("Expiry");
		stepslab = new JLabel("Steps: 2^");
		pathslab = new JLabel("Paths");
		stepslab2 = new JLabel();
		threadslab = new JLabel("Threads: 2^");
		threadslab2 = new JLabel("=1");
		stepslab2.setText("="+(int)Math.pow(2,Integer.parseInt(steps.getText())));
		
		steps.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent e) {
				
				try{
					stepslab2.setText("="+(int)Math.pow(2,Integer.parseInt(steps.getText())));	
				}catch(NumberFormatException excep){
					stepslab2.setText("=0");	
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try{
					stepslab2.setText("="+(int)Math.pow(2,Integer.parseInt(steps.getText())));	
				}catch(NumberFormatException excep){
					stepslab2.setText("=0");	
				}			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try{
					stepslab2.setText("="+(int)Math.pow(2,Integer.parseInt(steps.getText())));	
				}catch(NumberFormatException excep){
					stepslab2.setText("=0");	
				}				
			}
		});
		
		threads.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent e) {
				
				try{
					threadslab2.setText("="+(int)Math.pow(2,Integer.parseInt(threads.getText())));	
				}catch(NumberFormatException excep){
					threadslab2.setText("^2=0");	
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try{
					threadslab2.setText("="+(int)Math.pow(2,Integer.parseInt(threads.getText())));	
				}catch(NumberFormatException excep){
					threadslab2.setText("=0");	
				}			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try{
					threadslab2.setText("="+(int)Math.pow(2,Integer.parseInt(threads.getText())));	
				}catch(NumberFormatException excep){
					threadslab2.setText("=0");	
				}				}
			
		});
		JPanel optionInfoSubPanel = new JPanel();
		
		optionInfoPanel.add(strikelab);
		optionInfoPanel.add(strike);
		optionInfoPanel.add(expirylab);
		optionInfoPanel.add(expiry);
		optionInfoPanel.add(stepslab);
		optionInfoPanel.add(steps);
		optionInfoPanel.add(stepslab2);
		optionInfoSubPanel.add(pathslab);
		optionInfoSubPanel.add(paths);	
		optionInfoSubPanel.add(threadslab);
		optionInfoSubPanel.add(threads);
		optionInfoSubPanel.add(threadslab2);		
		mainPanel.add(optionInfoSubPanel);
		
		paths.setEnabled(false);
		threads.setEnabled(false);

		
		//Market data web pull check boxes
		JPanel dataScrapePanel = new JPanel();
		Border dataScrapeBorder = BorderFactory.createTitledBorder("Web Data Scrape");
		dataScrapePanel.setBorder(dataScrapeBorder);
		//mainPanel.add(dataScrapePanel);
		
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
		spot.setText("41");
		interestRate = new JTextField(5);
		interestRate.setText(".08");
		volatility = new JTextField(5);
		volatility.setText(".3");
		dividend = new JTextField(5);
		dividend.setText("0");
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
		if(e.getSource().equals(vanilla) && vanilla.isSelected()){
			binomial.setEnabled(true);
			controlVariate.setEnabled(true);
		} else if(e.getSource().equals(asian) && asian.isSelected()){
			binomial.setEnabled(false);
			controlVariate.setEnabled(false);
			controlVariate.setSelected(false);
			monteCarlo.setSelected(true);
		} else if(e.getSource().equals(american) && american.isSelected()){
			if(!asian.isSelected()){
				binomial.setEnabled(true);	
			}
			monteCarlo.setEnabled(true);
			controlVariate.setEnabled(false);
			controlVariate.setSelected(false);
			if (monteCarlo.isSelected()){
				paths.setEnabled(true);
				threads.setEnabled(true);
				antithetic.setEnabled(true);
				stratified.setEnabled(true);
		
			}else{
				paths.setText("1");
				paths.setEnabled(false);
				threads.setText("0");
				threads.setEnabled(false);
				antithetic.setEnabled(false);
				stratified.setEnabled(false);
				controlVariate.setEnabled(false);
				controlVariate.setSelected(false);
			}
		} else if (e.getSource().equals(european) && european.isSelected()){
			if(!asian.isSelected()){
				binomial.setEnabled(true);
			}
			monteCarlo.setEnabled(true);
			antithetic.setEnabled(false);
			stratified.setEnabled(false);
			controlVariate.setEnabled(false);
			controlVariate.setSelected(false);
			if (monteCarlo.isSelected()){
				paths.setEnabled(true);
				threads.setEnabled(true);
				antithetic.setEnabled(true);
				stratified.setEnabled(true);
				if(vanilla.isSelected()){controlVariate.setEnabled(true);}
			}else{
				paths.setText("1");
				paths.setEnabled(false);
				threads.setText("0");
				threads.setEnabled(false);
				antithetic.setEnabled(false);
				stratified.setEnabled(false);
				controlVariate.setEnabled(false);
				controlVariate.setSelected(false);
			}
		
		} else if (e.getSource().equals(binomial) && binomial.isSelected()){
		
			antithetic.setEnabled(false);
			stratified.setEnabled(false);
			controlVariate.setEnabled(false);
			controlVariate.setSelected(false);
			antithetic.setSelected(false);
			stratified.setSelected(false);
			paths.setEnabled(false);
			paths.setText("1");
			threads.setEnabled(false);
			threads.setText("0");
		} else if (e.getSource().equals(monteCarlo) && monteCarlo.isSelected()){
			antithetic.setEnabled(true);
			stratified.setEnabled(true);
			paths.setEnabled(true);
			threads.setEnabled(true);
			if(american.isSelected()||asian.isSelected()){
				controlVariate.setEnabled(false);
				controlVariate.setSelected(false);
			}else{controlVariate.setEnabled(true);}
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
		int threadsVal;

		
		
		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource().equals(calc)) {

				
				
				
				try{
					strikeVal = Double.parseDouble(strike.getText());
					expiryVal = Double.parseDouble(expiry.getText());
					stepsVal = (int) Math.pow(2,Integer.parseInt(steps.getText()));
					spotVal = Double.parseDouble(spot.getText());
					interestRateVal = Double.parseDouble(interestRate.getText());
					volatilityVal = Double.parseDouble(volatility.getText());
					dividendVal = Double.parseDouble(dividend.getText());
					pathsVal = Integer.parseInt(paths.getText());
					threadsVal = (int) Math.pow(2,Integer.parseInt(threads.getText()));
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
						JOptionPane.showMessageDialog(MainFrame.this, "paths must be greater than 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					if(threadsVal <= 0){
						JOptionPane.showMessageDialog(MainFrame.this, "Threads must be greater at least 1.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					// ----------------
					
					int exerciseType  = 8;
					if (american.isSelected()) {exerciseType = 0;}
					if (european.isSelected()) {exerciseType = 1;}
					
					int payoffType = 8;
					if (call.isSelected()) {payoffType = 0;}
					if (put.isSelected()) {payoffType = 1;}
					if (weird.isSelected()) {payoffType = 2;}
					
					int pricertype = 8;
					if (american.isSelected() && binomial.isSelected()) {pricertype = 0;}
					if (european.isSelected() && binomial.isSelected()) {pricertype = 1;}
					if (american.isSelected() && monteCarlo.isSelected()) {pricertype = 2;}
					if (european.isSelected() && monteCarlo.isSelected()) {pricertype = 3;}
					if (american.isSelected() && asian.isSelected()) {pricertype = 4;}
					if (european.isSelected() && asian.isSelected()) {pricertype = 5;}
					
					int MCType = 0;
					if (antithetic.isSelected() && !stratified.isSelected() && !controlVariate.isSelected()){MCType = 1;}
					if (!antithetic.isSelected() && stratified.isSelected() && !controlVariate.isSelected()){MCType = 2;}
					if (!antithetic.isSelected() && !stratified.isSelected() && controlVariate.isSelected()){MCType = 3;}
					if (antithetic.isSelected() && stratified.isSelected() && !controlVariate.isSelected()){MCType = 4;}
					if (antithetic.isSelected() && !stratified.isSelected() && controlVariate.isSelected()){MCType = 5;}
					if (!antithetic.isSelected() && stratified.isSelected() && controlVariate.isSelected()){MCType = 6;}
					if (antithetic.isSelected() && !stratified.isSelected() && !controlVariate.isSelected()){MCType = 7;}
					if (asian.isSelected() && !antithetic.isSelected() && !stratified.isSelected()){MCType = 8;}
					if (asian.isSelected() && antithetic.isSelected() && !stratified.isSelected()){MCType = 9;}
					if (asian.isSelected() && !antithetic.isSelected() && stratified.isSelected()){MCType = 10;}
					if (asian.isSelected() && antithetic.isSelected() && stratified.isSelected()){MCType = 11;}
					
					System.out.println("exercise type: "+exerciseType);
					System.out.println("payoff type: "+payoffType);
					System.out.println("pricer type: "+pricertype);
					System.out.println("MCType: "+MCType);
					
					priceVal = GUIClient.client(stepsVal, pathsVal, spotVal, interestRateVal, volatilityVal, dividendVal, strikeVal, expiryVal, threadsVal, exerciseType,payoffType,pricertype, MCType);
					
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
