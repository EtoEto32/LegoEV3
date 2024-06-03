package jp.ac.kanazawait.ep.majorlabB.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import lejos.robotics.Color;

/**
 * leJOS EV3 の lejos.robotics.Color クラスを便利に使うためのクラス
 * クラス定数とクラスメソッドのみを定義しているので，インスタンスを作成せずに利用可能
 * @author mmotoki
 *
 */
final public class LegoColor {
	/**
	 * カラーIDと色の名前の対応を保存するHashMap
	 * staticフィールドなので，このクラスのインスタンスを作成しなくても使用可能
	 */
	public static final Map<Integer, String> colorNames = getColorName();

	/**
	 * カラーIDから色の名前を取得するメソッド
	 * クラスメソッドなので，このクラスのインスタンスを作成しなくても使用可能
	 * @param id	int: カラーID
	 * @return		String: 対応する色の名前
	 */
	public static String colorId2Name(int id) {
		return LegoColor.colorNames.get(id) == null ? "NULL" : LegoColor.colorNames.get(id);
	}

	/**
	 * lejos.robotics.Colorクラスのstaticフィールドとして定義されているカラーIDと色の名前の対応表を作成するメソッド．
	 * @return	キーがカラーID，値が色の名前であるHashMapオブジェクト
	 */
	private static Map<Integer, String> getColorName() {
		/**
		 * カラーIDと色の名前の対応表
		 */
		HashMap<Integer, String> colorIdMap = new HashMap<>();

		/**
		 * 色の名前の最大長
		 */
		int maxLength = 0;

		// lejos.robotics.Colorクラスで宣言されたフィールドの取得
		Field[] declaredFields = Color.class.getDeclaredFields();

		// 色の名前の最大長の取得
		for (Field field : declaredFields) {
			// staticフィールドだけを取り出す
			if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				if (maxLength < field.getName().length())
					maxLength = field.getName().length();
			}
		}

		for (Field field : declaredFields) {
			// staticフィールドだけを取り出す
			if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				// 色番号をキーに，フィールド（色）の名前を値にしてHashMapに挿入
				// 色の名前は，最大長に合わせて空白をパディング
				try {
					colorIdMap.put(field.getInt(null),
							String.format("%-" + Integer.toString(maxLength) + "s", field.getName()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return colorIdMap;
	}

	/**
	 * convert RGB color space into HSV (cone type) color space
	 * @param rgb	a float array that consists of Red, Green, Blue values (0 to 1) respectively
	 * @return a float array that consists of Hue, Saturation, Value values respectively
	 */
	//	public static float[] rgb2hsv(float[] rgb) {
	public static float[] rgb2hsv(float[] sample) {
		float[] rgb = { sample[0], sample[1], sample[2] };
		float[] hsv = new float[3];

		float min = rgb[0];
		float max = rgb[0];
		int minIndex = 0;
		for (int i = 1; i < 3; i++) {
			if (rgb[i] < min) {
				min = rgb[i];
				minIndex = i;
			} else if (rgb[i] > max) {
				max = rgb[i];
			}
		}

		if (min == max) {
			hsv[0] = Float.NaN;
		} else {
			switch (minIndex) {
			case 0: // min == red
				hsv[0] = 60 * (rgb[2] - rgb[1]) / (max - min) + 180;
				break;
			case 1: // min == green
				hsv[0] = 60 * (rgb[0] - rgb[2]) / (max - min) + 300;
				break;
			default: // min == blue
				hsv[0] = 60 * (rgb[1] - rgb[0]) / (max - min) + 60;
			}
			while (hsv[0] >= 360) {
				hsv[0] -= 360;
			}
			while (hsv[0] < 0) {
				hsv[0] += 360;
			}
		}

		hsv[1] = max - min;

		hsv[2] = max;

		return hsv;
	}

	/**
	 * convert hsv color to LEGO color id
	 * @param hsv	array of float:i.e., H, S, and V
	 * @return	colorid
	 */
	public static int hsv2colorid(float[] hsv) {
		if (hsv.length != 3) {
			throw new IllegalArgumentException("length of array != 3: " + hsv.length);
		}

		/**
		 * returnするカラーID
		 */
		int colorId;

		// 無彩色に近い場合
		if (hsv[0] == Float.NaN || hsv[1] <= hsv[2] / 2 || hsv[1] < 0.05) {
			if (hsv[2] > 0.2f) {
				colorId = Color.WHITE;
			} else if (hsv[2] > 0.15f) {
				colorId = Color.LIGHT_GRAY;
			} else if (hsv[2] > 0.1f) {
				colorId = Color.DARK_GRAY;
			} else {
				colorId = Color.BLACK;
			}
			// 有彩色の場合
		} else { // hsv[0] != Float.NaN
			if (hsv[0] < 30 || hsv[0] >= 330) {
				colorId = Color.RED;
			} else if (hsv[0] < 90) {
				colorId = Color.YELLOW;
			} else if (hsv[0] < 150) {
				colorId = Color.GREEN;
			} else if (hsv[0] < 210) {
				colorId = Color.CYAN;
			} else if (hsv[0] < 270) {
				colorId = Color.BLUE;
			} else {
				colorId = Color.MAGENTA;
			}
		}

		return colorId;
	}

	/**
	 * convert rgb color to LEGO color id
	 * @param rgb	array of float:i.e., R, G, and B
	 * @return	colorid
	 */
	public static int rgb2colorid(float[] rgb) {
		if (rgb.length != 3) {
			throw new IllegalArgumentException("length of array != 3: " + rgb.length);
		}
		return hsv2colorid(rgb2hsv(rgb));
	}
}
