package com.autoliba.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.R;
import com.autoliba.model.AutoNewsModel;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;

public class AutoNewDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout sliderLayout;
    ArrayList<String> layouts = new ArrayList<>();
    TextView autoNew_title, autoNew_desc, back;
    ProgressBar progressBar;

    AutoNewsModel.Message new_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_new_details);

        if (getIntent().getExtras() != null) {
            new_data = getIntent().getExtras().getParcelable("autoNew_data");
        }
        back = findViewById(R.id.new_details_back_id);
        progressBar = findViewById(R.id.autoNew_details_progress);
        sliderLayout = findViewById(R.id.new_details_banner_slider);
        autoNew_title = findViewById(R.id.autoNew_title_txt_id);
        autoNew_desc = findViewById(R.id.autoNew_desc_txt_id);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // set Images to Slider Layout...
        layouts.add("http://libyagt.com/users/images/" + new_data.getImage());
        for (String name : layouts) {
            TextSliderView textSliderView = new TextSliderView(AutoNewDetails.this);
            // initialize a SliderLayout
            textSliderView.image(name)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(AutoNewDetails.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(AutoNewDetails.this);

        autoNew_title.setText(Html.fromHtml(new_data.getTitle()));
        autoNew_desc.setText(Html.fromHtml(new_data.getArticle()));

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
