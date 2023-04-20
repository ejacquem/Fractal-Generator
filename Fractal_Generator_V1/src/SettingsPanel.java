
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SettingsPanel extends JPanel implements ActionListener, ChangeListener, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Koch
	JButton buttonMirror;
	JButton buttonTrunk;
	JButton buttonLeaves;
	JButton buttonTrunkA;
	JButton buttonLeavesA;
	JButton buttonAddBranch;
	JButton buttonRemoveBranch;
	
	JButton buttonRandomKoch;
	JButton buttonRandomTree;
	JButton buttonRandomTreeA;
	
	JSlider sliderIteration;
	JSlider sliderAngle;
	JSlider sliderSide;
	JSlider sliderSegment;
	JSlider sliderSkewAngle;
	
	JSlider sliderIterationTree;
	JSlider sliderAngleTree;
	JSlider sliderBranch;
	JSlider sliderLength;
	
	JSlider sliderIterationTreeA;
	JPanel branchesPanel;
	
	JSlider sliderIterationLSystem;
	JSlider sliderAngleLsystem;
	JTextArea LSystemRules;
	JTextField LSystemStart;

	JTabbedPane tabbedPane;
	JTabbedPane tabTree;
	
	JLabel labelLineCount = new JLabel("number of lines: 3");

	JScrollPane scrollPane;
	
	public Program frame;
	
	public Fractal f;
	
	public Branch[] branches;
	public Branch lastModifiedBranch;
	
	int fractalIteration;
	String currentTab = "00";
	int numberOfBranch = 1;
	public final int MAX_BRANCH = 10;
	
	public boolean branchHaveChanged;
	public boolean zoomed;
	public boolean settingsHaveChanged;
	public boolean lockSkew;
	public boolean dontUpdate;
	public boolean showAxis;
	public boolean generating;
	
	SettingsPanel() {
		super(new BorderLayout(2, 1));
		
		makeSlider();
		makeButton();
		
//		String Koch 	= "       Koch       ";
//		String Tree	 	= "       Tree       ";
//		String L-System = "     L-System     ";
		
        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(this);
         
        JComponent panelKoch = makePanelKoch();
        tabbedPane.addTab("      Koch      ", null, panelKoch);
        
        tabTree = new JTabbedPane();
        tabTree.addChangeListener(this);
        tabbedPane.addTab("      Tree      ", null, tabTree);
//        tabTree.addChangeListener(this);
	        
	        JComponent panelTreeSimple = makePanelTreeSimple();
	        tabTree.addTab("    Simple    ", null, panelTreeSimple);
	        
	        JComponent panelTreeAdvanced = makePanelTreeAdvanced();
//	        panelTreeAdvanced.setLayout(new GridLayout(3, 1));
	        tabTree.addTab("   Advanced   ", null, panelTreeAdvanced);
        
        JComponent panelOther = makePanelOther();
        tabbedPane.addTab("    L-System    ", null, panelOther);
		
//		setBounds(Program.DPANEL_SIZE, 0, width, height);
		setBackground(Color.GRAY);
//		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		

		add(tabbedPane);
//		labelLineCount.setBorder(BorderFactory.createLineBorder(Color.black));
		labelLineCount.setForeground(Color.white);
		labelLineCount.setFont(new Font(null,Font.PLAIN,20));;
		add(labelLineCount, BorderLayout.PAGE_END);
		
	}
	
	private JComponent makePanelKoch() {
		JPanel p = new JPanel();
//		p.setLayout(new GridLayout(8, 1));
		
		p.add(sliderIteration);
		p.add(sliderAngle);
		p.add(sliderSkewAngle);
		p.add(sliderSide);
		p.add(sliderSegment);
		
		p.add(buttonMirror);
		p.add(buttonRandomKoch);
		
		return p;
	}
	
	private JComponent makePanelTreeSimple() {
		JPanel p = new JPanel();
		
		p.add(sliderIterationTree);
		p.add(sliderAngleTree);
		p.add(sliderBranch);
		p.add(sliderLength);
		
		p.add(buttonLeaves);
		p.add(buttonTrunk);
		p.add(buttonRandomTree);
		
		return p;
	}
	
	private JComponent makePanelTreeAdvanced() {
		JPanel p = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		branchesPanel = new JPanel();
		branchesPanel.setBorder(BorderFactory.createTitledBorder("Branches"));
		
		scrollPane = new JScrollPane(branchesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
				
		makeBranches();	

		buttonPanel.add(sliderIterationTreeA);
		buttonPanel.add(buttonAddBranch);
		buttonPanel.add(buttonRemoveBranch);
		buttonPanel.add(buttonLeavesA);
		buttonPanel.add(buttonTrunkA);
		buttonPanel.add(buttonRandomTreeA);
		
		p.setLayout(new BorderLayout());
		buttonPanel.setPreferredSize(new Dimension(0,200));
		
		p.add(buttonPanel,BorderLayout.NORTH);
		p.add(scrollPane,BorderLayout.CENTER);
		
		return p;
	}

	private JComponent makePanelOther() {
		JPanel p = new JPanel();
		
		LSystemRules = new JTextArea();
		LSystemRules.addKeyListener(this);
		LSystemRules.setLineWrap(true);
		LSystemRules.setColumns(30);
		LSystemRules.setRows(6);
		LSystemRules.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		
		LSystemStart = new JTextField();
		LSystemStart.addKeyListener(this);
		LSystemStart.setPreferredSize(new Dimension(280,50));
		LSystemStart.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		
		LSystemRules.setBorder(BorderFactory.createTitledBorder("Rules"));
		LSystemStart.setBorder(BorderFactory.createTitledBorder("Start"));
		
		p.add(LSystemRules);
		p.add(LSystemStart);
		p.add(sliderIterationLSystem);
		p.add(sliderAngleLsystem);
		
		return p;
	}
	
	private void makeButton() {
		buttonMirror = Button("");
		buttonTrunk = Button("");
		buttonLeaves = Button("");
		buttonTrunkA = Button("");
		buttonLeavesA = Button("");
		buttonAddBranch = Button("add branch");
		buttonRemoveBranch = Button("remove branch");
		buttonRandomKoch = Button("Random");
		buttonRandomTree = Button("Random");
		buttonRandomTreeA= Button("Random");
	}
	
	private JButton Button(String name){
		JButton button = new JButton();
		button.setText(name);
		button.addActionListener(this);
		return button;
	}
	
	private void makeSlider() {
		sliderIteration = Slider(new JSlider(1,16,5),2);
		sliderIteration.setToolTipText("Set the number of iterations");
		

		sliderAngle = Slider(new JSlider(0,360,60),180);
		
		sliderSkewAngle = Slider(new JSlider(0,720,0),360);
		
		sliderSide = Slider(new JSlider(1,10,3),9);
		
		sliderSegment = Slider(new JSlider(2,20,2),18);
		
		sliderIterationTree = Slider(new JSlider(0,20,2),2);
		
		sliderAngleTree = Slider(new JSlider(0,360,60),180);
		sliderAngleTree.setMinorTickSpacing(10);
		
		sliderBranch = Slider(new JSlider(1,10,2),9);
		
		sliderLength = Slider(new JSlider(0,1000,500),500);
		sliderLength.setMinorTickSpacing(100);
		sliderLength.setPaintLabels(false);
		
		sliderIterationTreeA = Slider(new JSlider(1,15,2),14);
		
		sliderIterationLSystem = Slider(new JSlider(1,15,5),14);
		
		sliderAngleLsystem = Slider(new JSlider(0,180,90),90);
		sliderAngleLsystem.setMinorTickSpacing(10);
	}
	
	
	private JSlider Slider(JSlider slider, int majorTick) {
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(1);
		slider.addChangeListener(this);
		slider.setPreferredSize(new Dimension(220,80));
		slider.setMajorTickSpacing(majorTick);
		
		return slider;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(f == null || dontUpdate || generating) return;
		if(e.getSource() == buttonMirror) {
			f.mirror = !f.mirror;
			buttonMirror.setText("Mirror : " + (f.mirror ? "Yes" : "No"));
			f.generateFractal("Koch");
			System.out.println("koch");
		}
		if(e.getSource() == buttonTrunk) {
			f.trunk = !f.trunk;
			buttonTrunk.setText("Trunk : " + (f.trunk ? "Yes" : "No"));
			f.generateFractal("Tree");
		}
		if(e.getSource() == buttonLeaves) {
			f.leaves = !f.leaves;
			buttonLeaves.setText("Only Leaves : " + (f.leaves ? "Yes" : "No"));
			f.generateFractal("Tree");
		}
		if(e.getSource() == buttonTrunkA) {
			f.trunkA = !f.trunkA;
			buttonTrunkA.setText("Trunk : " + (f.trunkA ? "Yes" : "No"));
			f.generateFractal("TreeAdvanced");
		}
		if(e.getSource() == buttonLeavesA) {
			f.leavesA = !f.leavesA;
			buttonLeavesA.setText("Only Leaves : " + (f.leavesA ? "Yes" : "No"));
			f.generateFractal("TreeAdvanced");
		}
		if(e.getSource() == buttonAddBranch) {
			addBranch();
			f.generateFractal("TreeAdvanced");
		}
		if(e.getSource() == buttonRemoveBranch) {
			removeBranch();
			f.generateFractal("TreeAdvanced");
		}
		if(e.getSource() == buttonRandomKoch) {
			dontUpdate = true;
			System.out.println("Random Koch");
			setValueNoUpdate(sliderSegment,	(int) (Math.random()*sliderSegment.getMaximum()+2));
			setMaxAngle();
			
			setValueNoUpdate(sliderIteration,(int) (Math.random()*3)+2);
			setValueNoUpdate(sliderAngle, 	(int) (Math.random()*sliderAngle.getMaximum()));
			setValueNoUpdate(sliderSkewAngle, 	(int) (Math.random()*sliderSkewAngle.getMaximum()));
			setValueNoUpdate(sliderSide, 	(int) (Math.random()*sliderSide.getMaximum()+1));
			dontUpdate = false;
			stateChanged(null);
		}
		if(e.getSource() == buttonRandomTree) {
			setValueNoUpdate(sliderBranch, (int) (Math.random()*sliderBranch.getMaximum()+1));
			setMaxIteration();
			
			if(sliderBranch.getValue() == 1) {
				setValueNoUpdate(sliderLength,(int) (Math.random()*50 + 950));		
				setValueNoUpdate(sliderIterationTree, (int) (Math.random()*100)+401);
			}else {
				setValueNoUpdate(sliderLength,(int) (Math.random()*sliderLength.getMaximum()+2));
				setValueNoUpdate(sliderIterationTree, (int) (Math.random()*4)+3);
			} 	
				
			setValueNoUpdate(sliderAngleTree, 	(int) (Math.random()*sliderAngleTree.getMaximum()));
			stateChanged(null);
		}
		if(e.getSource() == buttonRandomTreeA) {
			int i = (int) (Math.random()*9)+2;
			System.out.println(i);
			while(numberOfBranch != i) {
				if(numberOfBranch > i)removeBranch();
				else addBranch();
			}
			double maxN;
			if(numberOfBranch == 1)maxN = sliderIterationTreeA.getMaximum()+1;
			else maxN = Math.log(300_000)/Math.log(numberOfBranch) +1;
			setValueNoUpdate(sliderIterationTreeA, Math.max(4,(int) (Math.random()*maxN)));
			for(Branch b : branches) {
				if(b != null)b.setRandomValue();
			}
			branchHaveChanged = true;
			stateChanged(null);
		}
		updateTitle();
	}

	
	private void removeBranch() {
		if(numberOfBranch == 0) {
			System.out.println("Can't go below 0 branches");
			return;
		}
		branchesPanel.remove(branches[numberOfBranch-1]);
		branches[numberOfBranch-1] = null;
		numberOfBranch--;
		branchesPanel.setLayout(new GridLayout(numberOfBranch,1));
	}

	private void addBranch() {
		if(numberOfBranch == MAX_BRANCH) {
			System.out.println("Can't go above 10 branches");
			return;
		}
		branches[numberOfBranch] = new Branch(this,numberOfBranch+1);
		branchesPanel.add(branches[numberOfBranch]);
		numberOfBranch++;
		scrollPane.repaint();
		branchesPanel.setLayout(new GridLayout(numberOfBranch,1));
	}
	
	//add all branches in branches[] array to the panel
	public void addAllBranch(Branch[] b) {
		removeAllBranch();
		System.out.println("Adding all branch");
		while(numberOfBranch <b.length && b[numberOfBranch] != null && numberOfBranch < MAX_BRANCH) {
			branches[numberOfBranch] = b[numberOfBranch];
			branches[numberOfBranch].Spanel = this;
			branchesPanel.add(branches[numberOfBranch]);
			numberOfBranch++;
			System.out.println("Add branch" + numberOfBranch);
		}
		f.branches = branches;
		branchHaveChanged = true;
		scrollPane.repaint();
	}
	public void removeAllBranch() {
		for(int i = 0 ; i < MAX_BRANCH;i++) {removeBranch();}
		numberOfBranch = 0;
	}

	public Branch getLastModifiedBranch() {
		if(lastModifiedBranch != null && branches[lastModifiedBranch.branchNumber] != null)return lastModifiedBranch;
		else return branches[numberOfBranch-1];
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(f == null || dontUpdate || generating) return;
		generating = true;
		boolean stuffHaveChanged = tabHaveChanged() || zoomed || settingsHaveChanged;
		if(tabbedPane.getSelectedIndex() == 0 && (kochHaveChanged() || stuffHaveChanged)) {
			setMaxAngle();
			if(lockSkew && f.rotationAngle != sliderAngle.getValue())setAngle();
			f.skewAngle = sliderSkewAngle.getValue();
			f.setValue(sliderIteration.getValue(),sliderAngle.getValue(),sliderSide.getValue(),sliderSegment.getValue());
			f.generateFractal("Koch");
			System.out.println("Koch generated");
		}
		if(tabbedPane.getSelectedIndex() == 1) {
			if(tabTree.getSelectedIndex() == 0 && (treeSimpleHaveChanged() || stuffHaveChanged)) {
				setMaxIteration();
				f.setValueTree(sliderIterationTree.getValue(), sliderAngleTree.getValue(),sliderBranch.getValue(), sliderLength.getValue()/1000.);
				f.generateFractal("Tree");
				System.out.println("Tree Simple generated");
			}
			if(tabTree.getSelectedIndex() == 1 && (treeAdvancedHaveChanged() || stuffHaveChanged)) {
				f.setValueTreeA(sliderIterationTreeA.getValue());
				System.out.println(branches[0]);
				f.generateFractal("TreeAdvanced");
				System.out.println("Tree Advanced generated");
				branchHaveChanged = false;
			}
		}
		if(tabbedPane.getSelectedIndex() == 2 && (HilbertHaveChanged() || stuffHaveChanged)) {
			f.rotationAngle = sliderAngleLsystem.getValue();
			f.numberOfIteration = sliderIterationLSystem.getValue();
			f.generateFractal("LSystem");
		}
		if(tabHaveChanged()) {
			currentTab = ""+tabbedPane.getSelectedIndex() + tabTree.getSelectedIndex();
			System.out.println("tab index: " + currentTab);
		}
		zoomed = settingsHaveChanged = stuffHaveChanged = generating =false;
		updateTitle();
	}
	
	public String getcurrentab() {
		return currentTab;
	}
	
	private void setAngle() {
		int value = sliderSkewAngle.getValue() + (sliderAngle.getValue() - (int)f.rotationAngle)*2;
		if(value > sliderSkewAngle.getMaximum()) {
			value -= sliderSkewAngle.getMaximum();
		}
		else if(value < sliderSkewAngle.getMinimum()) {
			value += sliderSkewAngle.getMaximum();
		}
		setValueNoUpdate(sliderSkewAngle,value);
	}


	private void setMaxIteration() {
		dontUpdate = true;
		int max = sliderBranch.getValue() == 1 ? 1000:20;
		
		sliderIterationTree.setMaximum(max);
		sliderIterationTree.setLabelTable(null);
		sliderIterationTree.setMinorTickSpacing(max/20);
		sliderIterationTree.setMajorTickSpacing(max/4);
		dontUpdate = false;
	}


	private void setMaxAngle() {
		dontUpdate = true;
		int max = 360 * (sliderSegment.getValue()-1);
		
		sliderAngle.setMaximum(max);
		sliderAngle.setLabelTable(null);//setMajorTickSpacing doesn't work if label table is not reset
		sliderAngle.setMajorTickSpacing(max/3);
		sliderAngle.setMinorTickSpacing(max/6);
		
		sliderSkewAngle.setMaximum(max);
		sliderSkewAngle.setLabelTable(null);//setMajorTickSpacing doesn't work if label table is not reset
		sliderSkewAngle.setMajorTickSpacing(max/3);
		sliderSkewAngle.setMinorTickSpacing(max/6);
		
		dontUpdate = false;
	}


	public void updateTitle() {
//		label2.setText(String.format("Angle = %d�", sliderAngle.getValue()));
        
		setTitle(sliderIteration,"number of iteration : %d");
		setTitle(sliderAngle,"Angle : %d°");
		setTitle(sliderSkewAngle,"Skew Angle : %d°");
		setTitle(sliderSide,"number of sides : %d");
		setTitle(sliderSegment,"number of segments : %d");
		setTitle(sliderIterationTree,"number of iteration : %d");
		setTitle(sliderAngleTree,"Angle : %d°");
		setTitle(sliderBranch,"number of branch : %d");
		setTitl2(sliderLength,"length of branch : %.1f %%");
		setTitle(sliderIterationTreeA,"number of iteration : %d");
		setTitle(sliderIterationLSystem,"number of iteration : %d");
		setTitle(sliderAngleLsystem,"Angle : %d°");
      
		updateLineCount();
		frame.update();
	}
	
	public void setTitle(JSlider slider, String title) {
		TitledBorder titled = BorderFactory.createTitledBorder(String.format(title, slider.getValue()));
        slider.setBorder(titled);
	}
	public void setTitl2(JSlider slider, String title) {
		TitledBorder titled = BorderFactory.createTitledBorder(String.format(title, 0.1*slider.getValue()));
		slider.setBorder(titled);
	}
	
	/*
	 *This function is to limit the updates on the fractal and prevent unnecessary call of the generateFractal methods
	 */
	public boolean tabHaveChanged() {
		return 	   tabbedPane.getSelectedIndex()!= (currentTab.charAt(0)-'0')
				|| tabTree.getSelectedIndex() 	!= (currentTab.charAt(1)-'0')
				;
	}
	
	private boolean kochHaveChanged() {
		return     f.getAngle() 			!= sliderAngle.getValue() 
				|| f.getNumberOfIteration() != sliderIteration.getValue() 
				|| f.getNumberOfSegment() 	!= sliderSegment.getValue() 
				|| f.getNumberOfSide()		!= sliderSide.getValue()
				|| f.skewAngle		!= sliderSkewAngle.getValue()
				;
	}
	private boolean treeSimpleHaveChanged() {
		return 	   f.getNumberOfIteration() != sliderIterationTree.getValue() 
				|| f.getAngle() 			!= sliderAngleTree.getValue() 
				|| f.getNumberOfBranch() 	!= sliderBranch.getValue() 
				|| (int)(f.getLengthOfBranch()*1000) 	!= sliderLength.getValue() ;
	}
	private boolean treeAdvancedHaveChanged() {
		return f.getNumberOfIteration() != sliderIterationTreeA.getValue() 
				|| branchHaveChanged;
	}
	private boolean HilbertHaveChanged() {
		return true;
	}
	
	public void updateLineCount() {
		Color color = Color.white;
		if(f.getNumberOfLine() >= 100_000) color = Color.yellow;
		if(f.getNumberOfLine() >= 500_000)	color = Color.orange;
		if(f.getNumberOfLine() >= 1_000_000) color = Color.red;
		
		labelLineCount.setForeground(color);
		labelLineCount.setText(String.format(" number of lines: %,d", f.getNumberOfLine()));
	}
	
	private void makeBranches() {
		branches = new Branch[MAX_BRANCH];
		branches[0] = new Branch(this,1);
		branchesPanel.add(branches[0]);
	}
	
	public void sendStateChangeUpdate() {
		settingsHaveChanged = true;
		stateChanged(null);
	}
	
	public void setValueNoUpdate(JSlider s,int value) {
		dontUpdate = true;
		s.setValue(value);
		dontUpdate = false;
	}
	public void setValueKoch(PresetData preset) {
		setValueKoch(preset.n,preset.angle,preset.skew,preset.side,preset.segment,preset.mirror);
	}
	public void setValueTreeSimple(PresetData preset) {
		setValueTreeSimple(preset.n,preset.angle,preset.branch,preset.length,preset.leaves,preset.trunk);
	}
	public void setValueTreeAdvanced(PresetData preset) {
		setValueTreeAdvanced(preset.n,preset.leaves,preset.trunk,preset.branchesData);
	}
	public void setValueLSystem(PresetData preset) {
		setValueLSystem(preset.n,preset.angle,preset.rules,preset.startRule);
	}
	
	public void setValueKoch(int n, int angle , int skew, int side ,int segment , boolean mirror) {
		tabbedPane.setSelectedIndex(0);
		
		//no update to prevent multiple fractal generation
		setValueNoUpdate(sliderSegment, segment); 
		setMaxAngle();
		setValueNoUpdate(sliderIteration, n); 
		setValueNoUpdate(sliderAngle, angle); 
		setValueNoUpdate(sliderSkewAngle, skew); 
		setValueNoUpdate(sliderSide, side); 
		
		//button.settext doesn't send update
		f.mirror = mirror;
		buttonMirror.setText("Mirror : " + (mirror ? "Yes" : "No"));
	}
	
	public void setValueTreeSimple(int n, int angle , int branch ,int length , boolean leaves,boolean trunk) {
		tabbedPane.setSelectedIndex(1);
		tabTree.setSelectedIndex(0);
		
		//no update to prevent multiple fractal generation
		setValueNoUpdate(sliderBranch, branch); 
		setMaxIteration();
		setValueNoUpdate(sliderIterationTree, n); 
		setValueNoUpdate(sliderAngleTree, angle); 
		setValueNoUpdate(sliderLength, length); 
		
		//button.settext doesn't send update
		f.leaves = leaves;
		buttonLeaves.setText("Only Leaves : " + (leaves ? "Yes" : "No"));

		f.trunk = trunk;
		buttonTrunk.setText("Trunk : " + (trunk ? "Yes" : "No"));
	}
	
	public void setValueTreeAdvanced(int n, boolean leaves,boolean trunk, int[][] branchesData) {
		tabbedPane.setSelectedIndex(1);
		tabTree.setSelectedIndex(1);
		
		//no update to prevent multiple fractal generation
		setValueNoUpdate(sliderIterationTreeA, n); 
		
		//button.settext doesn't send update
		f.leavesA = leaves;
		buttonLeavesA.setText("Only Leaves : " + (leaves ? "Yes" : "No"));

		f.trunkA = trunk;
		buttonTrunkA.setText("Trunk : " + (trunk ? "Yes" : "No"));
		
		Branch[] branches = new Branch[branchesData.length];
		for(int i = 0 ; i< branchesData.length; i++) {
			branches[i] = new Branch(i+1, branchesData[i][0], branchesData[i][1], branchesData[i][2]);
		}
		addAllBranch(branches);
	}
	
	public void setValueLSystem(int n, int angle, String rules, String startRule) {
		tabbedPane.setSelectedIndex(2);
		
		//no update to prevent multiple fractal generation
		setValueNoUpdate(sliderIterationLSystem, n); 
		setValueNoUpdate(sliderAngleLsystem, angle); 

		LSystemRules.setText(rules);
		LSystemStart.setText(startRule); 
		
		keyReleased(null);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		f.clearRules();
		String rules = LSystemRules.getText();
		try {
			for(int i = 0; i<LSystemRules.getLineCount();i++) {
				int start = LSystemRules.getLineStartOffset(i);
				int end = LSystemRules.getLineEndOffset(i);
				int ruleNumber = rules.charAt(start)-'A';
				if(ruleNumber >= 0 && ruleNumber < 26) {
					f.rules[ruleNumber] = rules.substring(start+4,end);	
					System.out.println("Rules :"+ rules.charAt(start)+ f.rules[ruleNumber]);
				}
			}
		} catch (Exception e2) {}
		System.out.println("Rules :F"+ f.rules['F'-'A']);
		f.StartL = LSystemStart.getText();
//		System.out.println("new LSystem : " + f.ruleF);
		sendStateChangeUpdate();
	}
}
