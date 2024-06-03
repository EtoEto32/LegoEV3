package jp.ac.kanazawait.ep.majorlabB.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public abstract class AbstLoggerThread extends Thread implements Logger {
	/**
	 * ログ記録間隔 (ms)．
	 * publicなので，値を直接読み書きできる．
	 * 10ms 間隔だと，最長でもおよそ4～5mm 毎にでログを取得できる．
	 */
	public int interval = 10;

	/**
	 * ログ記録最大時間 (s)
	 * publicなので，値を直接読み書きできる
	 */
	public int logDurationTime = 120;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCar(TimeKeeper car) {
		this.car = car;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initLog() {
		logArrayLength = logDurationTime * 1000 / interval;
		// 配列確保
		allocateArray(logArrayLength);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startLogging() {
		isActive = true;
		start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopLogging() {
		isActive = false;
	}

	/**
	 * ログ記録スレッドの内容
	 */
	public void run() {
		// ログ記録開始
		starttime = System.currentTimeMillis();
		while (isActive && currentLength < logArrayLength) {
			// データ保存
			saveToLog(currentLength);
			// データ数インクリメント
			this.currentLength++;
			// ディレイ
			Delay.msDelay(interval);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToCSV(final String filename) {
		File file = new File(filename);
		BufferedWriter bw;
		LCD.drawString("Writing Log:", 0, 4);
		LCD.drawString("/" + currentLength, String.valueOf(currentLength).length(), 5);
		try {
			bw = new BufferedWriter(new FileWriter(file));
			// ヘッダ書き込み
			writeHeader(bw);
			// データ書き込み
			for (int i = 0; i < this.currentLength; i++) {
				LCD.drawInt(i, String.valueOf(currentLength).length(), 0, 5);
				writeData(bw, i);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ログ記録対象の TimeKeeper のインスタンス
	 * setCar メソッドで設定すること
	 */
	protected TimeKeeper car;

	/**
	 * ログ記録スレッドがアクティブかどうか
	 * 停止するためにはこの値をfalseとする
	 */
	protected boolean isActive = false;

	/**
	 * ログ開始時刻
	 */
	protected long starttime;

	/**
	 * ログを記録する
	 */
	protected int logArrayLength;

	/**
	 * 現在のログの長さ
	 */
	protected int currentLength = 0;

	/**
	 * length分の各配列確保
	 */
	protected abstract void allocateArray(int length);

	/**
	 * currentLength番目の測定値を配列に保存する
	 * @param currentLength
	 */
	protected abstract void saveToLog(final int currentLength);

	/**
	 * CSVにヘッダを書き込む
	 * @param bw	書き込み先のBufferedWriterのインスタンス
	 * @throws IOException
	 */
	protected abstract void writeHeader(BufferedWriter bw) throws IOException;

	/**
	 * CSVにデータを書き込む
	 * @param bw	書き込み先のBufferedWriterのインスタンス
	 * @param index 書き込むデータのインデックス
	 * @throws IOException
	 */
	protected abstract void writeData(BufferedWriter bw, int index) throws IOException;
}
