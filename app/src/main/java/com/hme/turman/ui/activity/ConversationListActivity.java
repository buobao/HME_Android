package com.hme.turman.ui.activity;

import android.os.Bundle;

import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;


/**
 * Created by diaoqf on 2016/11/8.
 */

public class ConversationListActivity extends BaseActivity {

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_conversation_list;
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected String getPageTitle() {
        return "会话";
    }
}
