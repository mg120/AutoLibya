package com.autoliba.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.R;
import com.autoliba.adapter.CommentsAdapter;
import com.autoliba.model.AdDetailsModel;
import com.autoliba.model.AddNewCommentModel;
import com.autoliba.model.HomeModel;
import com.autoliba.model.ProfileDataModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDetails extends AppCompatActivity implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    HomeModel.Message item_data;
    TextView details_back, no_available_comments, annonce_txtV, phone_txtV, email_txtV, priceState_txtV,
            product_country_txtV, car_type_txtV, modal_txtV, add_comment;

    ProgressBar progressBar;
    LinearLayout details_layout;
    EditText comment_ed;
    RecyclerView comments_recycler;
    CommentsAdapter commentsAdapter;
    Button contact_annonce_btn;
    SliderLayout sliderLayout;
    ArrayList<String> layouts = new ArrayList<>();

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        if (getIntent().getExtras() != null) {
            item_data = getIntent().getExtras().getParcelable("item_data");
        }
        progressBar = findViewById(R.id.details_progress);
        details_layout = findViewById(R.id.description_layout_id);
        sliderLayout = findViewById(R.id.banner_slider);
        product_country_txtV = findViewById(R.id.product_country_txt_id);
        car_type_txtV = findViewById(R.id.car_type_txt_id);
        modal_txtV = findViewById(R.id.modal_txtV_id);
        annonce_txtV = findViewById(R.id.annonce_txtV_id);
        phone_txtV = findViewById(R.id.phone_txtV_id);
        email_txtV = findViewById(R.id.email_txtV_id);
        priceState_txtV = findViewById(R.id.priceState_txtV_id);
        add_comment = findViewById(R.id.send_comment_id);
        comment_ed = findViewById(R.id.comment_ed_id);

        no_available_comments = findViewById(R.id.no_available_comments);
        comments_recycler = findViewById(R.id.comments_recycler_id);
        details_back = findViewById(R.id.add_detials_txt_id);
        contact_annonce_btn = findViewById(R.id.contact_annonce_btn_id);

        getAdDetails(item_data.getId());

        /// Back Button pressed
        details_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /// Contact Annoncer Button
        contact_annonce_btn.setOnClickListener(this);
        // Add Comment
        add_comment.setOnClickListener(this);
    }

    private void getAdDetails(Integer id) {

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<AdDetailsModel> call = apiService.ad_itemDetails(id + "");
        call.enqueue(new Callback<AdDetailsModel>() {
            @Override
            public void onResponse(Call<AdDetailsModel> call, Response<AdDetailsModel> response) {

                progressBar.setVisibility(View.GONE);
                details_layout.setVisibility(View.VISIBLE);

                AdDetailsModel adDetailsModel = response.body();
                AdDetailsModel.Message message = adDetailsModel.getMessage();

                product_country_txtV.setText(message.getCityname());
                car_type_txtV.setText(message.getBrandname());
                modal_txtV.setText(message.getModal());
                annonce_txtV.setText(message.getMembername());
                phone_txtV.setText(message.getPhone());
                email_txtV.setText(message.getEmail());
                if (item_data.getNeogation().equals("0")) {
                    priceState_txtV.setText("السعر غير قابل للتفاوض");
                } else if (item_data.getNeogation().equals("1")) {
                    priceState_txtV.setText("السعر قابل للتفاوض");
                }

                // set Images to Slider Layout...
                layouts.add("http://libyagt.com/users/images/" + message.getImage());
                for (String name : layouts) {
                    TextSliderView textSliderView = new TextSliderView(AddDetails.this);
                    // initialize a SliderLayout
                    textSliderView.image(name)
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(AddDetails.this);

                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    sliderLayout.addSlider(textSliderView);
                }
                sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(4000);
                sliderLayout.addOnPageChangeListener(AddDetails.this);


                // ----------- For item Comments ----------
                List<AdDetailsModel.Comment> commenst_list = message.getComments();

                if (commenst_list.size() > 0) {
                    GridLayoutManager layoutManager = new GridLayoutManager(AddDetails.this, 1);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    comments_recycler.setLayoutManager(layoutManager);
                    comments_recycler.setHasFixedSize(true);

                    commentsAdapter = new CommentsAdapter(AddDetails.this, commenst_list);
                    comments_recycler.setAdapter(commentsAdapter);
                } else {
                    comments_recycler.setVisibility(View.GONE);
                    no_available_comments.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AdDetailsModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.contact_annonce_btn_id:
                Intent intent = new Intent(AddDetails.this, Messages.class);
                // put Data to Intent ...
//                intent.putExtra("ad_data", item_data);
                intent.putExtra("receiver_Id", String.valueOf(item_data.getUserId()));
//                intent.putExtra("receiver_name", item_data.get);
                startActivity(intent);
                break;

            case R.id.send_comment_id:
                String comment = comment_ed.getText().toString().trim();
                if (TextUtils.isEmpty(comment)) {
                    comment_ed.setError("برجاء كتابة التعليق اولا !");
                    comment_ed.requestFocus();
                    return;
                }
                apiService = ApiClient.getClient().create(ApiService.class);
                Call<AddNewCommentModel> call = apiService.add_comment(MainActivity.user_data.getId() + "", item_data.getId() + "", MainActivity.user_data.getFirstname() + MainActivity.user_data.getLastname(), MainActivity.user_data.getEmail(), MainActivity.user_data.getImage(), comment);
                call.enqueue(new Callback<AddNewCommentModel>() {
                    @Override
                    public void onResponse(Call<AddNewCommentModel> call, Response<AddNewCommentModel> response) {
                        AddNewCommentModel addNewCommentModel = response.body();
                        boolean success = addNewCommentModel.getSuccess();
                        if (success) {
                            Toasty.success(AddDetails.this, "تم ارسال تعليقك بنجاح", 1500).show();
                            comment_ed.setText("");
                        } else {
                            Toasty.error(AddDetails.this, getString(R.string.error_occured), 1500).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddNewCommentModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
