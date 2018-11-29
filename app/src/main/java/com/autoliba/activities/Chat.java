package com.autoliba.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.adapter.ChatRoomsAdapter;
import com.autoliba.model.GetChattersModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView chat_rooms_data_txt, back;
    ProgressBar progressBar;

    ApiService apiService;
    ChatRoomsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        back = findViewById(R.id.chat_back_txt_id);
        recyclerView = findViewById(R.id.chat_rooms_recycler_id);
        chat_rooms_data_txt = findViewById(R.id.chat_rooms_data_txt_id);
        progressBar = findViewById(R.id.chat_rooms_progress_id);

        getChatRooms(MainActivity.user_data.getId());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getChatRooms(int id) {
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<GetChattersModel> call = apiService.getChattersRoom(id + "");
        call.enqueue(new Callback<GetChattersModel>() {
            @Override
            public void onResponse(Call<GetChattersModel> call, Response<GetChattersModel> response) {
                GetChattersModel chattersModel = response.body();
                boolean success = chattersModel.getSuccess();

                if (success) {
                    progressBar.setVisibility(View.GONE);
                    List<GetChattersModel.Message> list = chattersModel.getMessage();
                    Log.e("list_size: ", list.size() + "");
                    if (list.size() > 0) {
                        chat_rooms_data_txt.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new GridLayoutManager(Chat.this, 1));
                        recyclerView.setHasFixedSize(true);

                        adapter = new ChatRoomsAdapter(Chat.this, list);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    chat_rooms_data_txt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetChattersModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }
}
