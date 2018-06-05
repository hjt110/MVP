package tong.com.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tong.library.adapter.recyclerview.CommonAdapter;
import com.tong.library.adapter.recyclerview.base.ViewHolder;
import com.tong.library.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {


    @BindView(R.id.rlv_main)
    RecyclerView rlvMain;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    private void initUI() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("test" + i);
        }

//        RecyclerView rlv = findViewById(R.id.rlv_main);
        rlvMain.setLayoutManager(new LinearLayoutManager(this));
        MyAdpter myAdpter = new MyAdpter(this, R.layout.layout_rlv_main, list);
        rlvMain.setAdapter(myAdpter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    class MyAdpter extends CommonAdapter<String> {

        public MyAdpter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, String s, int position) {
            holder.setText(R.id.tv_rlv, s);
        }
    }

}
