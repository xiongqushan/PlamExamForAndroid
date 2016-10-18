package com.ihaozuo.plamexam.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.HZUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BannerFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_BANNERFRAGMENT";
    private int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner5, R.drawable.banner4, R.drawable.banner5};

    private View view;

    public BannerFragment() {
    }

    private int position;


    public static BannerFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, position);
        BannerFragment fragment = new BannerFragment();
        fragment.setArguments(args);
        return fragment;
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
            ImageView img = (ImageView) view.findViewById(R.id.imgBanner);
            img.setImageResource(images[position]);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (HZUtils.isFastDoubleClick()) {
                        return;
                    }
                    Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }

}
