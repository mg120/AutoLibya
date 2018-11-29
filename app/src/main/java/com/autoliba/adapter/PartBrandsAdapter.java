package com.autoliba.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.activities.PartBrandsDetails;
import com.autoliba.model.PartBrandsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ma7MouD on 11/18/2018.
 */

public class PartBrandsAdapter extends RecyclerView.Adapter<PartBrandsAdapter.ViewHolder> {

    Context context;
    List<PartBrandsModel.Part> list;

    public PartBrandsAdapter(Context context, List<PartBrandsModel.Part> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PartBrandsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.part_brands_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartBrandsAdapter.ViewHolder holder, int position) {

        Picasso.with(context).load("http://libyagt.com/users/images/" + list.get(position).getImage()).into(holder.imageView);
        holder.textView.setText(list.get(position).getName());
        holder.card_Item.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        CardView card_Item;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.part_brand_img_id);
            textView = itemView.findViewById(R.id.part_brand_txt_id);
            card_Item = itemView.findViewById(R.id.part_brand_layout_id);
            card_Item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            Intent intent = new Intent(context, PartBrandsDetails.class);
            intent.putParcelableArrayListExtra("part_brand_item", (ArrayList<? extends Parcelable>) list.get(pos).getPartinfo());
            context.startActivity(intent);
        }
    }
}
