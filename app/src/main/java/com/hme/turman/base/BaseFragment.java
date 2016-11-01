package com.hme.turman.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import rx.Subscriber;
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
    
    protected void addTask(Subscriber subscriber) {
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


    //Utils

    protected void toast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
    protected void toast(int msg) {
        Toast.makeText(getActivity(),getResources().getString(msg),Toast.LENGTH_SHORT).show();
    }
}
