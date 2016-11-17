package com.hme.turman.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hme.turman.CacheData;
import com.hme.turman.R;
import com.hme.turman.api.bean.HelpEventBean;
import com.hme.turman.base.ListBaseAdapter;
import com.hme.turman.ui.activity.PhotoViewActivity;
import com.hme.turman.utils.UiUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by lebro on 2016/11/5.
 */

public class HelpEventAdapter extends ListBaseAdapter<HelpEventBean>  {
    public HelpEventAdapter() {
    }

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_event_layout, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //加载头像
        Glide.with(parent.getContext())
                .load(getItem(position).getUserImage())
                .placeholder(R.drawable.default_portrait)
                .error(R.drawable.default_portrait)
                .fitCenter()
                .into(vh.item_head_img);
        vh.item_title_tv.setText(getItem(position).getTitle());
        vh.item_time.setText(getItem(position).getPushDate());
        vh.item_content.setText(getItem(position).getContent());
        vh.item_location_tv.setText(getItem(position).getAddress());
        vh.item_conversation_chat.setOnClickListener(v->{
            if (CacheData.getDefault().isLogin()) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startConversation(parent.getContext(), Conversation.ConversationType.CHATROOM, "9527", "这个是聊天室的标题要很长很长很长很长很长很长");
                }
            } else {
                UiUtil.goLogin(parent.getContext());
            }
        });

        for (int i=0;i<getItem(position).getContentImage().size();i++) {
            Glide.with(parent.getContext())
                    .load(getItem(position).getContentImage().get(i))
                    .placeholder(R.drawable.default_portrait)
                    .error(R.drawable.default_portrait)
                    .fitCenter()
//                    .override(50,50)
                    .into(vh.content_images.get(i));
        }
        vh.item_image_layout.setOnClickListener(v->{
            Intent intent = new Intent(parent.getContext(), PhotoViewActivity.class);
            intent.putStringArrayListExtra(PhotoViewActivity.IMAGE_URL, (ArrayList<String>) getItem(position).getContentImage());
            parent.getContext().startActivity(intent);
        });

        return convertView;
    }

    static class ViewHolder {
        private CircleImageView item_head_img;
        private TextView item_title_tv;
        private TextView item_time;
        private TextView item_content;
        private TextView item_location_tv;
        private ImageView item_conversation_chat;
        private LinearLayout item_image_layout;

        private List<ImageView> content_images;

        public ViewHolder(View view) {
            item_head_img = (CircleImageView) view.findViewById(R.id.item_head_img);
            item_title_tv = (TextView) view.findViewById(R.id.item_title_tv);
            item_time = (TextView) view.findViewById(R.id.item_time);
            item_content = (TextView) view.findViewById(R.id.item_content);
            item_location_tv = (TextView) view.findViewById(R.id.item_location_tv);
            item_conversation_chat = (ImageView) view.findViewById(R.id.item_conversation_chat);
            item_image_layout = (LinearLayout) view.findViewById(R.id.image_layout);
            content_images = new ArrayList() {
                {
                    add(view.findViewById(R.id.item_content_image_1));
                    add(view.findViewById(R.id.item_content_image_2));
                    add(view.findViewById(R.id.item_content_image_3));
                    add(view.findViewById(R.id.item_content_image_4));
                    add(view.findViewById(R.id.item_content_image_5));
                }
            };
        }

    }
}
