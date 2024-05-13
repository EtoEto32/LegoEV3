package jp.ac.kanazawait.ep.majorlabB.checker;

/**
 * 色の変化を検出したときに通知するためのインタフェース
 * ColorListenerインタフェースと対になる．
 * 実装例：
 * 	private ArrayList<ColorChangeListener> listeners = null;

	@Override
	public void addColorChangeListener(ColorChangeListener listener) {
		if(this.listeners == null) {
			this.listeners = new ArrayList<ColorChangeListener>();
		}
		this.listeners.add(listener);
	}

	@Override
	public void callListeners() {
		// TODO: coloridの取得と通知するかどうかの場合分け
		for(ColorChangeListener listener : this.listeners) {
			listener.colorChangeDetected(colorid);
		}
	}
 * @author mmotoki
 *
 */
public interface ColorChangeNotifier {

	/**
	 * リスナーをリストに追加するメソッド
	 * @param listener	リストに追加されるリスナー
	 */
	void addColorChangeListener(ColorChangeListener listener);

	/**
	 * color ID が変化したイベントをリスナーに通知するメソッド．
	 * リスナーのリストに登録されている各リスナーのcolorChangeDetectedメソッドを呼び出す．
	 */
	void callListeners();

}
