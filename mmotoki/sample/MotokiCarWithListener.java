package jp.ac.kanazawait.ep.mmotoki.sample;

import jp.ac.kanazawait.ep.majorlabB.car.AbstCar;
import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorChangeListener;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorCheckerThread;
import jp.ac.kanazawait.ep.majorlabB.logger.LoggerThread;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;

public class MotokiCarWithListener extends AbstCar implements KeyListener, ColorChangeListener {

	public static void main(String[] args) {
		LCD.drawString("ButtonEventCar", 0, 0);
		TimeKeeper car = new MotokiCarWithListener();
		car.start();
	}

	public MotokiCarWithListener() {
		colorChecker = ColorCheckerThread.getInstance();
		driver = new MotokiNaiveDriver("B", "C");
		navigator = new MotokiNaiveLeftEdgeTracer();
		// ログ設定
		logger = LoggerThread.getInstance();
		logger.setCar(this);
		// listener登録
		Button.ESCAPE.addKeyListener(this);
		colorChecker.addColorChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		isActive = true;
		// while(!Button.ESCAPE.isDown() && this.checkerColorSensor.getColorId() != Color.RED) {
		while (this.isActive) {
			navigator.decision(colorChecker, driver);
		}
	}

	/**
	 *  キーが押された時の動作
	 *  @param k	押されたキーの種類 （lejos.hardware.Button で宣言された static フィールド）
	 */
	@Override
	public void keyPressed(Key k) {
		// ESCキーが押された時の動作
		if (k == Button.ESCAPE) {
			this.isActive = false;
			this.driver.stop();
		}
	}

	/**
	 * キーが離された時の動作．基本的に必要ないので，内容を記述する必要なし．
	 *  @param k	押されたキーの種類 （lejos.hardware.Button で宣言された static フィールド）
	 */
	@Override
	public void keyReleased(Key k) {
		// 必要ないので，何も記述しない
	}

	/**
	 * 色の変化の通知を受けた時の動作
	 * @param colorId 変化後の色の番号
	 */
	@Override
	public void colorChangeDetected(int colorId) {
		if (colorId == Color.RED) {
			this.isActive = false;
			this.driver.stop();
		}
	}

	/**
	 * 動作継続条件
	 */
	private boolean isActive = false;

}
