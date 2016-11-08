package com.hme.turman.ui.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.hme.turman.R;
import com.hme.turman.api.ApiHelper;
import com.hme.turman.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by diaoqf on 2016/11/8.
 */

public class ConversationActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.input_status)
    TextView input_status;

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

    private MyHandler mHandler;

    @Override
    protected void init(Bundle savedInstanceState) {
        mHandler  = new MyHandler(this);
        mTargetId = getIntent().getData().getQueryParameter("targetId");

        title.setText(getIntent().getData().getQueryParameter("title"));
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->{
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(back.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            onBackPressed();});

        addTask(ApiHelper.getUserOnlineStatus(mTargetId).subscribe(result->{
            Drawable drawable= getResources().getDrawable(R.drawable.sel_ic_offline);
            if (result != null && result.getStatus().equals("1")) {
                drawable= getResources().getDrawable(R.drawable.sel_ic_online);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            title.setCompoundDrawables(null,null,drawable,null);
        },throwable -> throwable.printStackTrace()));


        //输入状态监听
        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
            @Override
            public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
                //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
                if (type.equals(Conversation.ConversationType.PRIVATE) && targetId.equals(mTargetId)) {
                    //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
                    int count = typingStatusSet.size();
                    if (count > 0) {
                        Iterator iterator = typingStatusSet.iterator();
                        TypingStatus status = (TypingStatus) iterator.next();
                        String objectName = status.getTypingContentType();

                        MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
                        MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
                        if (objectName.equals(textTag.value())) {
                            mHandler.sendEmptyMessage(0);
                        } else if (objectName.equals(voiceTag.value())) {
                            mHandler.sendEmptyMessage(1);
                        }
                    } else {
                        mHandler.sendEmptyMessage(2);
                    }
                }
            }
        });

    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_conversation;
    }


    static class MyHandler extends Handler {

        WeakReference<ConversationActivity> mWeakReference;

        public MyHandler(ConversationActivity activity)
        {
            mWeakReference=new WeakReference<ConversationActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final ConversationActivity activity=mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        //正在输入
                        activity.input_status.setVisibility(View.VISIBLE);
                        activity.input_status.setText("对方正在输入...");
                        break;
                    case 1:
                        //正在录音
                        activity.input_status.setVisibility(View.VISIBLE);
                        activity.input_status.setText("对方正在录音...");
                        break;
                    case 2:
                        //非输入状态
                        activity.input_status.setVisibility(View.GONE);
                        break;
                }
            }
        }
    }

}
