package com.hme.turman.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Cache;
import com.hme.turman.CacheData;
import com.hme.turman.Contents;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.ui.entity.event.CustomEvent;
import com.hme.turman.utils.glide.GlideLoad;
import com.hme.turman.widgets.FormItemLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by diaoqf on 2016/11/14.
 */

public class UserInfoEditActivity extends BaseActivity {
    @BindView(R.id.user_portrait_upload)
    RelativeLayout user_portrait_upload;
    @BindView(R.id.user_portrait)
    CircleImageView user_portrait;

    @BindView(R.id.menu_user_name)
    FormItemLayout menu_user_name;
    @BindView(R.id.menu_user_voice)
    FormItemLayout menu_user_voice;
    @BindView(R.id.menu_user_age)
    FormItemLayout menu_user_age;


    private String photoPath;

    @Override
    protected String getPageTitle() {
        return "资料编辑";
    }

    @Override
    protected boolean showRightMenu() {
        return true;
    }

    @Override
    protected String getRightMenuTitle() {
        return "提交";
    }

    @Override
    protected View.OnClickListener getRightMenuListener() {
        return v->{
            //提交处理
        };
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        user_portrait_upload.setOnClickListener(v->{
            PhotoPicker.builder()
                    .setShowCamera(true)
                    .setPhotoCount(1)
                    .start(this);
        });

        user_portrait.setOnClickListener(v->{
            Intent intent = new Intent(this, PhotoViewActivity.class);
            intent.putExtra(PhotoViewActivity.IMAGE_URL,new ArrayList(){{add(CacheData.getDefault().getUserPortrait());}});
            startActivity(intent);
        });

        menu_user_name.setOnItemClickListener(v->{
            Intent intent = new Intent(this, NameExchangeActivity.class);
            startActivity(intent);
        });

        fillUserInfo(0);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_user_info_edit;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            if (photos != null) {
                photoPath = photos.get(0);
                GlideLoad.init(this)
                        .load(photoPath)
                        .into(user_portrait);
            }
        }
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CustomEvent event) {
        if (event.getType().equals(CustomEvent.UPDATE_USER_INFO)) {
            fillUserInfo(2);
        }
    }

    private void fillUserInfo(int code) {
        if (code == 0 || code == 1) {
            GlideLoad.init(this)
                    .load(CacheData.getDefault().getUserPortrait())
                    .into(user_portrait);
        }
        if (code == 0 || code == 2) {
            menu_user_name.setHint(CacheData.getDefault().getUserNickName());
        }
        if (code == 0 || code == 3) {
            menu_user_age.setHint(CacheData.getDefault().getUserAge());
        }
    }
}
