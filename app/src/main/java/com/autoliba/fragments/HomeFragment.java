package com.autoliba.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.R;
import com.autoliba.activities.AddAds;
import com.autoliba.activities.Search;
import com.autoliba.adapter.HomeRecyclerAdapter;
import com.autoliba.model.FilterTypesModel;
import com.autoliba.model.HomeModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.google.android.gms.common.api.Api;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView home_recycler;
    ProgressBar progressBar;
    TextView no_available_data;
    RecyclerView.LayoutManager layoutManager;
    ApiService apiService;
    HomeRecyclerAdapter adapter;
    Spinner type_Spinner;
    List<String> types = new ArrayList<>();
    List<FilterTypesModel.Message> list_types;

    EditText search_ed;
    String selected_type_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("الرئيسية");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        home_recycler = view.findViewById(R.id.home_recycler_id);
        progressBar = view.findViewById(R.id.home_progress_id);
        no_available_data = view.findViewById(R.id.no_available_data_txt_id);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        search_ed = view.findViewById(R.id.home_search_ed_id);
        type_Spinner = view.findViewById(R.id.type_spinner_id);

        getfilterTypes();

        search_ed.setInputType(InputType.TYPE_NULL);
        search_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showMyDialog();
                Toast.makeText(getActivity(), "Search_ed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), Search.class));

            }
        });
        search_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // showMyDialog();
                }
            }
        });

//        LoadHomeData("0");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    private void getfilterTypes() {

        types.add("الكل");

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<FilterTypesModel> call = apiService.allTypes();
        call.enqueue(new Callback<FilterTypesModel>() {
            @Override
            public void onResponse(Call<FilterTypesModel> call, Response<FilterTypesModel> response) {
                FilterTypesModel filterTypesModel = response.body();
                boolean success = filterTypesModel.getSuccess();
                if (success) {
                    list_types = filterTypesModel.getMessage();
                    for (int i = 0; i < list_types.size(); i++) {
                        types.add(list_types.get(i).getName());
                    }
                    ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, types);
                    type_Spinner.setAdapter(spinner_adapter);
                    type_Spinner.setSelection(0);

                    type_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selected_item = adapterView.getSelectedItem().toString();
                            if (selected_item.equals("الكل")) {
                                selected_type_id = "0";
                            } else {
                                selected_type_id = list_types.get(i).getId();
                            }
                            LoadHomeData(selected_type_id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FilterTypesModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void LoadHomeData(String id) {
        progressBar.setVisibility(View.VISIBLE);
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<HomeModel> call = apiService.homeData(id);
        call.enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                HomeModel homeModel = response.body();
                boolean success = homeModel.getSuccess();
                if (success) {
                    List<HomeModel.Message> list = homeModel.getMessage();
                    no_available_data.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    home_recycler.setVisibility(View.VISIBLE);
                    home_recycler.setLayoutManager(layoutManager);
                    home_recycler.setHasFixedSize(true);
                    adapter = new HomeRecyclerAdapter(getActivity(), list);
                    home_recycler.setAdapter(adapter);

                } else {
                    home_recycler.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    no_available_data.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
