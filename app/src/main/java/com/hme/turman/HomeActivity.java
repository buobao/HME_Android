package com.hme.turman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Set;
import java.util.StringJoiner;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

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
                ButterKnife.bind(this);
        test.setOnClickListener(v->{
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        });

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

//        Observable<String> stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                for (int i=0;i<10;i++) {
//                    subscriber.onNext("Hello RxJava");
//                }
//                subscriber.onCompleted();
//            }
//        });
//
//        Subscription subscription = stringObservable.subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                Logger.i("onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Logger.i("onError");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Logger.i(s);
//            }
//        });

        //基本入门
         Observable<String> observable1 = Observable.create(subscriber -> {
             subscriber.onNext("observable1");
             subscriber.onCompleted();
        });

        Observable<String> observable2 = Observable.create(subscriber -> {
            subscriber.onNext("observable2");
            subscriber.onCompleted();
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(HomeActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        };

//        observable1.subscribe(subscriber);
//        observable2.subscribe(subscriber);

        //from基本使用，迭代发射数列中的值，顺序输出字符
        Observable<String> observable3 = Observable.from(new ArrayList(){
            {
                add("Android");
                add("IOS");
            }
        });
//        observable3.subscribe(subscriber);

        //just基本使用
        Observable<Object> observable4 = Observable.just("Come on", new ArrayList<String>(){
            {
                add("Baby");
                add("Dad");
            }
        });

        Subscriber<Object> subscriber1 = new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Toast.makeText(HomeActivity.this,"completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if (o instanceof String) {
                    Toast.makeText(HomeActivity.this,(String)o,Toast.LENGTH_SHORT).show();
                } else if (o instanceof ArrayList) {
                    ArrayList<String> arrayList = (ArrayList<String>) o;
                    for (String s:arrayList) {
                        Toast.makeText(HomeActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

//        observable4.subscribe(subscriber1);

        //empty 什么都不发射
//        Observable.empty().subscribe(subscriber1);

        //never,永远都不发射
//        Observable.never().subscribe(subscriber1);

        //PublishSubject
        PublishSubject<String> publishSubject = PublishSubject.create();
        Subscription subscription = publishSubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(HomeActivity.this,"completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(HomeActivity.this,"error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(HomeActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });
//        publishSubject.onNext("Hello World!");  //这里调用并执行


        //一个实用的例子
        PublishSubject<String> demoSubject = PublishSubject.create();
        demoSubject.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(HomeActivity.this,"completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(HomeActivity.this,"error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                test.setText(s);
            }
        });

//        Observable.create(new Observable.OnSubscribe<Integer>() {
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                for (int i=0;i<5;i++) {
//                    subscriber.onNext(i);
//                }
//                subscriber.onCompleted();
//            }
//        }).doOnCompleted(new Action0() {
//            @Override
//            public void call() {
//                demoSubject.onNext("demo!");
//            }
//        }).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Toast.makeText(HomeActivity.this,integer+"",Toast.LENGTH_SHORT).show();
//            }
//        });



        //BehaviorSubject
        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create(1);
        behaviorSubject.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Toast.makeText(HomeActivity.this,integer+"",Toast.LENGTH_SHORT).show();
            }
        });



        //ReplaySubject.
        //AsyncSubject


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
