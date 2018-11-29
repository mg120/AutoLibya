package com.autoliba.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.adapter.SearchResultsAdapter;
import com.autoliba.model.HomeModel;
import com.autoliba.model.SearchResultModel;
import com.autoliba.model.SettingInfoModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity implements View.OnClickListener {

    Spinner search_city_spinner, search_type_spinner, search_brands_spinner, search_model_spinner;
    Button search_btn;
    EditText max_price_ed, min_price_ed;
    TextView back, no_results_txt;
    ProgressBar progressBar;
    RecyclerView results_recycler;
    SearchResultsAdapter adapter;

    ApiService apiService;

    // For Spinners
    String selected_type, selected_brand, selected_model;
    int type = 3;
    int selected_brand_id, selected_city_id;
    final List<String> models = new ArrayList<>();
    final List<String> brands = new ArrayList<>();
    final List<String> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back = findViewById(R.id.search_back_txt_id);
        search_city_spinner = findViewById(R.id.search_city_spinner);
        search_type_spinner = findViewById(R.id.search_type_spinner);
        search_brands_spinner = findViewById(R.id.search_brands_spinner);
        search_model_spinner = findViewById(R.id.search_model_spinner);
        search_btn = findViewById(R.id.search_btn_id);
        max_price_ed = findViewById(R.id.max_price_ed_id);
        min_price_ed = findViewById(R.id.min_price_ed_id);
        no_results_txt = findViewById(R.id.search_results_data_txt_id);
        results_recycler = findViewById(R.id.search_results_recycler_id);
        progressBar = findViewById(R.id.search_results_progress_id);

        getSettingInfo();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // --------------------------------------------
        search_btn.setOnClickListener(this);
    }

    private void getSettingInfo() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SettingInfoModel> call = apiService.getSettingInfo();
        call.enqueue(new Callback<SettingInfoModel>() {
            @Override
            public void onResponse(Call<SettingInfoModel> call, Response<SettingInfoModel> response) {
                SettingInfoModel settingInfoModel = response.body();
                boolean success = settingInfoModel.getSuccess();
                SettingInfoModel.Message message = settingInfoModel.getMessage();

                if (success) {

                    final List<SettingInfoModel.Modal> modals_list = message.getModals();
                    final List<SettingInfoModel.Count> counts_list = message.getCounts();
                    final List<SettingInfoModel.Type> type_List = message.getTypes();

                    //---------------- For Type Spinner -------------
                    String[] type_arr = getResources().getStringArray(R.array.search_type_arr);
                    final ArrayAdapter<String> states_adapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_dropdown_item_1line, type_arr);
                    search_type_spinner.setAdapter(states_adapter);
                    search_type_spinner.setSelection(0);
                    search_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selected_type = adapterView.getSelectedItem().toString();
                            if (selected_type.equals("سيارة جديدة")) {
                                type = 3;
                            } else if (selected_type.equals("سيارة مستعملة")) {
                                type = 2;
                            }

                            for (int x = 0; x < type_List.size(); x++) {
                                if (type == type_List.get(x).getId()) {
                                    List<SettingInfoModel.City_> type_city_list = type_List.get(x).getCity();
                                    getbrands(type_city_list);
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });

                    // ------------------- For Modals Spinner ----------------
                    for (int x = 0; x < modals_list.size(); x++)
                        models.add(modals_list.get(x).getModal());

                    final ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_dropdown_item_1line, models);
                    search_model_spinner.setAdapter(category_adapter);
                    search_model_spinner.setSelection(0);

                    search_model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selected_model = adapterView.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                    // ------------------- For city Spinner ----------------

                    final List<SettingInfoModel.City> city_list = counts_list.get(0).getCity();
                    for (int y = 0; y < city_list.size(); y++) {
                        cities.add(city_list.get(y).getName());
                    }

                    final ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_dropdown_item_1line, cities);
                    search_city_spinner.setAdapter(city_adapter);
                    search_city_spinner.setSelection(0);

                    search_city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            selected_model = adapterView.getSelectedItem().toString();
                            selected_city_id = city_list.get(i).getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SettingInfoModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getbrands(final List<SettingInfoModel.City_> type_city_list) {
        for (int x = 0; x < type_city_list.size(); x++)
            brands.add(type_city_list.get(x).getName());

        final ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_dropdown_item_1line, brands);
        search_brands_spinner.setAdapter(category_adapter);
        search_brands_spinner.setSelection(0);

        search_brands_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_brand = adapterView.getSelectedItem().toString();
                selected_brand_id = type_city_list.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!FUtilsValidation.isEmpty(max_price_ed, getString(R.string.required))
                && !FUtilsValidation.isEmpty(min_price_ed, getString(R.string.required))
                ) {
            no_results_txt.setVisibility(View.GONE);
            results_recycler.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            apiService = ApiClient.getClient().create(ApiService.class);
            Call<HomeModel> call = apiService.search(type + "", selected_brand_id + "", selected_model, min_price_ed.getText().toString(), max_price_ed.getText().toString(), selected_city_id + "");
            call.enqueue(new Callback<HomeModel>() {
                @Override
                public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                    HomeModel HomeModel = response.body();
                    if (HomeModel.getSuccess()) {
                        progressBar.setVisibility(View.GONE);
                        no_results_txt.setVisibility(View.GONE);
                        results_recycler.setVisibility(View.VISIBLE);

                        List<HomeModel.Message> results_list = HomeModel.getMessage();
                        Log.e("results_list : ", results_list.size() + "");

                        results_recycler.setLayoutManager(new GridLayoutManager(Search.this, 2));
                        results_recycler.setHasFixedSize(true);

                        adapter = new SearchResultsAdapter(Search.this, results_list);
                        results_recycler.setAdapter(adapter);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        results_recycler.setVisibility(View.GONE);
                        no_results_txt.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<HomeModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        }
    }
}
