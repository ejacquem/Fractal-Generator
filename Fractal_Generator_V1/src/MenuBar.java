
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuBar extends JMenuBar implements ActionListener,MenuListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenu presetMenu = new JMenu("Preset");
	JMenu saveMenu = new JMenu("Save");
	JMenu animateMenu = new JMenu("Animate");
	JMenu settingsMenu = new JMenu("Settings");
	
	JMenu Koch = new JMenu("Koch");
	JMenu TreeSimple = new JMenu("TreeSimple");
	JMenu TreeAdvanced = new JMenu("TreeAdvanced");
	JMenu LSystem = new JMenu("L-System");
	
	JMenuItem SaveImage;
	JMenu QualityOfImage = new JMenu("Quality Of Image");
	JMenuItem SaveCurrentFractal;
	JMenu SavedFractalMenu = new JMenu("Load My Fractal");
	JMenuItem OpenSaveFolder;
	JMenuItem[] MyFractalSave;
	
	ButtonGroup groupQuality;
	JRadioButtonMenuItem qualityLow;
	JRadioButtonMenuItem qualityNormal;
	JRadioButtonMenuItem qualityHigh;
	JRadioButtonMenuItem qualitySuperHigh;

	JCheckBoxMenuItem Play;
	JMenu AnimationSpeed = new JMenu("Speed");
	JMenu AnimationQuality = new JMenu("Quality");
	
	ButtonGroup groupSpeed;
	JRadioButtonMenuItem speedSlow;
	JRadioButtonMenuItem speedNormal;
	JRadioButtonMenuItem speedFast;
	
	JCheckBoxMenuItem lockSkewAngleRatio;
	JCheckBoxMenuItem showAxis;
	JMenuItem recenter;
	
	ButtonGroup groupCenter;
	JRadioButtonMenuItem centerToTrunk;
	JRadioButtonMenuItem centerToMiddle;
	
	ButtonGroup groupZoom;
	JRadioButtonMenuItem zoomMode1;
	JRadioButtonMenuItem zoomMode2;
	JRadioButtonMenuItem zoomMode3;
	
	JMenu limitMenu = new JMenu("Number of Line limit");
	ButtonGroup groupLimit;
	JRadioButtonMenuItem limit10_000;
	JRadioButtonMenuItem limit50_000;
	JRadioButtonMenuItem limit100_000;
	JRadioButtonMenuItem limit500_000;
	JRadioButtonMenuItem limit1_000_000;
	JRadioButtonMenuItem noLimit;
	
	Animation animation = new Animation();
	
	SettingsPanel Spanel;
	
	Save save = new Save();
	
	public MenuBar() {
		addMenuPresets(PresetData.values());
		presetMenu.add(Koch);
		presetMenu.add(TreeSimple);
		presetMenu.add(TreeAdvanced);
		presetMenu.add(LSystem);
		
		makeAnimationMenu();
		makeSaveMenu();
		
		showAxis = new JCheckBoxMenuItem("Show axis");
		showAxis.addActionListener(this);
		settingsMenu.add(showAxis);
		
		lockSkewAngleRatio = new JCheckBoxMenuItem("Lock skew angle / angle ratio");
		lockSkewAngleRatio.addActionListener(this);
		settingsMenu.add(lockSkewAngleRatio);
		settingsMenu.addSeparator();
		
		recenter = new JMenuItem("Recenter Fractal");
		recenter.addActionListener(this);
		settingsMenu.add(recenter);
		settingsMenu.addSeparator();
		
        groupCenter = new ButtonGroup();
        centerToMiddle = makeRadioButtonMenu(groupCenter, "Center To Middle");
        centerToTrunk = makeRadioButtonMenu(groupCenter, "Center To Trunk");
        settingsMenu.addSeparator();
        
        groupZoom = new ButtonGroup();
        zoomMode1 = makeRadioButtonMenu(groupZoom, "Zoom in center of Fractal");
        zoomMode2 = makeRadioButtonMenu(groupZoom, "Zoom in center of Board");
        zoomMode3 = makeRadioButtonMenu(groupZoom, "Zoom on mouse");
        settingsMenu.addSeparator();
        
        groupLimit = new ButtonGroup();
        limit10_000 = makeRadioButtonMenu(groupLimit, "10 000");
        limit50_000 = makeRadioButtonMenu(groupLimit, "50 000");
        limit100_000 = makeRadioButtonMenu(groupLimit, "100 000");
        limit500_000 = makeRadioButtonMenu(groupLimit, "500 000");
        limit1_000_000 = makeRadioButtonMenu(groupLimit, "1 000 000");
        noLimit = makeRadioButtonMenu(groupLimit, "Unlimited");
        settingsMenu.add(limitMenu);
        
        centerToMiddle.setSelected(true);
        zoomMode2.setSelected(true);
        limit100_000.setSelected(true);
        
		this.add(presetMenu);
		this.add(saveMenu);
		this.add(animateMenu);
		this.add(settingsMenu);
	}
	
	private void makeAnimationMenu() {
		Play = new JCheckBoxMenuItem("Play ▶");
		Play.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.VK_ALT));
		Play.addActionListener(this);
		
		groupSpeed = new ButtonGroup();
		speedSlow = makeRadioButtonMenu(groupSpeed, "Slow");
		speedNormal = makeRadioButtonMenu(groupSpeed, "Normal");
		speedFast = makeRadioButtonMenu(groupSpeed, "Fast");
		speedNormal.setSelected(true);
        
        animateMenu.add(Play);
        animateMenu.addSeparator();
        animateMenu.add(AnimationSpeed);
	}
	private void makeSaveMenu() {
		saveMenu.addMenuListener(this);
		
		SaveImage = new JMenuItem("Save Image",null);
		SaveImage.addActionListener(this);
		
		groupQuality = new ButtonGroup();
		qualityLow = makeRadioButtonMenu(groupQuality, "Low");
		qualityNormal = makeRadioButtonMenu(groupQuality, "Normal");
		qualityHigh = makeRadioButtonMenu(groupQuality, "High");
		qualitySuperHigh = makeRadioButtonMenu(groupQuality, "SuperHigh");
		qualityNormal.setSelected(true);
		
		SaveCurrentFractal = new JMenuItem("SaveCurrentFractal");
		SaveCurrentFractal.addActionListener(this);

		OpenSaveFolder = new JMenuItem("OpenSaveFolder");
		OpenSaveFolder.addActionListener(this);
		
		saveMenu.add(OpenSaveFolder);
		saveMenu.addSeparator();
		saveMenu.add(SaveImage);
		saveMenu.add(QualityOfImage);
		saveMenu.addSeparator();
		saveMenu.add(SaveCurrentFractal);
		saveMenu.add(SavedFractalMenu);
		
		updateSavedFractalMenu();
	}

	public class MenuPreset extends JMenuItem {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public final PresetData data;
		MenuPreset(PresetData p) {this.data = p;}
	}
	
	private JRadioButtonMenuItem makeRadioButtonMenu(ButtonGroup g,String s) {
		JRadioButtonMenuItem b = new JRadioButtonMenuItem(s);
		b.addActionListener(this);
        g.add(b);
        if(g == groupCenter || g == groupZoom)settingsMenu.add(b);
        if(g == groupLimit)limitMenu.add(b);
        if(g == groupSpeed  )AnimationSpeed.add(b);
        if(g == groupQuality  )QualityOfImage.add(b);
        return b;
	}
	
	/*
	 * Create a MenuPreset (JMenuItem) for each constant in enum PresetData and
	 * add them to their appropriate JMenu.
	 */
	private void addMenuPresets(PresetData[] presets) {
		for(PresetData p : presets) {
			MenuPreset a = new MenuPreset(p);
			a.addActionListener(this);
			a.setText(p.getName());
			switch (p.getType()) {
				case 'K':Koch.add(a);break;
				case 'S':TreeSimple.add(a);break;
				case 'A':TreeAdvanced.add(a);break;
				case 'L':LSystem.add(a);break;
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e == null)return;
		if(e.getSource() instanceof MenuPreset)setValue(((MenuPreset)e.getSource()).data);
		if(e.getSource() == centerToTrunk || e.getSource() == centerToMiddle) {
			if(centerToTrunk.isSelected()) {
				Spanel.f.centerToTrunk = true;
				System.out.println("trunk anchored");
			}				
			else Spanel.f.centerToTrunk = false;
		}
		if(e.getSource() == lockSkewAngleRatio) Spanel.lockSkew = lockSkewAngleRatio.isSelected();
		if(e.getSource() == showAxis) Spanel.showAxis = showAxis.isSelected();
		if(e.getSource() == zoomMode1) Spanel.frame.zoomMode = 1;
		if(e.getSource() == zoomMode2) Spanel.frame.zoomMode = 2;
		if(e.getSource() == zoomMode3) Spanel.frame.zoomMode = 3;
		if(e.getSource() == limit10_000) 	Spanel.f.limitOfLine =    10_000;
		if(e.getSource() == limit50_000) 	Spanel.f.limitOfLine =    50_000;
		if(e.getSource() == limit100_000) 	Spanel.f.limitOfLine =   100_000;
		if(e.getSource() == limit500_000) 	Spanel.f.limitOfLine =   500_000;
		if(e.getSource() == limit1_000_000) Spanel.f.limitOfLine = 1_000_000;
		if(e.getSource() == qualityLow		) Spanel.frame.quality = 1;
		if(e.getSource() == qualityNormal	) Spanel.frame.quality = 2;
		if(e.getSource() == qualityHigh		) Spanel.frame.quality = 5;
		if(e.getSource() == qualitySuperHigh) Spanel.frame.quality = 10;
		if(e.getSource() == noLimit) Spanel.f.limitOfLine = 100_000_000;
		if(e.getSource() == recenter) recenter();
		if(e.getSource() == speedSlow  ) animation.setSpeed(1);
		if(e.getSource() == speedNormal) animation.setSpeed(2);
		if(e.getSource() == speedFast  ) animation.setSpeed(4);
		if(e.getSource() == Play) {
			if(Play.isSelected())animation.animate();
			else animation.stop();
		}
		if(e.getSource() == SaveImage) Spanel.frame.panel.saveImage();
		if(e.getSource() == SaveCurrentFractal) {
			String s;
			switch (Spanel.currentTab) {
				case "00":
				case "01":s = "Koch ";break;
				case "10":s = "TreeSimple ";break;
				case "11":s = "TreeAdvanced ";break;
				case "20":
				case "21":s = "LSystem ";break;
				default:return;
			}
			save.SaveFractal(Spanel.f, s);
		}
		if(e.getSource() == OpenSaveFolder) {
			try {
				System.out.println("Opening folder");
				Desktop.getDesktop().open(new File(".\\"));
			} catch (Exception e2) {}
		}
		else {
			for(int i = 0 ; i < MyFractalSave.length ; i++) {
				if(e.getSource() == MyFractalSave[i]) {
					save.SetValue(i);
				}
			}
		}
		Spanel.sendStateChangeUpdate();
		Spanel.frame.update();
	}
	public void setValue(PresetData p) {
		switch (p.getType()) {
			case 'K':Spanel.setValueKoch(p);break;
			case 'S':Spanel.setValueTreeSimple(p);break;
			case 'A':Spanel.setValueTreeAdvanced(p);break;
			case 'L':Spanel.setValueLSystem(p);break;
		}
		//send update to generate fractal with new value
		Spanel.stateChanged(null);
	}

	public void recenter() {
		double x = Spanel.frame.panel.getSize().width/2;
		double y = Spanel.frame.panel.getSize().height/2;
		Spanel.f.boardCenter = new Point2D(x,y);
	}
	
	@Override
	public void menuSelected(MenuEvent e) {
		if(e.getSource() == saveMenu) {
			System.out.println("Save menu Selected");
			updateSavedFractalMenu();
		}
	}

	@Override
	public void menuDeselected(MenuEvent e) {}

	@Override
	public void menuCanceled(MenuEvent e) {}
	
	public void updateSavedFractalMenu(){
		SavedFractalMenu.removeAll();
		String[] SavedFractal = save.getSavedFractal();
		MyFractalSave = new JMenuItem[SavedFractal.length];
		for(int i = 0 ; i < MyFractalSave.length ; i++) {
			System.out.println("#" +i + SavedFractal[i]);
			if(SavedFractal[i].length() >5&& Pattern.matches("|Koch |TreeS|TreeA|LSyst|", SavedFractal[i].substring(0,5))) {
				int start = SavedFractal[i].indexOf(":")+1;
				int end = SavedFractal[i].indexOf("(");
				MyFractalSave[i] = new JMenuItem(SavedFractal[i].substring(start,end));
				MyFractalSave[i].addActionListener(this);
				SavedFractalMenu.add(MyFractalSave[i]);
			}
		}
		saveMenu.add(SavedFractalMenu);
	}
}
