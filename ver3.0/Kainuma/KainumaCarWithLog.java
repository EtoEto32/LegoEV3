package jp.ac.kanazawait.ep.Kainuma;

import jp.ac.kanazawait.ep.majorlabB.car.AbstCar;
import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorCheckerThread;
import jp.ac.kanazawait.ep.majorlabB.logger.LoggerThread;
import lejos.hardware.Button;
import lejos.robotics.Color;

public class KainumaCarWithLog extends AbstCar {

	public static void main(String[] args) {
		TimeKeeper car = new KainumaCarWithLog();
		car.start();
	}

	public KainumaCarWithLog() {
		colorChecker = ColorCheckerThread.getInstance();
		driver = new KainumaNaiveDriver("B", "C");
		navigator = new KainumaNaiveLeftEdgeTracer();
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
