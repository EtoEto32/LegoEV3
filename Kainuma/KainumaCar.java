package jp.ac.kanazawait.ep.Kainuma;
import jp.ac.kanazawait.ep.majorlabB.car.AbstCar;
import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorCheckerThread;
import jp.ac.kanazawait.ep.majorlabB.navigator.Navigator;
import lejos.hardware.Button;
import lejos.robotics.Color;
public class KainumaCar extends AbstCar {
    private Navigator curNavi;
	public static void main(String[] args) {
		TimeKeeper car = new KainumaCar();
		car.start();
	}

	public KainumaCar(){
		colorChecker = ColorCheckerThread.getInstance();
		driver = new KainumaNaiveDriver("B", "C");
		navigator = new KainumaNaiveRightEdgeTracer();
	}
    @Override
    public void run() {
        while (!Button.ESCAPE.isDown() && colorChecker.getColorId() != Color.RED) {
        	//この条件を満たしていないと、コース外にいってしまう。
            if (Button.ENTER.isDown()) {
                switchDirection(); 
                while(Button.ENTER.isDown()) { //押してから動くように
                	
                }
            }
            curNavi.decision(colorChecker, driver);
        }
    }
    private boolean LeftEdgeTracer = true;//取り敢えず左から
    public void switchDirection() {
        if (LeftEdgeTracer) {
            curNavi = new KainumaNaiveLeftEdgeTracer();
            LeftEdgeTracer = !LeftEdgeTracer;//反転してエッジ走行を切り替える。
            System.out.println("LeftEdge");
        } else {
            curNavi = new KainumaNaiveRightEdgeTracer();
            System.out.println("RightEdge");
            LeftEdgeTracer = !LeftEdgeTracer;//反転してエッジ走行を切り替える。
        }
    }

}
