package tong.com.animator;

import android.animation.TypeEvaluator;

public class CharEvaluation implements TypeEvaluator<Character> {
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int startInt = startValue;
        int endInt = endValue;
        int currentInt = (int) (startInt+ fraction*(endInt-startInt));
        return (char) currentInt;
    }
}
