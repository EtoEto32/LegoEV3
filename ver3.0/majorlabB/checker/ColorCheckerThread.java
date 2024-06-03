package jp.ac.kanazawait.ep.majorlabB.checker;

import jp.ac.kanazawait.ep.majorlabB.util.LegoColor;
import lejos.hardware.lcd.LCD;

/***
 * <p>カラーセンサーの測定を続けるスレッドの具象クラス．
 * <strong>センサーは必ず<em>S2ポート</em>に接続</strong>すること．
 * Singletonパターンを使うので，一つしかインスタンスを作成できない．</p>
 * <p>使用法：</p>
 * <ol>
 * <li> getInstance()でインスタンスを取得する
 * <li> <strong>1.で取得したインスタンスに対し，<em>startChecker()</em>を実行</strong>し，測定スレッド開始．
 * <li> 測定値を得たいときは，ColorChecker インタフェースで定義されている get～ メソッドを用いる．
 * <li> <strong>スレッドの実行を終了するには，<em>stopChecker()</em>を実行</strong>．
 * </ol>
 * @author mmotoki
 *
 */
public class ColorCheckerThread extends AbstColorCheckerThread {

	/**
	 * 測定したsampleを表示する開始位置のx座標
	 * showSample()で使用する
	 * publicなので，値を直接読み書きできる
	 */
	public int xShowAt = 0;

	/**
	 * 測定したsampleを表示する開始位置のy座標
	 * showSample()で使用する
	 * publicなので，値を直接読み書きできる
	 */
	public int yShowAt = 2;

	/**
	 * インスタンスを取得するクラスメソッド
	 * @return 唯一のインスタンス
	 */
	public static ColorCheckerThread getInstance() {
		return instance;
	}

	@Override
	public synchronized void showSample(int x, int y) {
		LCD.drawString("ID : " + this.getColorId() + " " + LegoColor.colorId2Name(this.getColorId()), x, y);
		LCD.drawString("Red: 0." + (int) (this.getRed() * 100), x, y + 1);
		LCD.drawString("RGB:", x, y + 2);
		float[] rgb = this.getRGB();
		LCD.drawString("R:" + (int) (rgb[0] * 10) / 10.0
				+ " G:" + (int) (rgb[1] * 10) / 10.0
				+ " B:" + (int) (rgb[2] * 10) / 10.0,
				x, y + 3);
		float[] hsv = LegoColor.rgb2hsv(rgb);
		LCD.drawString("H:" + (int) hsv[0]
				+ " S:" + (int) (hsv[1] * 10) / 10.0
				+ " V:" + (int) (hsv[2] * 10) / 10.0,
				x, y + 4);
		LCD.drawString("GREYSCALE:" + ((int) ((rgb[0] + rgb[1] + rgb[2]) / 3 * 10)) / 10.0, x, y + 5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected synchronized void fetchSample() {
		providerRed.fetchSample(this.sampleRed, 0);
		providerRGB.fetchSample(this.sampleRGB, 0);
		//		providerColorId.fetchSample(this.sampleColorID, 0);
		callListeners();

		// 測定値の表示
		if (showSample) {
			showSample(this.xShowAt, this.yShowAt);
		}
	}

	/**
	 * センサーを接続したポート
	 */
	private final String port = "S2";

	/**
	 * Singletonパターンとするための，唯一のインスタンス
	 */
	private static ColorCheckerThread instance = new ColorCheckerThread();

	/**
	 * コンストラクタ
	 * singletonパターンを適用するため，他からは呼べないprivateで宣言してある
	 */
	private ColorCheckerThread() {
		this.init(port);
	}

}
