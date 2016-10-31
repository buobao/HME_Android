package com.hme.turman.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by diaoqf on 2016/10/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        if (useBindFrame()) {
            ButterKnife.bind(this);
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    abstract protected int getContentLayout();

    /**
     * 使用butterknife
     * @return
     */
    protected boolean useBindFrame(){
        return true;
    }

    /**
     * 是用消息总线
     * @return
     */
    protected boolean useEventBus(){
        return false;
    }



    //Utils

    /**
     * toast message
     * @param msg
     */
    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void toast(int msg) {
        Toast.makeText(this,getResources().getString(msg),Toast.LENGTH_SHORT).show();
    }
}
