package com.ihaozuo.plamexam.view.mine.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.common.Banner.XBanner;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends BaseFragment {


    @Bind(R.id.banner)
    XBanner banner;
    private View rootView;
    private List<Integer> idList;

    public AboutUsFragment() {
        idList = new ArrayList<Integer>();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.aboutus_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.about_us));
        ButterKnife.bind(this, rootView);

        idList.add(R.drawable.banner_about1);
        idList.add(R.drawable.banner_about2);
        idList.add(R.drawable.banner_about3);

        banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, final SimpleDraweeView view, int position) {
                ImageLoadUtils.getInstance().display("res://com.ihaozuo.plamexam/"+idList.get(position), view);
            }
        });
        banner.setData(idList);
        banner.stopAutoPlay();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
