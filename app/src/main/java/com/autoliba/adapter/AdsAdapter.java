package com.autoliba.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.R;
import com.autoliba.activities.MainActivity;
import com.autoliba.activities.UpdateAd;
import com.autoliba.fragments.ProfileFragment;
import com.autoliba.model.DeleteAdModel;
import com.autoliba.model.ProfileDataModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ma7MouD on 11/7/2018.
 */

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.ViewHolder> {

    Context context;
    List<ProfileDataModel.Myad> ads_list;
    ApiService apiService;

    public AdsAdapter(Context context, List<ProfileDataModel.Myad> ads_list) {
        this.context = context;
        this.ads_list = ads_list;
    }

    @Override
    public AdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_ads_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdsAdapter.ViewHolder holder, final int position) {
        Picasso.with(context).load("http://libyagt.com/users/images/" + ads_list.get(position).getImage()).into(holder.imageView);
        holder.new_title_txt.setText(ads_list.get(position).getTitle());
        holder.new_model_txt.setText(ads_list.get(position).getModal());
        holder.new_car_price_txt.setText(ads_list.get(position).getPrice());

        holder.edit_ad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String data_obj = gson.toJson(ads_list.get(position));
                Log.e("data_obj: ", data_obj);

                Intent intent = new Intent(context, UpdateAd.class);
                intent.putExtra("Ad_data", ads_list.get(position));
                context.startActivity(intent);
            }
        });

        holder.del_ad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiService = ApiClient.getClient().create(ApiService.class);
                Call<DeleteAdModel> call = apiService.delete_ad(ads_list.get(position).getId() + "", MainActivity.user_data.getId() + "");
                call.enqueue(new Callback<DeleteAdModel>() {
                    @Override
                    public void onResponse(Call<DeleteAdModel> call, Response<DeleteAdModel> response) {
                        DeleteAdModel deleteAdModel = response.body();
                        boolean success = deleteAdModel.getSuccess();
                        if (success) {
                            ads_list.remove(position);
                            AdsAdapter.this.notifyDataSetChanged();
                            Toasty.success(context, "تم حذف المنتج بنجاح", 1500).show();
                            if (ads_list.size() == 0) {
                                ProfileFragment.ads_recyclerView.setVisibility(View.GONE);
                                ProfileFragment.no_available_ads_txt.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteAdModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return ads_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button edit_ad_btn, del_ad_btn;
        ImageView imageView;
        TextView new_title_txt, new_model_txt, new_car_price_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.new_img_item_id);
            new_title_txt = itemView.findViewById(R.id.new_title_txt_id);
            new_model_txt = itemView.findViewById(R.id.new_car_model_txt_id);
            new_car_price_txt = itemView.findViewById(R.id.new_car_price_txt_id);
            edit_ad_btn = itemView.findViewById(R.id.edit_ads_btn_id);
            del_ad_btn = itemView.findViewById(R.id.delete_ads_btn_id);
        }
    }
}
