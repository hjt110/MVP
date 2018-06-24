package tong.com.animator;

import android.animation.TypeEvaluator;
import android.util.Log;

public class MyEvaluation implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {

        Log.e("fraction",fraction+"");
        Log.e("startValue",startValue+"");
        Log.e("endValue",endValue+"");
        Log.e("result",(int) (startValue+ fraction * (endValue - startValue))+"");

        return (int) (startValue+ fraction * (endValue - startValue));
    }
}
