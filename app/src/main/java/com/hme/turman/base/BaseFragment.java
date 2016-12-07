package com.hme.turman.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hme.turman.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by diaoqf on 2016/10/31.
 */

public abstract class BaseFragment extends Fragment {

    protected LayoutInflater inflater;
    protected CompositeSubscription mCompositeSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCompositeSubscription = new CompositeSubscription();
        this.inflater = inflater;
        View view = inflater.inflate(getLayoutId(), container, false);

        if (useBindFrame()) {
            ButterKnife.bind(this, view);
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }

        if (hasToolbar()) {
            if (showBackButton()) {
                if (getBackListener() != null) {
                    view.findViewById(R.id.back).setOnClickListener(getBackListener());
                } else {
                    view.findViewById(R.id.back).setOnClickListener(v -> getActivity().onBackPressed());
                }
                view.findViewById(R.id.back).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.back).setVisibility(View.GONE);
            }

            if (showRightMenu()) {
                view.findViewById(R.id.title_menu).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.title_menu)).setText(getRightMenuTitle());
                view.findViewById(R.id.title_menu).setOnClickListener(getRightMenuListener());
            }

            ((TextView)view.findViewById(R.id.title)).setText(getPageTitle());
        }

        init(savedInstanceState);

        return view;
    }

    protected abstract void init(Bundle savedInstanceState);
    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }
    
    protected void addTask(Subscription subscriber) {
        mCompositeSubscription.add(subscriber);
    }

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
        return false;
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

    protected void toast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
    protected void toast(int msg) {
        Toast.makeText(getActivity(),getResources().getString(msg),Toast.LENGTH_SHORT).show();
    }
}
