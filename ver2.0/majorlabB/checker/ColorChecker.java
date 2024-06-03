package jp.ac.kanazawait.ep.majorlabB.checker;

/**
 * カラーセンサーから情報取得するためのインタフェース．
 * このインタフェースを実装するクラスは Thread クラスのサブクラスとして，
 * メインスレッドとは別スレッドで実行できるようにすることが望ましい．
 * @author mmotoki
 *
 */
public interface ColorChecker extends ColorChangeNotifier {
	/**
	 * センサーの計測開始
	 */
	void startChecker();

	/**
	 * センサーの計測停止
	 */
	void stopChecker();

	/**
	 * カラーIDの取得
	 * @return カラーID
	 */
	int getColorId();

	/**
	 * Redモードで測定した明るさの取得
	 * @return	Redモードで測定した明るさ (0以上1以下のfloat)
	 */
	float getRed();

	/**
	 * RGBモードで測定した３色の値の取得
	 * @return	RGBモードで測定した３色の値 (0以上1以下のfloatの長さ3の配列). インデックスはRGBの順．すなわち，0番目はRの値，1番目はGの値，2番目はBの値．
	 */
	float[] getRGB();

	/**
	 * すべてのセンサーデータの取得
	 * @return [カラーID, Redモードで測定した明るさ，RGBモードのR, RGBモードのG, RGBモードのB]
	 */
	float[] getAllInfomation();

	/**
	 * RGBの合計（グレースケール)での明るさの取得
	 * @return RGBの合計値 (0以上1以下のfloat) 0は完全な黒，1は完全な白
	 */
	float getGrayScale();

}
