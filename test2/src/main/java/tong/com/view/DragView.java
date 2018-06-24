package tong.com.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class DragView extends TextView {
    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float X = event.getX();
        float Y = event.getY();
        float lastX = 0;
        float lastY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = X;
                lastY = Y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) (X - lastX);
                int offsetY = (int) (Y - lastY);
                layout(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }
}
