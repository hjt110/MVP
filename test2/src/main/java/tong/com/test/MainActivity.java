package tong.com.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tong.library.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import tong.com.animator.AnimatorActivity;

public class MainActivity extends BaseActivity {


    @BindView(R.id.button2)
    Button button2;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.button2)
    public void navigation(View view){

        switch (view.getId()){
            case R.id.button2:
                Toast.makeText(getActivity(),"click here",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), AnimatorActivity.class));

                break;
        }
    }

}
