package com.hme.turman.ui.fragment;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.hme.turman.R;
import com.hme.turman.base.BaseFragment;
import com.hme.turman.ui.entity.NetworkImageHolderView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class HelpMeFragment extends BaseFragment {
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;

    private String[] images = {
            "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, Arrays.asList(images));

        convenientBanner.setOnItemClickListener(v->{

        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_help_me;
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}
