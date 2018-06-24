package tong.com.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tong.library.base.BaseActivity;

public abstract class Base2Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("base2","oncreat");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("base2","ondestroy");
    }
}
