package com.tong.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义城市列表
 */
public class CustomView extends View
{

	private Paint mpaint1;
	private Paint mpaint2;
	String[] letter = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private int letterH;
	private int num;
	private boolean isTouch;

	public CustomView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initPaint();
	}

	private void initPaint()
	{
		mpaint1 = new Paint();
		mpaint1.setTextSize(30);
		mpaint1.setStyle(Style.FILL);
		mpaint1.setStrokeWidth(2);
		// mpaint1.setColor(0xffF8F8F8);
		mpaint1.setColor(Color.rgb(252, 201, 93));

		mpaint2 = new Paint();
		mpaint2.setTextSize(60);
		mpaint2.setStyle(Style.FILL);
		mpaint2.setStrokeWidth(5);
//		mpaint2.setColor(Color.BLUE);
		mpaint2.setColor(Color.rgb(252, 201, 93));

	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		int x = canvas.getWidth();
		int y = canvas.getHeight();
		canvas.translate(x / 2, 0);
		letterH = y / letter.length;
		for (int i = 0; i < letter.length; i++)
		{
			if (isTouch && num == i)
			{
				canvas.drawText(letter[i], 0, letterH * (i + 1), mpaint2);
			} else
			{
				canvas.drawText(letter[i], 0, letterH * (i + 1), mpaint1);
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		float y2 = event.getY();
		num = (int) (y2 / letterH);
		switch (action)
		{
		case MotionEvent.ACTION_DOWN:// 按下
			if (l != null)
			{
				if (num<=0)
				{
					num=0;
				}
				if (num>=letter.length)
				{
					Log.e("num", num+"");
					num=25;
					
				}
				l.onTouch(letter[num]);
			}
			isTouch = true;
			break;

		case MotionEvent.ACTION_MOVE:// 移动
			if (l != null)
			{
				if (num<=0)
				{
					num=0;
				}
				if (num>=letter.length)
				{
					num=25;
					
				}
				l.onTouch(letter[num]);
			}
			isTouch = true;
			break;

		case MotionEvent.ACTION_UP:// 放开
			if (l != null)
			{
				l.up();
			}
			isTouch = false;
			break;
		default:
			break;
		}

		invalidate();
		return true;
	}

	public interface onTouchListener
	{
		void onTouch(String letter);

		void up();
	}

	private onTouchListener l;

	public void setOnTouchListener(onTouchListener listener)
	{
		l = listener;
	}

}
