package com.hme.turman.api;

import com.hme.turman.BuildConfig;
import com.hme.turman.Contents;
import com.hme.turman.HmeApplication;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lebro on 2016/10/30.
 */

public class RetrofitHelper {
    /**
     * 获取normal client
     * @return
     */
    public static Retrofit createRetrofit(Map<String, String> headers,boolean isCached, String host){
        return  new Retrofit.Builder()
                .client(getHttpClient(headers, isCached))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(host)
                .build();
    }

    /**
     * 创建httpclient
     * @return
     */
    private static OkHttpClient getHttpClient(Map<String, String> headers, boolean hasCache){

        //过滤器
        Interceptor okhttpLogIntercepter = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder requestBuild = request.newBuilder();
                requestBuild.addHeader(Contents.APP_VERSION,BuildConfig.VERSION_NAME);
                if (headers != null) {
                    for (String key:headers.keySet()) {
                        requestBuild.addHeader(key,headers.get(key));
                    }
                }

                if (BuildConfig.DEBUG) {
                    long t1 = System.nanoTime();
                    Response response = chain.proceed(request);
                    long t2 = System.nanoTime();

                    double time = (t2-t1)/1e6d;

                    String msg = "\nurl->" + request.url()
                            + "\ntime->" + time
                            + "ms\nheaders->" + request.headers()
                            + "\nresponse code->" + response.code()
                            + "\nresponse headers->" + response.headers();

                    if (request.method().equals("GET")) {
                        Logger.i("GET"+msg);
                    } else if (request.method().equals("POST")) {
                        Request copyRequest = request.newBuilder().build();
                        if (copyRequest.body() instanceof FormBody) {
                            Buffer buffer = new Buffer();
                            copyRequest.body().writeTo(buffer);
                            Logger.i("request params:" + buffer.readUtf8());
                        }
                        Logger.i("POST"+msg);
                    } else if (request.method().equals("PUT")) {
                        Logger.i("PUT"+msg);
                    } else if (request.method().equals("DELETE")) {
                        Logger.i("DELETE"+msg);
                    }
                    return response;
                }

                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(ApiContents.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(ApiContents.DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ApiContents.DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);

        if (hasCache){
            builder.addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    int maxAge = 60; // 在线缓存在1分钟内可读取
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                }});

            File httpCacheDirectory = new File(HmeApplication.getContext().getCacheDir(), "FindPropertyCache");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            builder.cache(cache);
        }
        builder.addInterceptor(okhttpLogIntercepter);

        return builder.build();
    }

}
