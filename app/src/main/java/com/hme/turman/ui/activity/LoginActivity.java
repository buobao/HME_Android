package com.hme.turman.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.utils.UiUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/11/1.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.input_phone)
    EditText input_phone;
    @BindView(R.id.input_code)
    EditText input_code;
    @BindView(R.id.get_code)
    TextView get_code;
    @BindView(R.id.login_button)
    TextView login_button;
    @BindView(R.id.psw_login)
    TextView psw_login;

    @Override
    protected void init(Bundle savedInstanceState) {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());
        title.setText("手机号快捷登录");

        get_code.setOnClickListener(v->{
            if (UiUtil.checkPhone(input_phone.getText().toString())) {
                get_code.setEnabled(false);
                Timer timer = new Timer();
                final boolean[] required = {false};
                final int[] seconds = {60};
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(()->{
                            if (!required[0]) {
                                //这里从服务器获取code
                                required[0] = true;
                            }
                            seconds[0]--;
                            if (seconds[0] > 0) {
                                get_code.setText(seconds[0] +"后获取");
                            } else {
                                timer.cancel();
                                get_code.setText("获取验证码");
                                get_code.setEnabled(true);
                            }
                        });
                    }
                };
                timer.schedule(task,1000,1000);
            }
        });

        login_button.setOnClickListener(v->{
            if (!input_phone.getText().toString().equals("") && !input_code.getText().toString().equals("")) {
                toast("登录应用");
                //登录后台接口
                //缓存用户数据
                //下载用户头像并获取头像文件路径
            }
        });

        psw_login.setOnClickListener(v->{
            Intent intent = new Intent(this, PswLoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_login;
    }
}