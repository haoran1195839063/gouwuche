package com.example.com.myapplication.model;

import android.os.Handler;

import com.example.com.myapplication.api.Api;
import com.example.com.myapplication.contract.CartContract;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class CartModel implements CartContract.CartModel {
    private Handler handler = new Handler();

    @Override
    public void getCart(HashMap<String, String> params, final ICartmodelCallback callback) {
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        Request build1 = new Request.Builder().url(Api.url).build();

        okHttpClient.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failure("失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.success(string);
                    }
                });
            }
        });

    }
}
