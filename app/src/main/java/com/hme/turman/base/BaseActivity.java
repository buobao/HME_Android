package com.hme.turman.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hme.turman.HmeApplication;
import com.hme.turman.R;

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

        if (hasToolbar()) {
            if (showBackButton()) {
                if (getBackListener() != null) {
                    findViewById(R.id.back).setOnClickListener(getBackListener());
                } else {
                    findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
                }
                findViewById(R.id.back).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.back).setVisibility(View.GONE);
            }

            if (showRightMenu()) {
                findViewById(R.id.title_menu).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.title_menu)).setText(getRightMenuTitle());
                findViewById(R.id.title_menu).setOnClickListener(getRightMenuListener());
            }

            ((TextView)findViewById(R.id.title)).setText(getPageTitle());
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

    /**
     * 是否显示返回按钮
     * @return
     */
    protected boolean showBackButton() {
        return true;
    }

    /**
     * 设置标题
     * @return
     */
    protected String getPageTitle() {
        return "";
    }

    /**
     * 设置是否显示右侧菜单
     * @return
     */
    protected boolean showRightMenu() {
        return false;
    }

    /**
     * 设置右侧菜单名称
     * @return
     */
    protected String getRightMenuTitle() {
        return "";
    }

    /**
     * 设置右侧菜单点击事件
     * @return
     */
    protected View.OnClickListener getRightMenuListener() {
        return null;
    }

    /**
     * 返回按钮事件
     * @return
     */
    protected View.OnClickListener getBackListener() {
        return null;
    }

    /**
     * 是否添加工具栏
     * @return
     */
    protected boolean hasToolbar() {
        return true;
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
