package jp.ac.kanazawait.ep.Kainuma;

import jp.ac.kanazawait.ep.majorlabB.car.AbstCar;
import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorCheckerThread;
import jp.ac.kanazawait.ep.majorlabB.navigator.Navigator;
import lejos.hardware.Button;
import lejos.robotics.Color;

public class KainumaCar extends AbstCar {
    private Navigator curNavi;
    private boolean LeftEdgeTracer = true; // 最初は左エッジ走行に設定する（外側からだとなんか挙動は変になるので）

    public static void main(String[] args) {
        TimeKeeper car = new KainumaCar();
        car.start();
    }

    public KainumaCar() {
        colorChecker = ColorCheckerThread.getInstance();
        driver = new KainumaNaiveDriver("B", "C");
        curNavi= new KainumaNaiveLeftEdgeTracer(); // 左エッジ走行ナビゲータを使用
    }

    @Override
    public void run() {
        while (!Button.ESCAPE.isDown() && colorChecker.getColorId() != Color.RED) {
            int colorId = colorChecker.getColorId();
            
            if (colorId == Color.CYAN) { // シアン色を検知した場合
                switchDirection();  // ナビゲーター切り替え
                waitColorChange(Color.CYAN); // シアン色が続いている間待機
            }

            curNavi.decision(colorChecker, driver);
        }
    }

    /**
     * エッジ走行の方向を切り替えるメソッド
     */
    private void switchDirection() {
        if (LeftEdgeTracer) {
            curNavi = new KainumaNaiveRightEdgeTracer();
        } else {
            curNavi = new KainumaNaiveLeftEdgeTracer();
        }
        LeftEdgeTracer = !LeftEdgeTracer; // 反転してエッジ走行を切り替える
        Sound.playTone(440, 500); // 440Hz のトーンを 500ms 再生
    }

    /**
     * 指定された色が変更されるまで待機するメソッド
     */
    private void waitColorChange(int colorId) {
        while (colorChecker.getColorId() == colorId) {
            try {
                Thread.sleep(110); // センサーの誤動作防止、安定性向上
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
