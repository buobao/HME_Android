package com.hme.turman.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.utils.UiUtil;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/11/1.
 */

public class PswLoginActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.input_phone)
    EditText input_phone;
    @BindView(R.id.input_psw)
    EditText input_psw;
    @BindView(R.id.login_button)
    TextView login_button;
    @BindView(R.id.code_login)
    TextView code_login;

    @BindView(R.id.show_psw)
    LinearLayout show_psw;

    private boolean showPassword;

    @Override
    protected void init(Bundle savedInstanceState) {
        showPassword = false;
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());
        title.setText("密码登录");

        login_button.setOnClickListener(v->{
            if (UiUtil.checkPhone(input_phone.getText().toString()) && !input_psw.getText().toString().equals("")) {
                toast("登录应用");
                //登录后台接口
                //缓存用户数据
                //下载用户头像并获取头像文件路径
            }
        });

        show_psw.setOnClickListener(v->{
            if (input_psw.getText().toString().length() > 0) {
                if (!showPassword) {
                    input_psw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    input_psw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                input_psw.setSelection(input_psw.getText().toString().length());
                showPassword = !showPassword;
            }
        });

        code_login.setOnClickListener(v->{
            UiUtil.goLogin(this);
            finish();
        });

        login_button.setOnClickListener(v->{
            if (!input_phone.getText().toString().equals("") && !input_psw.getText().toString().equals("")) {
                toast("登录应用");
                //登录后台接口
                //缓存用户数据
                //下载用户头像并获取头像文件路径
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_psw_login;
    }
}
