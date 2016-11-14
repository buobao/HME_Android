package com.hme.turman.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.hme.turman.HmeApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by diaoqf on 2016/10/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected HmeApplication mApplication;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (HmeApplication) getApplication();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mCompositeSubscription = new CompositeSubscription();
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

    protected void addTask(Subscription subscriber) {
        mCompositeSubscription.add(subscriber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
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
