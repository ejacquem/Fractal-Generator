
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Branch extends JPanel implements ChangeListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SettingsPanel Spanel;
	
	int branchNumber;
	
	int angle 	= 60;
	int length 	= 50;
	int position= 100;
	
	JPanel p;
	
	JSlider sliderAngle;
	JSlider sliderLength;
	JSlider sliderPosition;
	
	
	Branch(SettingsPanel Spanel,int branchNumber){
		this.branchNumber = branchNumber;
		this.Spanel = Spanel;
		makePanel();
		stateChanged(null);
	}
	
	Branch(int branchNumber,int angle,int length, int position){
		this.branchNumber = branchNumber;
		this.angle = angle;
		this.length = length;
		this.position = position;
		
		makePanel();
	}
	
	private void makePanel() {
		p = new JPanel();
		
		p.setLayout(new GridLayout(3, 1));
		p.setBorder(BorderFactory.createTitledBorder(String.format("Branch %d", branchNumber)));
				
		sliderAngle = makeSlider(new JSlider(-180,180,angle));
		sliderLength = makeSlider(new JSlider(0,100,length));
		sliderPosition = makeSlider(new JSlider(0,100,position));
		updateTitle();
		
		p.add(sliderAngle);
		p.add(sliderLength);
		p.add(sliderPosition);

		add(p);
	}
	
	private JSlider makeSlider(JSlider slider) {
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(10);
		slider.addChangeListener(this);
		
		return slider;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(haveChanged()) {
			Spanel.lastModifiedBranch = this;
			
			angle = sliderAngle.getValue();
			length = sliderLength.getValue();
			position = sliderPosition.getValue();
			
			System.out.println(angle);
			
			updateTitle();
			
			if(Spanel !=null) {
				Spanel.branchHaveChanged = true;
				Spanel.stateChanged(null);
			}
		}
	}
	
	public void updateTitle() {
		setTitle(sliderAngle,"Angle : %d°");
		setTitle(sliderLength,"length of branch : %d%%");
		setTitle(sliderPosition,"Position : %d%%");
	}
	
	public void setTitle(JSlider slider, String title) {
		TitledBorder titled = BorderFactory.createTitledBorder(String.format(title, slider.getValue()));
        slider.setBorder(titled);
	}
	
	private boolean haveChanged() {
		return !(angle == sliderAngle.getValue() && 
				length == sliderLength.getValue() && 
				position == sliderPosition.getValue()
				);
	}
	public void setRandomValue() {
		setValueNoUpdate(sliderAngle, (int) (Math.random()*sliderAngle.getMaximum()*2)-180);
		setValueNoUpdate(sliderLength, (int) (Math.random()*sliderLength.getMaximum()));
		setValueNoUpdate(sliderPosition, (int) (Math.random()*sliderPosition.getMaximum()));
		angle = sliderAngle.getValue();
		length = sliderLength.getValue();
		position = sliderPosition.getValue();
		updateTitle();
	}
	
	public void setValueNoUpdate(JSlider s,int value) {
		s.removeChangeListener(this);
		s.setValue(value);
		s.addChangeListener(this);
	}
	
	@Override
	public String toString() {
		return "Branch Value :\n"
				+ "angle 	= "+ angle +";\n"
				+ "length 	= "+ length +";\n"
				+ "position = "+ position +";\n";
	}
}
