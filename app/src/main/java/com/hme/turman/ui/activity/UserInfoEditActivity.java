package com.hme.turman.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hme.turman.CacheData;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.utils.glide.GlideLoad;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by diaoqf on 2016/11/14.
 */

public class UserInfoEditActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_menu)
    TextView title_menu;
    @BindView(R.id.user_portrait_upload)
    RelativeLayout user_portrait_upload;
    @BindView(R.id.user_portrait)
    CircleImageView user_portrait;

    private String photoPath;

    @Override
    protected void init(Bundle savedInstanceState) {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v->onBackPressed());
        title.setText("资料编辑");
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText("提交");
        title_menu.setOnClickListener(v->{
            //提交处理
        });

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
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_user_info_edit;
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
}
