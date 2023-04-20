
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class Program extends JFrame implements MouseMotionListener, MouseListener,MouseWheelListener,ComponentListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static int HEIGHT = 720;
	private final static int WIDTH = 1080;
	
	public static Fractal fractal = new Fractal();

	public DrawingPanel	panel;
	public SettingsPanel Spanel = new SettingsPanel();
	public MenuBar menu = new MenuBar();
	
	public JLayeredPane layer = new JLayeredPane();
	
	private int mousePosX;
	private int mousePosY;
	private int mouseLivePosX;
	private int mouseLivePosY;
	private int boardCenterPosX;
	private int boardCenterPosY;
	
	public int quality = 2;
	
	public int zoomMode = 2;
	
	private Point2D currentComponentSize = new Point2D(HEIGHT/2,HEIGHT/2);
	
	Program(){
		panel = new DrawingPanel();
		
		Spanel.f = fractal;
		Spanel.frame = this;		
		fractal.branches = Spanel.branches;
		menu.Spanel = Spanel;
		menu.animation.Spanel = Spanel;
		menu.save.Spanel = Spanel;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(WIDTH,HEIGHT);
		setLocationRelativeTo(null);
		setJMenuBar(menu);
		setTitle("Fractal Generator");
		
		Spanel.setPreferredSize(new Dimension(300,0));
		
		add(panel,BorderLayout.CENTER);
		add(Spanel,BorderLayout.EAST);
		
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		addComponentListener(this);

		componentResized(null);
		
		menu.recenter();
		menu.setValue(PresetData.TA_CLASSIC);
		menu.setValue(PresetData.TS_CLASSIC);
		menu.setValue(PresetData.L_HILBERT);
		menu.setValue(PresetData.K_SNOWFLAKE);
		
		componentResized(null);
		
		setVisible(true);
	}
	
	public void test() {

	}
	
	public void zoom(int i) {
		if((fractal.zoom > 10_000_000 && i==-1) || (fractal.zoom < 0.001 && i==1)) {
			System.out.println("ZOOM LIMIT | Can't go further");
			return;
		}

		fractal.zoom *= Math.pow(1.2, -i);
		
		if(zoomMode !=1) {
			int x=0,y=0;
			if(zoomMode == 2) {
				x = panel.getSize().width/2;
				y = panel.getSize().height/2;				
			}
			if(zoomMode == 3) {
				x = mouseLivePosX-7;
				y = mouseLivePosY-51;
			}
			fractal.boardCenter.substract(new Point2D(x,y));
			fractal.boardCenter.multiply(Math.pow(1.2, -i));
			fractal.boardCenter.add(new Point2D(x,y));
		}
		
		System.out.println("zoom ratio : "+fractal.zoom );
		
		Spanel.sendStateChangeUpdate();
	}
	
	@Override
	public void mouseDragged(MouseEvent me) {
		int x = me.getX() - mousePosX;
		int y = me.getY() - mousePosY;
		
		fractal.boardCenter.setX(boardCenterPosX+x);
		fractal.boardCenter.setY(boardCenterPosY+y);
		
		System.out.println("Fractal position : "+fractal.boardCenter);
		
		Spanel.sendStateChangeUpdate();
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		mouseLivePosX = me.getX();
		mouseLivePosY = me.getY();
	}		
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePosX = e.getX();
		mousePosY = e.getY();
		boardCenterPosX = (int)fractal.boardCenter.getX();
		boardCenterPosY = (int)fractal.boardCenter.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom(e.getWheelRotation());
	}
	
	public void update() {
		Spanel.repaint();

		panel.repaint();
		menu.repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		
		Point2D diff = new Point2D(
				panel.getSize().width  - currentComponentSize.getX(),
				panel.getSize().height - currentComponentSize.getY());

		diff.multiply(0.5);
		fractal.boardCenter.add(diff);
		System.out.println("component resized : "+diff.getX() + ", " + diff.getY());
		System.out.println("Panel size : "+panel.getSize().width + ", " + panel.getSize().height);
		Spanel.sendStateChangeUpdate();
		
		currentComponentSize = new Point2D(panel.getSize().width,panel.getSize().height);
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}
	 
	
	class DrawingPanel extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void saveImage() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH.mm.ss");
			LocalDateTime now = LocalDateTime.now();
			String path = String.format(".\\My Fractal Images\\%s.png", dtf.format(now));//  2021/03/22_16:37:15
			System.out.println(path);
			
			BufferedImage image = new BufferedImage(getWidth()*quality,getHeight()*quality, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = image.createGraphics();
			paint(g2,quality);
			try{
				ImageIO.write(image, "png", new File(path));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		public void paint(Graphics g1) {
			
			Graphics2D g = (Graphics2D) g1;
			g.setPaint(Color.darkGray);
			g.fillRect(0, 0, this.getSize().width, this.getSize().height);
			
			if(Spanel.showAxis) {
				g.setPaint(Color.gray);
				int x = this.getSize().width/2;
				int y = this.getSize().height/2;
				g.drawLine(0, y, x*2,  y);
				g.drawLine(x, 0, x  ,y*2);
			}
		
			g.setPaint(Color.white);
			try {
				for (Line2D l : fractal.lines) {
					g.drawLine(
							(int)l.getP1().getX(),
							(int)l.getP1().getY(),
							(int)l.getP2().getX(),
							(int)l.getP2().getY());
				}
			} catch (Exception e) {
				System.out.println("WEIRD ERROR");
			}
		}
		public void paint(Graphics g1, int length) {
			
			int x = this.getSize().width*length;
			int y = this.getSize().height*length;
			
			Graphics2D g = (Graphics2D) g1;
			g.setPaint(Color.darkGray);
			g.fillRect(0, 0, x, y);
			g.setPaint(Color.white);
			
			for (Line2D l : fractal.lines) {
				l.multiply(length);
				g.drawLine(
					(int)l.getP1().getX(),
					(int)l.getP1().getY(),
					(int)l.getP2().getX(),
					(int)l.getP2().getY());
			}
		}
	}
}