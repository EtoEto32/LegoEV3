package jp.ac.kanazawait.ep.majorlabB.logger;

import java.io.BufferedWriter;
import java.io.IOException;

import jp.ac.kanazawait.ep.majorlabB.util.LegoColor;

public class LoggerThread extends AbstLoggerThread {

	/**
	 * インスタンスを取得するクラスメソッド
	 * @return 唯一のインスタンス
	 */
	public static LoggerThread getInstance() {
		return instance;
	}

	/**
	 * 時間ログ記録用配列
	 */
	protected long[] arrayTime;

	/**
	 * センサーデータログ記録用配列
	 */
	protected float[][] arrayColorChecker;

	/**
	 * モーターデータログ記録用配列
	 */
	protected float[][] arrayMotorSpeed;

	/**
	 * モーターデータログ記録用配列
	 */
	protected float[][] arrayActualMotorSpeed;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void allocateArray(int length) {
		arrayTime = new long[length];
		arrayColorChecker = new float[length][];
		arrayMotorSpeed = new float[length][2];
		arrayActualMotorSpeed = new float[length][2];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveToLog(int currentLength) {
		arrayTime[currentLength] = System.currentTimeMillis();
		arrayColorChecker[currentLength] = car.getColorInformations();
		float[] speeds = car.getMotorSpeeds();
		arrayMotorSpeed[currentLength][0] = speeds[0];
		arrayMotorSpeed[currentLength][1] = speeds[1];
		speeds = car.getActualMotorSpeeds();
		arrayActualMotorSpeed[currentLength][0] = speeds[0];
		arrayActualMotorSpeed[currentLength][1] = speeds[1];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeHeader(BufferedWriter bw) throws IOException {
		bw.write("time, ColorID, Color, Reflected, R, G, B, RGBTotal, speedLeft, speedRight");
		bw.newLine();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeData(BufferedWriter bw, int index) throws IOException {
		// 経過時間
		bw.write("" + (arrayTime[index] - starttime));
		// カラーID
		int colorId = (int) arrayColorChecker[index][0];
		bw.write(", " + colorId);
		bw.write(", " + LegoColor.colorId2Name(colorId));
		// その他カラーセンサーの値
		for (int j = 1; j < arrayColorChecker[j].length; j++) {
			bw.write(", " + arrayColorChecker[index][j]);
		}
		bw.write(", " + (arrayColorChecker[index][2] + arrayColorChecker[index][3] + this.arrayColorChecker[index][4]));
		// モーター設定速度
		bw.write(", " + arrayMotorSpeed[index][0] + ", " + arrayMotorSpeed[index][1]);
		// モーター実測速度
		bw.write(", " + arrayActualMotorSpeed[index][0] + ", " + arrayActualMotorSpeed[index][1]);

		// １行分の最後の改行
		bw.newLine();
	}

	/**
	 * Singletonパターンとするための，唯一のインスタンス
	 */
	private static LoggerThread instance = new LoggerThread();

	/**
	 * コンストラクタ
	 * singletonパターンを適用するため，他からは呼べないprivateで宣言してある
	 */
	private LoggerThread() {
		initLog();
	}

}
