package jp.ac.kanazawait.ep.majorlabB.checker;

import java.util.ArrayList;

import jp.ac.kanazawait.ep.majorlabB.util.LegoColor;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/***
 * カラーセンサーの測定を続けるスレッドの抽象クラス．
 * @author mmotoki
 *
 */

public abstract class AbstColorCheckerThread extends Thread implements ColorChecker {

	/**
	 * センサーチェック間隔 (ミリ秒)．
	 * publicなので，値を直接読み書きできる．
	 * デフォルト値は 10 ミリ秒としている．
	 * EV3のタイヤの直径は 56 mm で，最大で900(度/秒)ほどの回転速度である．
	 * したがって，56 * pi * 900 / 360 * 10 / 1000 = 4.40 mm くらいの距離間隔で測定を行うことになる
	 */
	public int interval = 10;

	/**
	 * センサーで測定した値をLCDに表示するならtrue
	 * publicなので，値を直接読み書きできる
	 */
	public boolean showSample = true;

	/**
	 * 測定したsampleをLCDに表示するメソッド
	 * @param x	表示を開始する場所のx座標
	 * @param y	表示を開始する場所のy座標
	 */
	public abstract void showSample(int x, int y);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startChecker() {
		// Thread クラスの start を実行する．これによって run() で定義された内容が繰り返し実行される．
		isActive = true;
		start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopChecker() {
		isActive = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized int getColorId() {
		/**
		 * HSV色空間での色情報
		 */
		float[] hsv = LegoColor.rgb2hsv(sampleRGB);

		/**
		 * returnするカラーID
		 */
		int colorId = LegoColor.hsv2colorid(hsv);

		return colorId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized float getRed() {
		return sampleRed[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized float[] getRGB() {
		return sampleRGB;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized float[] getAllInfomation() {
		float[] data = { getColorId(), sampleRed[0], sampleRGB[0], sampleRGB[1], sampleRGB[2] };
		return data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized float getGrayScale() {
		return (this.sampleRGB[0] + this.sampleRGB[1] + this.sampleRGB[2]) / 3;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addColorChangeListener(ColorChangeListener listener) {
		if (this.listeners == null) {
			this.listeners = new ArrayList<ColorChangeListener>();
		}
		this.listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void callListeners() {
		if (listeners != null) {
			int colorId = this.getColorId();
			if (colorId != prevColorId) {
				for (ColorChangeListener listener : listeners) {
					listener.colorChangeDetected(colorId);
				}
				prevColorId = colorId;
			}
		}
	}

	/**
	 * センサー計測スレッドで実行する内容
	 */
	@Override
	public void run() {
		while (this.isActive) {
			// 測定
			fetchSample();
			Delay.msDelay(interval);
		}
	}

	/**
	 * 使用するカラーセンサー (S2ポートに接続すること)
	 */
	public SensorModes sensor;

	/**
	 * ColorIDモードのSampleProvider
	 */
	//	protected SampleProvider providerColorId;

	/**
	 * RedモードのSampleProvider
	 */
	protected SampleProvider providerRed;

	/**
	 * RGBモードのSampleProvider
	 */
	protected SampleProvider providerRGB;

	/**
	 * ColorIDモードのセンサーの測定値
	 */
	//	protected float[] sampleColorID;

	/**
	 * ColorIDモードのセンサーの測定値
	 */
	protected float[] sampleRGB;

	/**
	 * Redモードのセンサーの測定値
	 */
	protected float[] sampleRed;

	/**
	 * スレッドがアクティブなら true，アクティブでないなら false．
	 * run メソッド内でスレッドを継続するかどうかの判定のために参照し，
	 * startChecker スレッドと stopChecker スレッドでのみ変更する．
	 */
	protected boolean isActive = false;

	/**
	 * ColorChangeNotifier のための listener リスト
	 */
	protected ArrayList<ColorChangeListener> listeners = null;

	/**
	 * ColorChangeNotifier のための 直前の colorID
	 */
	protected int prevColorId;

	/**
	 * 実際にセンサーから計測値を取得するメソッド
	 */
	protected abstract void fetchSample();

	/**
	 * コンストラクタで使用する，初期化手続き．
	 * @param port	センサーをつなぐポート
	 */
	protected void init(String port) {
		// センサーの設定
		sensor = new EV3ColorSensor(LocalEV3.get().getPort(port));

		// SampleProviderの取得
		//		providerColorId = sensor.getMode("ColorID");
		providerRed = sensor.getMode("Red");
		providerRGB = sensor.getMode("RGB");

		// sample取得用配列の設定
		//		sampleColorID = new float[providerColorId.sampleSize()];
		sampleRed = new float[providerRed.sampleSize()];
		sampleRGB = new float[providerRGB.sampleSize()];
	}
}
