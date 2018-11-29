package com.autoliba.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.activities.AutoShowAds;
import com.autoliba.model.AutoShowsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ma7MouD on 11/13/2018.
 */

public class AutoShowsAdapter extends RecyclerView.Adapter<AutoShowsAdapter.ViewHolder> {

    private Context context;
    private List<AutoShowsModel.Message> list;

    public AutoShowsAdapter(Context context, List<AutoShowsModel.Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AutoShowsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.autoshow_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AutoShowsAdapter.ViewHolder holder, int position) {
        Picasso.with(context).load("http://libyagt.com/users/images/" + list.get(position).getImage()).into(holder.imageView);
        holder.item_Name_txt.setText(list.get(position).getFirstname());
        holder.autoShow_nickName_txt.setText(list.get(position).getLastname());
        holder.autoShow_phone_txt.setText(list.get(position).getPhone());
        holder.autoShow_address_txt.setText(list.get(position).getAddress());
        holder.cardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cardView ;
        ImageView imageView;
        TextView item_Name_txt, autoShow_nickName_txt, autoShow_phone_txt, autoShow_address_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.autoShow_img_item_id);
            item_Name_txt = itemView.findViewById(R.id.item_Name_txt_id);
            autoShow_nickName_txt = itemView.findViewById(R.id.item_nickName_txt_id);
            autoShow_phone_txt = itemView.findViewById(R.id.item_phone_txt_id);
            autoShow_address_txt = itemView.findViewById(R.id.item_address_txt_id);
            cardView = itemView.findViewById(R.id.auto_Show_card_item_id);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            Intent intent = new Intent(context, AutoShowAds.class);
            intent.putExtra("autoShow_id", list.get(pos).getId());
            context.startActivity(intent);
        }
    }
}
