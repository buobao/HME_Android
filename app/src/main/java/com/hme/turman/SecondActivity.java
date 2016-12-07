package com.hme.turman;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by diaoqf on 2016/10/28.
 */

public class SecondActivity extends Activity {
    @BindView(R.id.test)
    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_second);
        ButterKnife.bind(this);
        test.setOnClickListener(v->{
//            EventBus.getDefault().post(new DemoEvent());

        });
    }
}
