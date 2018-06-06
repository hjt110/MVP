package com.ygw.library.adapter.abslistview.base;


import com.ygw.library.adapter.abslistview.ViewHolder;


/**
 * Created by sunsh on 18/5/30.
 */
public interface ItemViewDelegate<T>
{

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);



}
