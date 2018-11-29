package com.autoliba.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.activities.AutoNewDetails;
import com.autoliba.model.AutoNewsModel;
import com.autoliba.model.AutoShowsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ma7MouD on 11/15/2018.
 */

public class AutoNewsAdapter extends RecyclerView.Adapter<AutoNewsAdapter.ViewHolder> {

    private Context context;
    private List<AutoNewsModel.Message> list;

    public AutoNewsAdapter(Context context, List<AutoNewsModel.Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AutoNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.auto_news_item, parent, false);
        return new AutoNewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AutoNewsAdapter.ViewHolder holder, int position) {
        Picasso.with(context).load("http://libyagt.com/users/images/" + list.get(position).getImage()).into(holder.imageView);
        holder.item_title.setText(Html.fromHtml(list.get(position).getTitle()));
        holder.item_details.setText(Html.fromHtml(list.get(position).getArticle()));
        holder.cardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView item_title, item_details;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_item_id);
            imageView = itemView.findViewById(R.id.autoNew_img_item_id);
            item_title = itemView.findViewById(R.id.new_title_txt_id);
            item_details = itemView.findViewById(R.id.new_details_txt_id);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            Intent intent = new Intent(context, AutoNewDetails.class);
            intent.putExtra("autoNew_data", list.get(pos));
            context.startActivity(intent);
        }
    }
}
