package jp.ac.kanazawait.ep.majorlabB.navigator;

import jp.ac.kanazawait.ep.majorlabB.checker.ColorChecker;
import jp.ac.kanazawait.ep.majorlabB.driver.MotorDriver;

public interface Navigator {
	/**
	 * 走行戦略を具体化するメソッド
	 * @param sensor	センサー
	 * @param driver	走行のための具象クラス
	 */
	public abstract void decision(ColorChecker colorChecker, MotorDriver driver);
}
