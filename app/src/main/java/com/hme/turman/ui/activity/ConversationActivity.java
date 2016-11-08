package com.hme.turman.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.api.ApiHelper;
import com.hme.turman.base.BaseActivity;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by diaoqf on 2016/11/8.
 */

public class ConversationActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;

    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    protected void init(Bundle savedInstanceState) {
        mTargetId = getIntent().getData().getQueryParameter("targetId");

        title.setText(getIntent().getData().getQueryParameter("title"));
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());

        addTask(ApiHelper.getUserOnlineStatus(mTargetId).subscribe(result->{
            Drawable drawable= getResources().getDrawable(R.drawable.sel_ic_offline);
            if (result != null && result.getStatus().equals("1")) {
                drawable= getResources().getDrawable(R.drawable.sel_ic_online);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            title.setCompoundDrawables(null,null,drawable,null);
        },throwable -> throwable.printStackTrace()));
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_conversation;
    }
}
