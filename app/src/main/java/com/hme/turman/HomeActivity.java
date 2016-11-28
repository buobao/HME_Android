package com.hme.turman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by diaoqf on 2016/10/28.
 */
public class HomeActivity extends Activity {
    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.getfromserver)
    TextView getfromserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
//        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
//        test.setOnClickListener(v -> {
//            Intent intent = new Intent(this, MapActivity.class);
//            startActivity(intent);
//        });

//        Observable.just("Hello world")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Toast.makeText(HomeActivity.this,s,Toast.LENGTH_SHORT).show();
//                    }
//                });

        //通过设置别名推送
//        JPushInterface.setAlias(getApplicationContext(), "buobao", new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//                Logger.i("别名设置回调:"+i);
//            }
//        });

//        addTask();
//        ApiHelper.getTestText().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        getfromserver.setText(s);
//                    }
//                });

        Observable<String> stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i=0;i<10;i++) {
                    subscriber.onNext("Hello RxJava");
                }
                subscriber.onCompleted();
            }
        });

        Subscription subscription = stringObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Logger.i("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("onError");
            }

            @Override
            public void onNext(String s) {
                Logger.i(s);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DemoEvent event) {
        test.setText("已更新");
    }
}
