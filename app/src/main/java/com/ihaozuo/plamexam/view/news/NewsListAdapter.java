package com.ihaozuo.plamexam.view.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.common.dialog.ShareDialog;
import com.ihaozuo.plamexam.framework.SysConfig;
import com.ihaozuo.plamexam.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.http.HEAD;

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
        newsList.clear();
        newsList.addAll(list);
        notifyDataSetChanged();
    }

    public void loadMoreList(List<NewsBean> list) {
        newsList.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_newslist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final NewsBean newsEntity = newsList.get(position);

        ResizeOptions resizeOptions = new ResizeOptions(250, 170);
        ImageLoadUtils.getInstance().display(newsEntity.imgFormat ,holder.imgNewslist, R.drawable.banner,resizeOptions);
        holder.tvCommiton.setText(newsEntity.timeFormat);
        holder.tvTitle.setText(newsEntity.title);
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String linkUrl = SysConfig.NEWS_DETAIL_URL[0]+ newsEntity.id + SysConfig.NEWS_DETAIL_URL[1];
                ShareDialog shareDialog = new ShareDialog(mContext,R.style.draw_dialog,newsEntity.title, linkUrl);
                shareDialog.show();

            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                String url = SysConfig.NEWS_DETAIL_URL[0] + newsEntity.id + SysConfig.NEWS_DETAIL_URL[1];
                intent.putExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY, url);
                mContext.startActivity(intent);
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
