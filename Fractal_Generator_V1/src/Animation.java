

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JSlider;

public class Animation {
	
	boolean running;
	int speed=2;
	
	Timer timer;
	
	SettingsPanel Spanel;
	
	public void animate(){
		running = true;
		
		timer = new Timer();
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("##############\nAnimating...");
				if(!Spanel.generating) {
					JSlider s = getSliderToModify();
					int value = s.getValue()+speed;
					if(value > s.getMaximum())value = s.getMinimum();
					s.setValue(value);
				}
			}
		};
		timer.schedule(task,0,50);
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
		if(running) {			
			stop();
			animate();
		}
	}
	public void stop() {
		if(running)timer.cancel();
		running = false;
	}
	private JSlider getSliderToModify() {
		if(Spanel.getcurrentab().charAt(0) == '0') return Spanel.sliderAngle;
		if(Spanel.getcurrentab().charAt(0) == '2') return Spanel.sliderAngleLsystem;
		if(Spanel.getcurrentab().equals("10")) return Spanel.sliderAngleTree;
		if(Spanel.getcurrentab().equals("11")) return Spanel.getLastModifiedBranch().sliderAngle;
		System.out.println("Something Went wrong");
		return null;
	}
}
