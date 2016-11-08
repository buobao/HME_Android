package com.hme.turman.utils;

import com.hme.turman.api.bean.ResponseBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lebro on 2016/10/30.
 */

public class RetrofitUtil {
    private final static Observable.Transformer ioTransformer = o -> ((Observable)o).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    public static <T> Observable.Transformer<T, T> applyIoSchedulers(){
        return (Observable.Transformer<T, T>) ioTransformer;
    }

    /**
     * 统一处理服务端结果码
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<ResponseBean<T>, T> handleResult(){
        return new Observable.Transformer<ResponseBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<ResponseBean<T>> apiResponseObservable) {
                return apiResponseObservable.flatMap(
                        new Func1<ResponseBean<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(ResponseBean<T> tApiResponse) {
                                if (tApiResponse==null){
                                    return Observable.empty();
                                }else if (tApiResponse.getCode() == 200){
                                    //请求成功
                                    return Observable.create(new Observable.OnSubscribe<T>() {
                                        @Override
                                        public void call(Subscriber<? super T> subscriber) {
                                            try {
                                                subscriber.onNext(tApiResponse.getResult());
                                                subscriber.onCompleted();
                                            }catch (Exception e){
                                                subscriber.onError(e);
                                            }
                                        }
                                    });
                                }else if (tApiResponse.getCode() == 404){
                                    //api访问失败处理
                                    return Observable.empty();
                                } else if (tApiResponse.getCode() == 500) {
                                    //服务器错误处理
                                    return Observable.create(new Observable.OnSubscribe<T>() {
                                        @Override
                                        public void call(Subscriber<? super T> subscriber) {
                                            try {
                                                subscriber.onNext(tApiResponse.getResult());
                                                subscriber.onCompleted();
                                            }catch (Exception e){
                                                subscriber.onError(e);
                                            }
                                        }
                                    });
//                                    return Observable.error(new Exception(tApiResponse.getMessage()+""));
                                } else if (tApiResponse.getCode() == 600){
                                    //登录过期处理
                                    return Observable.empty();
                                }
                                return Observable.error(new UnknownError());
                            }
                        });
            }
        };
    }
}
