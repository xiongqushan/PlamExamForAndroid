package com.ihaozuo.plamexam.view.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.common.dialog.ShareDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hzguest3 on 2016/10/19.
 */
public class NewsListAdapter extends SimpleBaseAdapter {

    private List<NewsBean> newsList;
    private Context mContext;
    private LayoutInflater mInflater;


    public NewsListAdapter(Context context) {
        newsList = new ArrayList();
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    public void refreshList(List<NewsBean> list) {
        newsList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return 4;
//        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_newslist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShareDialog shareDialog = new ShareDialog(mContext,R.style.draw_dialog);
                shareDialog.show();

            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.img_newslist)
        SimpleDraweeView imgNewslist;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_commiton)
        TextView tvCommiton;
        @Bind(R.id.btn_share)
        TextView btnShare;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
