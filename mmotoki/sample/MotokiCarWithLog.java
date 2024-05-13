package jp.ac.kanazawait.ep.mmotoki.sample;

import jp.ac.kanazawait.ep.majorlabB.car.AbstCar;
import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorCheckerThread;
import jp.ac.kanazawait.ep.majorlabB.logger.LoggerThread;
import lejos.hardware.Button;
import lejos.robotics.Color;

public class MotokiCarWithLog extends AbstCar {

	public static void main(String[] args) {
		TimeKeeper car = new MotokiCarWithLog();
		car.start();
	}

	public MotokiCarWithLog() {
		colorChecker = ColorCheckerThread.getInstance();
		driver = new MotokiNaiveDriver("B", "C");
		navigator = new MotokiNaiveLeftEdgeTracer();
		// ログ設定
		logger = LoggerThread.getInstance();
		logger.setCar(this);
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
