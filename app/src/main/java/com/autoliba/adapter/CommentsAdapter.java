package com.autoliba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.model.AdDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ma7MouD on 11/14/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Context context;
    List<AdDetailsModel.Comment> list;

    public CommentsAdapter(Context context, List<AdDetailsModel.Comment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comments_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.ViewHolder holder, int position) {

        Picasso.with(context).load("http://libyagt.com/users/images/" + list.get(position).getImage()).into(holder.comment_user_img);
        holder.comment_txt.setText(list.get(position).getComment());
        holder.comment_time_txt.setText(list.get(position).getCreatedDate());
        holder.comment_userName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView comment_userName, comment_time_txt, comment_txt;
        ImageView comment_user_img;

        public ViewHolder(View itemView) {
            super(itemView);
            comment_user_img = itemView.findViewById(R.id.comment_user_img_id);
            comment_userName = itemView.findViewById(R.id.comment_userName_txt_id);
            comment_time_txt = itemView.findViewById(R.id.comment_left_time_id);
            comment_txt = itemView.findViewById(R.id.comment_txt_id);
        }
    }
}
