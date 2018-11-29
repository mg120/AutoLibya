package com.autoliba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.model.PartBrandsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ma7MouD on 11/18/2018.
 */

public class PartsAdapter extends RecyclerView.Adapter<PartsAdapter.ViewHolder> {

    Context context;
    List<PartBrandsModel.Partinfo> list;

    public PartsAdapter(Context context, List<PartBrandsModel.Partinfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PartsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parts_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartsAdapter.ViewHolder holder, int position) {

        Picasso.with(context).load("http://libyagt.com/users/images/" + list.get(position).getImage()).into(holder.part_image);
        holder.partName.setText(list.get(position).getName());
//        holder.partNum.setText(list.get(position).getParent());
        holder.partPrice.setText(list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView part_image;
        TextView partName, partPrice, partNum;

        public ViewHolder(View itemView) {
            super(itemView);
            part_image = itemView.findViewById(R.id.partImage_id);
            partName = itemView.findViewById(R.id.partName_txt_id);
            partPrice = itemView.findViewById(R.id.partPrice_txt_id);
            partNum = itemView.findViewById(R.id.partNum_txt_id);
        }
    }
}
