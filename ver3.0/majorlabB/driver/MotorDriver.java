package jp.ac.kanazawait.ep.majorlabB.driver;

import lejos.hardware.motor.BaseRegulatedMotor;

/**
 * モーターをコントロールするためのインタフェース
 * @author mmotoki
 *
 */
public interface MotorDriver {

	/**
	 * 既定の速度で直進状態に設定
	 */
	void goStraight();

	/**
	 * 直進状態に設定
	 * @param speed	設定する速度
	 */
	void goStraight(int speed);

	/**
	 * 左に曲がる状態に設定
	 */
	void turnLeft();

	/**
	 * 右に曲がる状態に設定
	 */
	void turnRight();

	/**
	 * 左にきつく曲がる状態に設定
	 */
	void turnLeftSharply();

	/**
	 * 右にきつく曲がる状態に設定
	 */
	void turnRightSharply();

	/**
	 * 左にゆるく曲がる状態に設定
	 */
	void turnLeftGently();

	/**
	 * 右にゆるく曲がる状態に設定
	 */
	void turnRightGently();

	/**
	 * スピードを上げる
	 */
	void increaseSpeed();

	/**
	 * スピードを下げる
	 */
	void decreaseSpeed();

	/**
	 * 前進開始
	 * 後退している時に後退させたいときは，一度stop()せよ
	 */
	void forward();

	/**
	 * 後退開始
	 * 前進している時に後退させたいときは，一度stop()せよ
	 */
	void backward();

	/**
	 * 停止
	 */
	void stop();

	/**
	 * モーターが動いているかチェックする
	 * @return	どちらかでもモーターが動いていれば true，そうでなければ false
	 */
	boolean isMoving();

	/**
	 * 左モーターが動いているか
	 * @return
	 */
	boolean isMovingLeft();

	/**
	 * 右モーターが動いているか
	 * @return
	 */
	boolean isMovingRight();

	/**
	 * モーターが停止しているかチェックする
	 * @return	両方のモーターが停止していれば true，そうでなければ false
	 */
	boolean isStalled();

	/**
	 * 左右両方のホイールを同時に一定角度だけ回転
	 * @param angle	回転角度 (度)
	 */
	void rotate(int angle);

	/**
	 * ホイールをそれぞれ一定角度だけ回転
	 * @param angleLeft		左ホイールの回転角度 (度)
	 * @param angleRight	右ホイールの回転角度 (度)
	 */
	void rotate(int angleLeft, int angleRight);

	/**
	 * 左右のモーターに同じスピードを設定
	 * @param speed		設定するスピード (度/秒)
	 */
	void setSpeed(int speed);

	/**
	 * 左右のモーターごとにスピードを設定
	 * @param speedLeft	左車輪モーターの回転速度 (度/秒)
	 * @param speedRight	左車輪モーターの回転速度 (度/秒)
	 */
	void setSpeed(int speedLeft, int speedRight);

	/**
	 * モーターの回転速度を速い方に合わせる
	 */
	void AdjustToFasterSpeed();

	/**
	 * モーターの回転速度を遅い方に合わせる
	 */
	void AdjustToSlowerSpeed();

	/**
	 * 左右のモーターの回転速度を同じ差分で変化させる
	 * @param diff スピードの差分
	 */
	void changeSpeedByDiff(int diff);

	/**
	 * 左右のモーター毎にの回転速度を差分で変化させる
	 * @param diffLeft	左モーターのスピードの差分
	 * @param diffRight	右モーターのスピードの差分
	 */
	void changeSpeedByDiff(int diffLeft, int diffRight);

	/**
	 * 左右のモーターの回転速度を同じ倍率で変化させる
	 * @param diff スピードの倍率
	 */
	void changeSpeedByRatio(double ratio);

	/**
	 * 左右のモーター毎にの回転速度を倍率で変化させる
	 * @param ratioLeft
	 * @param ratioRight
	 */
	void changeSpeedByRatio(double ratioLeft, double ratioRight);

	/**
	 * 左右のモーターの最大速度の遅い方を取得
	 * @return	左右のモーターの最大速度の遅い方 （度/秒)
	 */
	float getMaxSpeed();

	/**
	 * 左右のモーターの速いほうの回転速度を取得
	 * @return	左右のモーターの回転速度の速い方 (度/秒)
	 */
	int getFasterSpeed();

	/**
	 * 左右のモーターの遅いほうの回転速度を取得
	 * @return	左右のモーターの回転速度の遅い方 (度/秒)
	 */
	int getSlowerSpeed();

	/**
	 * 左モーターの設定回転速度取得
	 * @return	左モーターの回転速度 (度/秒)
	 */
	int getSpeedLeft();

	/**
	 * 右モーターの設定回転速度取得
	 * @return	右モーターの回転速度 (度/秒)
	 */
	int getSpeedRight();

	/**
	 * 左モーターの実測回転速度取得
	 * @return	左モーターの回転速度 (度/秒)
	 */
	int getActualSpeedLeft();

	/**
	 * 右モーターの実測回転速度取得
	 * @return	右モーターの回転速度 (度/秒)
	 */
	int getActualSpeedRight();

	/**
	 * 直進設定かどうかを調べる
	 * @return	直進ならtrue
	 */
	boolean isStraight();

	/**
	 * 左に曲がる設定かどうかを調べる
	 * @return	左に曲がる設定ならtrue
	 */
	boolean isTurnLeft();

	/**
	 * 右に曲がる設定かどうかを調べる
	 * @return	右に曲がる設定ならtrue
	 */
	boolean isTurnRight();

	/**
	 * 使用している Motor の取得
	 * @return wheelLeft と wheelRight の配列
	 */
	BaseRegulatedMotor[] getMotors();

}
