package com.hme.turman.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;
import com.hme.turman.ui.entity.TabEntity;
import com.hme.turman.ui.fragment.FindFragment;
import com.hme.turman.ui.fragment.FlowMeFragment;
import com.hme.turman.ui.fragment.HelpMeFragment;
import com.hme.turman.ui.fragment.MineFragment;
import com.hme.turman.ui.fragment.MyMessageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class MainActivity extends BaseActivity {
    public static final int TAB_FLOW_ME = 0;
    public static final int TAB_HELP_ME = 1;
    public static final int TAB_FIND = 2;
    public static final int TAB_MY_MESSAGE = 3;
    public static final int TAB_MINE = 4;


    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.bottomTab)
    CommonTabLayout bottomTab;

    List<Fragment> mFragments;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"跟我走","帮帮我", "发现", "消息", "我的"};
    private int[] mIconSelectIds = {
            R.drawable.sel_main_tab_one_sel, R.drawable.sel_main_tab_two_sel, R.drawable.sel_main_tab_three_sel,
            R.drawable.sel_main_tab_four_sel,R.drawable.sel_main_tab_five_sel};
    private int[] mIconUnselectIds ={
            R.drawable.sel_main_tab_one_nor, R.drawable.sel_main_tab_two_nor, R.drawable.sel_main_tab_three_nor,
            R.drawable.sel_main_tab_four_nor,R.drawable.sel_main_tab_five_nor};

    private FragmentManager fragmentManager;

    @Override
    protected void init(Bundle savedInstanceState) {
        //fragment init
        initFragments();
        //tab init
        initBottomTab();
    }

    /**
     * 初始化fragment
     */
    private void initFragments() {
        mFragments = new ArrayList<>();
        FlowMeFragment flowMeFragment = new FlowMeFragment();
        mFragments.add(flowMeFragment);
        HelpMeFragment helpMeFragment = new HelpMeFragment();
        mFragments.add(helpMeFragment);
        FindFragment findFragment = new FindFragment();
        mFragments.add(findFragment);
        MyMessageFragment myMessageFragment = new MyMessageFragment();
        mFragments.add(myMessageFragment);
        MineFragment mineFragment = new MineFragment();
        mFragments.add(mineFragment);
    }

    /**
     * 底部切换初始化
     */
    private void initBottomTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        bottomTab.setTabData(mTabEntities);
        bottomTab.showDot(3);
        bottomTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                exchangeFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        exchangeFragment(TAB_FIND);
        bottomTab.setCurrentTab(TAB_FIND);
    }

    /**
     * 切换fragment
     * @param position
     */
    private void exchangeFragment(int position) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (position) {
            case TAB_FLOW_ME:
                ft.replace(R.id.frameLayout,mFragments.get(TAB_FLOW_ME));
                break;
            case TAB_HELP_ME:
                ft.replace(R.id.frameLayout,mFragments.get(TAB_HELP_ME));
                break;
            case TAB_FIND:
                ft.replace(R.id.frameLayout,mFragments.get(TAB_FIND));
                break;
            case TAB_MY_MESSAGE:
                ft.replace(R.id.frameLayout,mFragments.get(TAB_MY_MESSAGE));
                break;
            case TAB_MINE:
                ft.replace(R.id.frameLayout,mFragments.get(TAB_MINE));
                break;
        }
        ft.commit();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_main;
    }
}

































