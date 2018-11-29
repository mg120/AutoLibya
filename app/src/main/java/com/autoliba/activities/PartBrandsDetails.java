package com.autoliba.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.R;
import com.autoliba.adapter.PartsAdapter;
import com.autoliba.model.PartBrandsModel;

import java.util.ArrayList;
import java.util.List;

public class PartBrandsDetails extends AppCompatActivity {

    RecyclerView parts_recycler;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    TextView no_available_items_txt, back;

    List<PartBrandsModel.Partinfo> details_list = new ArrayList<>();
    PartsAdapter partsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_brands_details);

        if (getIntent().getExtras() != null) {
            details_list = getIntent().getExtras().getParcelableArrayList("part_brand_item");
        }
        back = findViewById(R.id.parts_back_txt_id);
        parts_recycler = findViewById(R.id.parts_details_recycler_id);
        progressBar = findViewById(R.id.parts_details_progress_id);
        no_available_items_txt = findViewById(R.id.parts_details_data_txt_id);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (details_list.size() > 0) {
            progressBar.setVisibility(View.GONE);
            no_available_items_txt.setVisibility(View.GONE);
            parts_recycler.setVisibility(View.VISIBLE);
            parts_recycler.setLayoutManager(new GridLayoutManager(PartBrandsDetails.this, 1));
            partsAdapter = new PartsAdapter(PartBrandsDetails.this, details_list);
            parts_recycler.setAdapter(partsAdapter);

        } else {
            progressBar.setVisibility(View.GONE);
            parts_recycler.setVisibility(View.GONE);
            no_available_items_txt.setVisibility(View.VISIBLE);
        }
    }
}
