package jp.ac.kanazawait.ep.majorlabB.car;

import jp.ac.kanazawait.ep.majorlabB.driver.MotorDriver;

/**
 * 計時を行うためのインタフェース
 * @author mmotoki
 *
 */
public interface TimeKeeper {

	/**
	 * 全体の動作処理
	 */
	void start();

	/**
	 * 実際の走行処理
	 */
	void run();

	/**
	 * 左右のモーターの設定速度取得
	 * @return 左右のモーターの速度の配列 (0: left, 1: right)
	 */
	float[] getMotorSpeeds();

	/**
	 * 左右のモーターの実際の速度取得
	 * @return 左右のモーターの速度の配列 (0: left, 1: right)
	 */
	float[] getActualMotorSpeeds();

	/**
	 * カラーセンサー情報の取得
	 * @return カラーセンサー情報 (0: ColorID, 1, Red, 2 RGB）
	 */
	float[] getColorInformations();

	/**
	 * driver の取得
	 * @return	使用している driver
	 */
	MotorDriver getDriver();
}
