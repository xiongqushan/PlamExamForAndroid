package com.ihaozuo.plamexam.view.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class GuideActivity extends BaseActivity {

    private List<Integer> idList;
    private Boolean clickAble ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.guide_act);
        ButterKnife.bind(this);
        idList = new ArrayList<>();
        idList.add(R.drawable.banner_about1);
        idList.add(R.drawable.banner_about2);
        idList.add(R.drawable.banner_about3);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PhotoPreviewAdapter photoPreviewAdapter = new PhotoPreviewAdapter(fragmentManager, idList);
        ViewPager vp = (ViewPager) findViewById(R.id.vp_photopreview);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        vp.setAdapter(photoPreviewAdapter);
        indicator.setViewPager(vp);

    }

    class PhotoPreviewAdapter extends FragmentPagerAdapter {
        private List<GuideFragment> feedbackList = new ArrayList<GuideFragment>();
        private GuideFragment mGuideFragment;

        public PhotoPreviewAdapter(FragmentManager fm, List<Integer> PhotoUrlList) {
            super(fm);
            for (int i = 0; i < PhotoUrlList.size(); i++) {
                if (i==PhotoUrlList.size()-1){
                    clickAble = true;
                }else {
                    clickAble = false;
                }
                mGuideFragment = GuideFragment.newInstance(PhotoUrlList.get(i),clickAble);
                feedbackList.add(mGuideFragment);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return feedbackList.get(position);
        }

        @Override
        public int getCount() {
            return feedbackList.size();
        }

    }

}
