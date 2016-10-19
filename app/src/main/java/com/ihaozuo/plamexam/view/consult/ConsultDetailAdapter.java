/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ihaozuo.plamexam.view.consult;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.bean.DoctorInfoBean;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConsultDetailAdapter extends Adapter<RecyclerView.ViewHolder> {

    private List<ConsultDetailBean> list;
    private Context mContext;
    private UserBean mUserInfo;
    private DoctorInfoBean mDoctorInfo;

    public ConsultDetailAdapter() {
        this.list = new ArrayList<>();
        this.mDoctorInfo = new DoctorInfoBean();
    }

    public void refreshList(Context context, List<ConsultDetailBean> list, DoctorInfoBean doctorInfoBean) {
        this.list.clear();
        this.list.addAll(list);
        this.mContext = context;
        this.mUserInfo = UserManager.getInstance().getUserInfo();
        this.mDoctorInfo = doctorInfoBean;
        notifyDataSetChanged();
    }

    public void addReply(ConsultDetailBean replyContent) {
        this.list.add(replyContent);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return this.list.get(position).SourceType;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;
        switch (viewType) {
            case 1://客户回复
                view = layoutInflater.inflate(R.layout.item_right_consult_datail, parent, false);
                return new ConsultRightHolder(view);

            case 2://健管师内容
                view = layoutInflater.inflate(R.layout.item_left_consult_detail, parent, false);
                return new ConsultLeftHolder(view);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ConsultDetailBean consultDetailEntity = list.get(position);
        if (null == consultDetailEntity){
            return;
        }
        if (holder instanceof ConsultLeftHolder) {
            bindLeftItem(consultDetailEntity, (ConsultLeftHolder) holder);
        } else if (holder instanceof ConsultRightHolder){
            bindRightItem(consultDetailEntity, (ConsultRightHolder) holder);
        }

    }


    public void bindLeftItem(ConsultDetailBean entity,ConsultLeftHolder holder){
        if (null != mDoctorInfo.ImageSrc){
            ImageLoadUtils.getInstance(mContext).display(mDoctorInfo.ImageSrc,holder.draweeConsultItemPhoto);
        }
        holder.txtConsultCommiton.setText(entity.getDate());
        holder.txtConsultItem.setText(entity.Content);
    }

    public void bindRightItem(ConsultDetailBean entity,ConsultRightHolder holder){
        holder.txtConsultCommiton.setText(entity.getDate());
        holder.txtConsultItem.setText(entity.Content);
    };

    public class ConsultLeftHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_consult_commiton)
        TextView txtConsultCommiton;
        @Bind(R.id.drawee_consult_item_photo)
        SimpleDraweeView draweeConsultItemPhoto;
        @Bind(R.id.txt_consult_item)
        TextView txtConsultItem;

        public ConsultLeftHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


    public class ConsultRightHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_consult_commiton)
        TextView txtConsultCommiton;
        @Bind(R.id.drawee_consult_item_photo)
        SimpleDraweeView draweeConsultItemPhoto;
        @Bind(R.id.txt_consult_item)
        TextView txtConsultItem;

        public ConsultRightHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
