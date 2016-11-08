package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.hme.turman.R;
import com.hme.turman.base.BaseFragment;
import com.hme.turman.widgets.FormItemLayout;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class MyMessageFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_menu)
    TextView title_menu;

    @BindView(R.id.my_conversation)
    FormItemLayout my_conversation;
    @BindView(R.id.my_broadcast)
    FormItemLayout my_broadcast;
    @BindView(R.id.my_active)
    FormItemLayout my_active;
    @BindView(R.id.my_notice)
    FormItemLayout my_notice;

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("消息");
        my_conversation.setOnItemClickListener(v->{
            if (RongIM.getInstance() != null) {
                RongIM.getInstance().startConversationList(getActivity());
            }
        });

        my_broadcast.setOnItemClickListener(v->{

        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_my_message;
    }
}
