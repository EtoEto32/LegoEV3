package jp.ac.kanazawait.ep.mmotoki.sample;

import jp.ac.kanazawait.ep.majorlabB.car.AbstCar;
import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorCheckerThread;
import lejos.hardware.Button;
import lejos.robotics.Color;

public class MotokiCar extends AbstCar {

	public static void main(String[] args) {
		TimeKeeper car = new MotokiCar();
		car.start();
	}

	public MotokiCar() {
		colorChecker = ColorCheckerThread.getInstance();
		driver = new MotokiNaiveDriver("B", "C");
		navigator = new MotokiNaiveLeftEdgeTracer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (!Button.ESCAPE.isDown() && colorChecker.getColorId() != Color.RED) {
			navigator.decision(colorChecker, driver);
		}
	}

}
