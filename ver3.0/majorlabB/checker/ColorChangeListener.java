package jp.ac.kanazawait.ep.majorlabB.checker;

public interface ColorChangeListener {

	/**
	 * color idの変化を通知された時に行う動作を実装するメソッド
	 * @param colorId	通知された（変化後の）カラーID
	 */
	void colorChangeDetected(final int colorId);
}
