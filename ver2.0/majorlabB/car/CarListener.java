package jp.ac.kanazawait.ep.majorlabB.car;

public interface CarListener {

	/**
	 * 車が走行開始したときに呼び出される
	 */
	public void carStarted();

	/**
	 * 車が停止したときに呼び出される
	 */
	public void carStalled();
}
