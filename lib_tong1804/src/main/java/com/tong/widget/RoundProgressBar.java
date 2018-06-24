package com.tong.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import legow.com.lib_tong1804.R;


/**
 * Created by ZD-104 on 2017/12/22.
 */

public class RoundProgressBar extends View
{
    /**
     * 画笔对象的引用
     */
    private Paint paint;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环的宽度
     */
    private float roundWith;

    /**
     * 字体颜色
     */
    private int textClolr;

    /**
     * 字体大小
     */
    private float textSize;

    /**
     * 进度
     */
    private int progress;

    /**
     * 最大进度值
     */
    private int max;

    /**
     * 圆环进度颜色
     */
    private int roundProgressColor;

    /**
     * 是否显示字体进度
     */
    private boolean textIsDisplay;

    /**
     * 进度的风格，空心或实心
     */
    private int style;

    private final static int STORKE = 0;
    private final static int FILL = 1;

    public RoundProgressBar(Context context)
    {
        super(context);
    }

    public RoundProgressBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);

        roundWith = typedArray.getDimension(R.styleable.RoundProgressBar_roundWith, 5);
        roundColor = typedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
        textClolr = typedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        textSize = typedArray.getDimension(R.styleable.RoundProgressBar_textSize, 40);
        max = typedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.YELLOW);
        textIsDisplay = typedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplay, true);
        style = typedArray.getInt(R.styleable.RoundProgressBar_style, 0);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        /**
         * 画最外层的圆
         */
        int center = getWidth() / 2;//获取圆心的坐标
        int radius = (int) (center - roundWith / 2);//圆环的半径
        Log.e("roundColor", roundColor + "");
        Log.e("roundWith", roundWith + "");
        paint.setColor(roundColor);//设置圆环颜色
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(roundWith);//设置圆环的宽度
        paint.setAntiAlias(true);//消除锯齿
        canvas.drawCircle(center, center, radius, paint);

        /**
         * 画进度百分比
         */
        paint.setStrokeWidth(0);
        paint.setColor(textClolr);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT);
        int percent = (int) (((float) progress / (float) max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWith = paint.measureText(percent + "%");  //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        if (textIsDisplay && percent != 0 && style == STORKE)
        {
            canvas.drawText(percent + "%", center - textWith / 2, center + textSize / 2, paint);  //画出进度百分比
        }

        /**
         * 画圆弧，画圆环的进度
         */
        paint.setStrokeWidth(roundWith);
        paint.setColor(roundProgressColor);
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);  //用于定义的圆弧的形状和大小的界限
        switch (style)
        {
            case STORKE:
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, -90, 360 * progress / max, false, paint);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL);
                canvas.drawArc(oval, -90, 360 * progress / max, true, paint);
                break;
        }

    }

    /**
     * 获取进度，需要同步
     *
     * @return
     */
    public synchronized int getProgress()
    {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress)
    {
        if (progress < 0)
        {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max)
        {
            progress = max;
        }
        if (progress <= max)
        {
            this.progress = progress;
            postInvalidate();
        }
    }

    public synchronized int getMax()
    {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max)
    {
        if (max < 0)
        {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public int getRoundColor()
    {
        return roundColor;
    }

    public void setRoundColor(int roundColor)
    {
        this.roundColor = roundColor;
    }

    public float getRoundWith()
    {
        return roundWith;
    }

    public void setRoundWith(float roundWith)
    {
        this.roundWith = roundWith;
    }

    public int getTextClolr()
    {
        return textClolr;
    }

    public void setTextClolr(int textClolr)
    {
        this.textClolr = textClolr;
    }

    public float getTextSize()
    {
        return textSize;
    }

    public void setTextSize(float textSize)
    {
        this.textSize = textSize;
    }

    public int getRoundProgressColor()
    {
        return roundProgressColor;
    }

    public void setRoundProgressColor(int roundProgressColor)
    {
        this.roundProgressColor = roundProgressColor;
    }

}
