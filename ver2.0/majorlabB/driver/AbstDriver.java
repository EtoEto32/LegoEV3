package jp.ac.kanazawait.ep.majorlabB.driver;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public abstract class AbstDriver implements MotorDriver {
	/**
	 * 左車輪駆動モーター
	 */
	protected EV3LargeRegulatedMotor wheelLeft;
	/**
	 * 右車輪駆動モーター
	 */
	protected EV3LargeRegulatedMotor wheelRight;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void goStraight(int speed) {
		setSpeed(speed);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void forward() {
		if (!this.wheelLeft.isMoving()) {
			this.wheelLeft.forward();
		}
		if (!this.wheelRight.isMoving()) {
			this.wheelRight.forward();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void backward() {
		if (!this.wheelLeft.isMoving()) {
			this.wheelLeft.backward();
		}
		if (!this.wheelRight.isMoving()) {
			this.wheelRight.backward();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void stop() {
		this.wheelLeft.stop(true);
		this.wheelRight.stop(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isMoving() {
		return this.wheelLeft.isMoving() || this.wheelRight.isMoving();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isMovingLeft() {
		return this.wheelLeft.isMoving();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isMovingRight() {
		return this.wheelRight.isMoving();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isStalled() {
		return this.wheelLeft.isStalled() && this.wheelRight.isStalled();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void rotate(int angle) {
		this.wheelLeft.rotate(angle, true);
		this.wheelRight.rotate(angle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void rotate(int angleLeft, int angleRight) {
		this.wheelLeft.rotate(angleLeft, true);
		this.wheelRight.rotate(angleRight);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setSpeed(int speed) {
		this.setSpeed(speed, speed);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setSpeed(int speedLeft, int speedRight) {
		this.wheelLeft.setSpeed(speedLeft);
		this.wheelRight.setSpeed(speedRight);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void AdjustToFasterSpeed() {
		int speedLeft = this.wheelLeft.getSpeed();
		int speedRight = this.wheelRight.getSpeed();
		if (speedLeft < speedRight) {
			this.wheelLeft.setSpeed(speedRight);
		} else if (speedLeft > speedRight) {
			this.wheelRight.setSpeed(speedLeft);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void AdjustToSlowerSpeed() {
		int speedLeft = this.wheelLeft.getSpeed();
		int speedRight = this.wheelRight.getSpeed();
		if (speedLeft < speedRight) {
			this.wheelRight.setSpeed(speedLeft);
		} else if (speedLeft > speedRight) {
			this.wheelLeft.setSpeed(speedRight);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void changeSpeedByDiff(int diff) {
		this.changeSpeedByDiff(diff, diff);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void changeSpeedByDiff(int diffLeft, int diffRight) {
		this.wheelLeft.setSpeed(this.wheelLeft.getSpeed() + diffLeft);
		this.wheelRight.setSpeed(this.wheelRight.getSpeed() + diffRight);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void changeSpeedByRatio(double ratio) {
		this.changeSpeedByRatio(ratio, ratio);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void changeSpeedByRatio(double ratioLeft, double ratioRight) {
		this.wheelLeft.setSpeed((int) (this.getSpeedLeft() * ratioLeft));
		this.wheelRight.setSpeed((int) (this.getSpeedRight() * ratioLeft));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getMaxSpeed() {
		float maxSpeedLeft = this.wheelLeft.getMaxSpeed();
		float maxSpeedRight = this.wheelRight.getMaxSpeed();
		return maxSpeedLeft < maxSpeedRight ? maxSpeedLeft : maxSpeedRight;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getFasterSpeed() {
		int speedLeft = this.getSpeedLeft();
		int speedRight = this.getSpeedRight();
		return speedLeft > speedRight ? speedLeft : speedRight;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getSlowerSpeed() {
		int speedLeft = this.getSpeedLeft();
		int speedRight = this.getSpeedRight();
		return speedLeft < speedRight ? speedLeft : speedRight;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getSpeedLeft() {
		return this.wheelLeft.getSpeed();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getSpeedRight() {
		return this.wheelRight.getSpeed();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getActualSpeedLeft() {
		return this.wheelLeft.getRotationSpeed();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getActualSpeedRight() {
		return this.wheelRight.getRotationSpeed();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isStraight() {
		return wheelLeft.getSpeed() == wheelRight.getSpeed();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public BaseRegulatedMotor[] getMotors() {
		return new BaseRegulatedMotor[] { wheelLeft, wheelRight };
	}

	/**
	 * 左に曲がる設定かどうかを調べる
	 * @return	左に曲がる設定ならtrue
	 */
	public final boolean isTurnLeft() {
		return wheelLeft.getSpeed() < wheelRight.getSpeed();
	}

	/**
	 * 右に曲がる設定かどうかを調べる
	 * @return	右に曲がる設定ならtrue
	 */
	public final boolean isTurnRight() {
		return wheelLeft.getSpeed() > wheelRight.getSpeed();
	}

	/**
	 * モーターを設定する
	 * @param portLeft	左車輪モーターを接続したモーターポート名の文字列 "A"～"D"
	 * @param portRight	右車輪モーターを接続したモーターポート名の文字列 "A"～"D"
	 */
	public final void setMotor(String portLeft, String portRight) {
		wheelLeft = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(portLeft));
		wheelRight = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(portRight));
	}

	/**
	 * モーターを設定する
	 * @param leftMotor	左車輪モーターを接続したEV3LargeReguratedMotorのインスタンス
	 * @param rightMotor	右車輪モーターを接続したEV3LargeReguratedMotorのインスタンス
	 */
	public final void setMotor(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
		wheelLeft = leftMotor;
		wheelRight = rightMotor;
	}

}
