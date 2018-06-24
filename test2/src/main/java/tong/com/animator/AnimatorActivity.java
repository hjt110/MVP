package tong.com.animator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tong.library.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tong.com.test.R;

public class AnimatorActivity extends BaseActivity {


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_animator;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.button, R.id.button3,R.id.button4})
    public void toChange(View view) {
        final int left = textView.getLeft();
        final int top = textView.getTop();
        final int right = textView.getRight();
        final int bottom = textView.getBottom();
        switch (view.getId()) {
            case R.id.button:
                ValueAnimator animator = ValueAnimator.ofInt(0, 200);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        textView.layout(left, top + animatedValue, right, bottom + animatedValue);
                    }
                });
                animator.setDuration(1000);
                animator.setInterpolator(new MyInterpolator());
                animator.setEvaluator(new MyEvaluation());
                animator.start();
                break;
            case R.id.button3:
                final ValueAnimator animator1 = ValueAnimator.ofObject(new CharEvaluation(), new Character('A'), new Character('z'));
                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        char animatedValue = (char) animator1.getAnimatedValue();
                        button3.setText(String.valueOf(animatedValue));
                    }
                });
                animator1.setDuration(2000);
                animator1.start();
                break;
            case R.id.button4:
                ObjectAnimator rotation = ObjectAnimator.ofFloat(textView, "rotationY", 0, 180);
                rotation.setDuration(1000);
                rotation.setRepeatCount(ValueAnimator.INFINITE);
                rotation.setRepeatMode(ValueAnimator.RESTART);
                rotation.start();
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
