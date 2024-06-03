package jp.ac.kanazawait.ep.majorlabB.car;

import java.util.ArrayList;

import jp.ac.kanazawait.ep.majorlabB.checker.ColorChecker;
import jp.ac.kanazawait.ep.majorlabB.driver.MotorDriver;
import jp.ac.kanazawait.ep.majorlabB.logger.Logger;
import jp.ac.kanazawait.ep.majorlabB.navigator.Navigator;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

/**
 * 車全体の抽象クラス
 * 具象クラスでrun()メソッドを実装すること．
 * run メソッドは実装済みの start メソッドから自動的に呼び出される．
 * @author mmotoki
 *
 */
public abstract class AbstCar implements TimeKeeper {
	/**
	 * {@inheritDoc}
	 */
	@Override
	final public void start() {
		// 前処理
		preProcess();

		// 走行
		run();

		// 後処理
		postProcess();
	}

	/**
	 * 走行前の追加処理．
	 * 必要ならば，このクラスを継承したクラスでオーバーライドせよ．
	 */
	protected void additionalPreProcess() {
	}

	/**
	 * 走行後の追加処理
	 * 必要ならば，このクラスを継承したクラスでオーバーライドせよ．
	 */
	protected void additionalPostProcess() {
	}

	/**
	 * 使用するセンサーチェッカー
	 */
	protected ColorChecker colorChecker;

	/**
	 * 現在使用しているdriver
	 */
	protected MotorDriver driver;

	/**
	 * 現在使用しているnavigator
	 */
	protected Navigator navigator;

	/**
	 * 使用するLogger
	 */
	protected Logger logger;

	/**
	 * 走行開始時刻 (ms)
	 * start メソッドで現在時刻を取得する
	 */
	private long startTime;

	/**
	 * 走行終了時刻 (ms)
	 * stop メソッドで現在時刻を取得する
	 */
	private long endTime;

	private ArrayList<CarListener> listeners;

	final private void preProcess() {
		LCD.drawString("Preparing", 0, 0);
		// センサー計測開始
		colorChecker.startChecker();

		// loggerが設定されていれば，ログ記録スレッド初期化
		if (logger != null) {
			logger.initLog();
		}

		// 追加処理
		additionalPreProcess();

		// 動作開始のボタン押下を待機
		Sound.buzz();
		LCD.clear();
		LCD.drawString("Press ENTER to start", 0, 0);
		Button.ENTER.waitForPress();

		// loggerが設定されていれば，ログ記録開始
		if (logger != null) {
			logger.startLogging();
		}

		// 動作開始
		LCD.clear();
		LCD.drawString("Press ESCAPE to stop", 0, 0);
		// 開始時刻記録

		if (listeners != null) {
			for (CarListener listener : listeners) {
				listener.carStarted();
			}
		}

		this.startTime = System.currentTimeMillis();
	}

	/**
	 * 走行後の処理
	 */
	final private void postProcess() {
		// モーター停止
		driver.stop();

		// 終了時刻記録
		this.endTime = System.currentTimeMillis();

		// センサー計測停止
		colorChecker.stopChecker();

		// 停止通知
		if (listeners != null) {
			for (CarListener listener : listeners) {
				System.out.println("notify stalled");
				listener.carStalled();
			}
		}

		// 走行時間表示
		LCD.clear();
		LCD.drawString("Time (s) =" + ((double) ((endTime - startTime) / 100) / 10), 0, 0);

		// loggerが設定されていたら，ログ記録スレッド停止・csv書き出し
		if (this.logger != null) {
			this.logger.stopLogging();
			LCD.drawString("Writing log", 0, 1);
			this.logger.writeToCSV("log.csv");
			LCD.drawString(" finished", 11, 1);
		}

		// 追加処理
		additionalPostProcess();

		// 動作終了待ち
		LCD.drawString("Press ESC", 0, 2);
		Button.ESCAPE.waitForPress();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float[] getMotorSpeeds() {
		return new float[] { driver.isMovingLeft() ? driver.getSpeedLeft() : 0,
				driver.isMovingRight() ? driver.getSpeedRight() : 0 };
	}

	/**
	 * 左右のモーターの実際の速度取得
	 * @return 左右のモーターの速度の配列 (0: left, 1: right)
	 */
	public float[] getActualMotorSpeeds() {
		return new float[] { driver.getActualSpeedLeft(), driver.getActualSpeedRight() };
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float[] getColorInformations() {
		return colorChecker.getAllInfomation();
	}

	public void addListener(CarListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<CarListener>();
		}
		listeners.add(listener);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public MotorDriver getDriver() {
		return driver;
	}
}
