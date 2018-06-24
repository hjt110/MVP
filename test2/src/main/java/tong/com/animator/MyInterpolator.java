package tong.com.animator;


import android.util.Log;
import android.view.animation.Interpolator;

public class MyInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        Log.e("input",(1-input)+"");
        return 1-input;
    }
}
