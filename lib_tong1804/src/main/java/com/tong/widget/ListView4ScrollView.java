package com.tong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListView4ScrollView extends ListView
{
    public ListView4ScrollView(Context context)
    {
        super(context);
    }

    public ListView4ScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ListView4ScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * 重写该方法，重新计算ListView高度
     * 累计所有行，计算出ListView的最大高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
