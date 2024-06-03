package jp.ac.kanazawait.ep.Kainuma;

import jp.ac.kanazawait.ep.majorlabB.checker.ColorChecker;
import jp.ac.kanazawait.ep.majorlabB.driver.MotorDriver;
import jp.ac.kanazawait.ep.majorlabB.navigator.Navigator;
import lejos.robotics.Color;

public class KainumaNaiveRightEdgeTracer implements Navigator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void decision(ColorChecker colorChecker, MotorDriver driver) {
		switch (colorChecker.getColorId()) {
		case Color.WHITE:
			driver.turnLeft();
			driver.forward();
			break;
		case Color.BLACK:
			driver.turnRight();
			driver.forward();
			break;
		default:
			driver.goStraight();
			driver.forward();
		}
	}

}
