package com.zhouwei.androidtrainingsimples.bottom_navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.zhouwei.androidtrainingsimples.R;

import static com.zhouwei.androidtrainingsimples.bottom_navigation.DataGenerator.mTabRes;
import static com.zhouwei.androidtrainingsimples.bottom_navigation.DataGenerator.mTabTitle;

/**
 * Created by zhouwei on 17/4/23.
 */

public class FragmentTabHostActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    private Fragment[] mFragments;
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_host_ac_layout);
        mFragments = DataGenerator.getFragments("FragmentTabHost Tab");
        initView();
    }

    private void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        //建立FragmentTabHost与Fragment container的关联
        mTabHost.setup(this, getSupportFragmentManager(), R.id.home_container);
        //注意，监听要设置在添加Tab之前，否则第一次收不到onTabChange回调
        mTabHost.setOnTabChangedListener(this);

        //添加Tab
        for (int i = 0; i < 4; i++) {
            //生成TabSpec
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTabTitle[i]).setIndicator(DataGenerator.getTabView(this, i));
            //通过FragmentTabHost添加的Fragment里面收不到通过setArgment()传递的参数，要传递参数，需要在添加Tab的时候通过Bundle来传参
            Bundle bundle = new Bundle();
            bundle.putString("from", "FragmentTabHost Tab");
            //添加Tab到TabHost，并绑定Fragment
            mTabHost.addTab(tabSpec, mFragments[i].getClass(), bundle);
        }

        //去掉Tab之间的分割线,默认在每个Tab之间有一根竖直的分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.setCurrentTab(0);
    }


    @Override
    public void onTabChanged(String tabId) {
        updateTabState();
    }

    /**
     * 更新Tab的状态
     **/
    private void updateTabState() {
        TabWidget tabWidget = mTabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getTabCount(); i++) {
            View view = tabWidget.getChildTabViewAt(i);
            ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
            TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
            if (i == mTabHost.getCurrentTab()) {
                tabIcon.setImageResource(DataGenerator.mTabResPressed[i]);
                tabText.setTextColor(getResources().getColor(android.R.color.black));
            } else {
                tabIcon.setImageResource(mTabRes[i]);
                tabText.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
    }
}
