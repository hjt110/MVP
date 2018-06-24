package tong.com.options;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tong.com.test.R;

public class MyPopwindow {


    public static class Builder {
        private final PopupWindow popupWindow;
        private Context context;
        private final Activity activity;
        private List<String> list;
        private final TextView tv_cancle;
        private BottomPopuAdapter popuSFAdapter;
        private boolean isSet;
        private final ListView lv;
        private List<Integer> posList = new ArrayList<>();
        private List<Integer> colorList = new ArrayList<>();

        public Builder(Context context, List<String> list) {
            this.context = context;
            activity = (Activity) context;
            this.list = list;
            View contentView = LayoutInflater.from(context).inflate(R.layout.pupview, null, false);
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            backgroundAlpha(0.65f, activity);

            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            tv_cancle = contentView.findViewById(R.id.quxiao);
            tv_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            lv = contentView.findViewById(R.id.list);
            for (int i = 0; i < list.size(); i++) {
                posList.add(-1);
                colorList.add(0);
            }

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backgroundAlpha(1.0f, activity);
                        }
                    }, 290);
                }
            });
        }

        public Builder setTextColor(int position, int color) {
            isSet = true;
            posList.set(position, position);
            colorList.set(position, color);
            return this;
        }

        public void show() {
            popuSFAdapter = new BottomPopuAdapter(context, list,isSet, posList, colorList);
            lv.setAdapter(popuSFAdapter);
            popupWindow.showAtLocation(activity.getWindow()
                    .getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }

        public void backgroundAlpha(float bgAlpha, Activity activity) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = bgAlpha; //0.0-1.0
            activity.getWindow().setAttributes(lp);
        }

    }
}
