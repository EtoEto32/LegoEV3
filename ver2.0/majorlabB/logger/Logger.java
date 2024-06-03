package jp.ac.kanazawait.ep.majorlabB.logger;

import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;

/**
 * ログ記録のためのインタフェース．
 * このクラスを実装するクラスは，Thread クラスのサブクラスとして宣言し，
 * メインスレッドとは別スレッドで実行できるようにすることが望ましい．
 * @author mmotoki
 *
 */
public interface Logger {
	/**
	 * ログ記録対象の TimeKeeper を設定する
	 * @param car
	 */
	void setCar(final TimeKeeper car);

	/**
	 * ログ初期化
	 */
	void initLog();

	/**
	 * ログ記録の開始
	 */
	void startLogging();

	/**
	 * ログ記録の停止
	 */
	void stopLogging();

	/**
	 * CSVにログを書き込む
	 * @param filename	ファイル名
	 */
	void writeToCSV(final String filename);

}
