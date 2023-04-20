
//
public enum PresetData {
	
	K_SNOWFLAKE(8,60,0,3,2,false),
	K_MIRROR(5,120,0,1,5,true),
	K_PENTAGON(5,1080,0,5,6,false),
	K_SIERPINSKI_TRIANGLE(8,120,0,1,3,false),
	K_SIERPINSKI_CARPET(5,90,0,1,3,true),
	K_COOL_STUFF(6,45,360,2,3,false),
	
	TS_CLASSIC(13, 50,2,700,false ,true),
	TS_TRIANGLE(10,120,3,500,true,true),
	TS_LEVY_CURVE(15, 90,2,700,true,true),
	TS_PENTAGON( 7, 72,5,381,true,false),
	TS_STAR( 8, 72,5,618,true,false),
	TS_KOCH(15,300,2,577,true,false),
	TS_KOCH_CURVED(15,300,2,577,false ,false),
	TS_HIGHWAY(13,270,2,670,false ,false),
	TS_ROSE(1000,257,1,999,false ,true),
	TS_SPIRAL(1000,59,1,993,false ,true),
	
	TA_CLASSIC(13,false,true,new int[][] {
		{40,70,100},
		{-20,70,100},
	}),
	TA_FERN(10,false,true,new int[][] {
		{72,30,60},
		{4,85,100},
		{-72,30,100}
		}),
	TA_BEECH(7,false,true,new int[][] {
		{0,50,100},
		{45,50,50},
		{-45,50,50},
		{30,50,70},
		{-30,50,70}
		}),
	TA_KOCHMIRROR(6,true,false,new int[][] {
		{60,33,33},
		{-60,33,33},
		{120,33,66},
		{-120,33,66},
		{-180,33,33},
		{0,33,66},
	}),
	TA_FIR(6,false,true,new int[][] {
		{-110,20,70},
		{ 110,20,70},
		{-110,25,50},
		{ 110,25,50},
		{-110,30,25},
		{ 110,30,25},
		{0,50,75},
		{0,90,15},
	}),
	TA_MAPLE(10,false,true,new int[][] {
		{45,50,50},
		{0,50,100},
		{-45,50,75}
	}),
	TA_COOL_TREE1(9,false,true,new int[][] {
		{-20,70,30},
		{-20,10,100},
		{20,10,100},
		{20,70,30},
	}),
	
	L_KOCH(8,60,"F -> F+F--F+F",""),
	L_CARPET(5,90,"F -> F+F-F-FF-F-FF-F-F+F",""),
	L_TRIANGLE(8,120,"F -> F+F-F-F+F",""),
	L_TRIANGLE2(7,60,"X -> YF+XF+Y\r\n"
			+ "Y -> XF-YF-X\r\n"
			+ "F -> F","YF"),
	L_PENTAGON(6,72,"F -> FF+F+F+F+F+FF",""),
	L_BALL(4,30,"F -> AF\n"
			+ "A -> F+F+F+F+F+F+F+F+F+F+F+F+F","A"),
	L_KOCH_WORLD(7,60,"X -> XF+XF--XF+XF", "X---X-X---X-X---X++X+++X+X+++X+X+++X"),
	L_BOARD(5,90,"F -> FF+F+F+F+FF", "F+F+F+F"),
//	L_(7,60,"", ""),
	L_PENTAPLEXITY(6,36,"F -> F++F++F|F-F++F", "F++F++F++F++F"),
	L_TILES(4,90,"F -> FF+F-F+F+FF", "F+F+F+F"),
	L_SNAKE_KOLAM(5,45,"X -> XFX--XFX\r\n"
			+ "F -> F", "X--X"),
	L_SIERPINSKI_CURVE(5,45,"X -> XF+F+XF--F--XF+F+X\r\n"
			+ "F -> F", "F--XF--F--XF"),
	L_HEXAGONAL_GOSPER(5,60,"X -> X+YF++YF-FX--FXFX-YF+\r\n"
			+ "Y -> -FX+YFYF++YF+FX--FX-Y\r\n"
			+ "F -> F", "X"),
	L_HILBERT(5,90,"A -> -BF+AFA+FB-\r\n"
			+ "B -> +AF-BFB-FA+\r\n"
			+ "F -> F", "A"),
	L_DRAGON(10,45,"Y -> +FX--FY+\n"
			+ "X -> -FX++FY-", "X"),
	L_LEVY(13,45,"A -> -AF++AF-", "A")
	
	;
	
	int n,angle,skew,side,segment,branch,length;
	boolean mirror,leaves,trunk;
	int[][] branchesData;
	String rules;
	String startRule;
	
	PresetData(int n, int angle , int skew, int side ,int segment , boolean mirror){
		this.n = n;
		this.angle = angle;
		this.skew = skew;
		this.side = side;
		this.segment = segment;
		this.mirror = mirror;
	}
	PresetData(int n, int angle , int branch ,int length , boolean leaves,boolean trunk){
		this.n = n;
		this.angle = angle;
		this.branch = branch;
		this.length = length;
		this.leaves = leaves;
		this.trunk = trunk;
	}
	PresetData(int n, boolean leaves,boolean trunk, int[][] arr){
		this.n = n;
		this.leaves = leaves;
		this.trunk = trunk;
		this.branchesData = arr;
	}
	PresetData(int n, int angle, String rules, String startRule){
		this.n = n;
		this.angle = angle;
		this.rules = rules;
		this.startRule = startRule;
	}
	
	public char getType() {
		return this.toString().charAt(this.toString().indexOf("_")-1);
	}
	
	public String getName() {
		int index = toString().indexOf('_')+1;
		return toString().substring(index,index+1) + toString().substring(index+1).toLowerCase();
		
	}
}