package com.autoliba.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.activities.AddDetails;
import com.autoliba.model.HomeModel;
import com.autoliba.model.ProfileDataModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ma7MouD on 11/28/2018.
 */

public class AutoShowAdsAdapter extends RecyclerView.Adapter<AutoShowAdsAdapter.ViewHolder> {

    Context context;
    List<HomeModel.Message> list;

    public AutoShowAdsAdapter(Context context, List<HomeModel.Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load("http://libyagt.com/users/images/" + list.get(position).getImage()).into(holder.imageView);
        holder.item_name.setText(list.get(position).getTitle());
        holder.card_item.setTag(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView item_name;
        CardView card_item;

        public ViewHolder(View itemView) {
            super(itemView);
            card_item = itemView.findViewById(R.id.home_item_card_id);
            imageView = itemView.findViewById(R.id.item_image_id);
            item_name = itemView.findViewById(R.id.item_name_txt);
            card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos_clicked = (int) view.getTag();
            Gson gson = new Gson();
            String ss = gson.toJson(list.get(pos_clicked));
//            Log.e("item_data: ", ss + "");
            Intent intent = new Intent(context, AddDetails.class);
            intent.putExtra("item_data", list.get(pos_clicked));
            context.startActivity(intent);
        }
    }
}
