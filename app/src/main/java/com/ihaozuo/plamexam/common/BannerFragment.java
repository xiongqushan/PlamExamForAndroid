package com.ihaozuo.plamexam.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.view.news.NewsDetailActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BannerFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_BANNERFRAGMENT";
    private static List<BannerBean> dataList;
    private int position;

    private View view;

    public BannerFragment() {
    }

//    public BannerFragment(List<BannerBean> bannerList, int position) {
//        dataList = bannerList;
//        this.position = position;
//    }


    public static BannerFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, position);
        BannerFragment fragment = new BannerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public static BannerFragment newInstance(List<BannerBean> bannerList, int position) {
        dataList = bannerList;
        return newInstance(position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_PAGE, 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.banner_frag, container,
                    false);
            SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.imgBanner);
//            if (dataList == null || dataList.size() == 0) {
//                img.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (HZUtils.isFastDoubleClick()) {
//                            return;
//                        }
//                        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//                        intent.putExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY, getString(R.string.url_default));
//                        startActivity(intent);
//                    }
//                });
//            } else {
                ImageLoadUtils.getInstance().display(dataList.get(position).ImageUrl, img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (HZUtils.isFastDoubleClick()) {
                            return;
                        }
                        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                        intent.putExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY, dataList.get(position));
//                        intent.putExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY, dataList.get(position).LinkUrl);
                        startActivity(intent);

                    }
                });
//            }

        }
        return view;
    }


}
