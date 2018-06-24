package tong.com.test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Base2Activity {


    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        textView.setBackgroundColor(Color.argb(0xff,0x00,0x00,0x00));
    }

    private void initUI() {


    }
    @OnClick(R.id.textView)
    public void ddd(View view){
        Toast.makeText(getActivity(),"jjkkjk",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
