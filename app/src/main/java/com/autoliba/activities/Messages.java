package com.autoliba.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.NetworkTester;
import com.autoliba.R;
import com.autoliba.adapter.MessagesAdapter;
import com.autoliba.adapter.MessagingAdapter;
import com.autoliba.model.AutoNewsModel;
import com.autoliba.model.ChatMessagesResponseModel;
import com.autoliba.model.HomeModel;
import com.autoliba.model.SendMessageResponseModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Messages extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    RecyclerView messages_recycler;
    TextView no_messages_txt, back, messages_title;
    EditText message_ed;
    ImageView send_img;
    private MessagesAdapter adapter;

    HomeModel.Message ad_data;
    String rooom_Id, receiver_id, receiver_name;

    NetworkTester networkTester = new NetworkTester(this);
    private List<ChatMessagesResponseModel.Message> messages;

    /**
     * BroadCast Receiver message receiver when register it called when broadcast sent for the intent
     * and execute code in the onReceive Method
     */
    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // get message sent from broadcast
            ChatMessagesResponseModel.Message message = intent.getParcelableExtra("msg");

            // check if message is null
            if (message != null) {
                // add message to messages list
                messages.add(message);
                // notify adapter that new message inserted to list in the last position (list size-1)
                adapter.notifyItemInserted(messages.size() - 1);
//                adapter.notifyDataSetChanged();
                // scroll to bottom of recycler show last message
                messages_recycler.scrollToPosition(messages.size() - 1);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        if (getIntent().getExtras() != null) {
            receiver_id = getIntent().getExtras().getString("receiver_Id");
            receiver_name = getIntent().getExtras().getString("receiver_name");
        }
        back = findViewById(R.id.messages_back_txt_id);
        messages_title = findViewById(R.id.messages_title_txt_id);
        progressBar = findViewById(R.id.messages_chat_progress_id);
        messages_recycler = findViewById(R.id.recycler_messages_id);
        no_messages_txt = findViewById(R.id.no_messages_txt_id);
        message_ed = findViewById(R.id.et_message);
        send_img = findViewById(R.id.img_send);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        messages_title.setText(receiver_name);

        // set Layout Manager to RecyclerView
        messages_recycler.setLayoutManager(new LinearLayoutManager(Messages.this));
        messages_recycler.setHasFixedSize(false);

        // create new adapter with these message
        adapter = new MessagesAdapter(Messages.this, messages);
        // set adapter for recycler
        messages_recycler.setAdapter(adapter);

        // get messages
        if (networkTester.isNetworkAvailable()) {
            getMessages(MainActivity.user_data.getId() + "", receiver_id);
        } else {
            Toasty.error(Messages.this, getString(R.string.error_connection), 1500).show();
        }

        // subscribe to topic of room (room+id)
//        FirebaseMessaging.getInstance().subscribeToTopic();
//        Log.e("room topic is ", "room" + roomId);

        send_img.setOnClickListener(this);
    }

    private void getMessages(String sender_id, String receiver_id) {
        Call<ChatMessagesResponseModel> call = ApiClient.getClient().create(ApiService.class).chat_messages(sender_id, receiver_id);
        call.enqueue(new Callback<ChatMessagesResponseModel>() {
            @Override
            public void onResponse(Call<ChatMessagesResponseModel> call, Response<ChatMessagesResponseModel> response) {
                progressBar.setVisibility(View.GONE);
                messages = response.body().getMessage();
                if (messages.size() > 0) {
                    no_messages_txt.setVisibility(View.GONE);
                    messages_recycler.setVisibility(View.VISIBLE);
                    adapter = new MessagesAdapter(Messages.this, messages);
                    messages_recycler.setAdapter(adapter);
                    // scroll to bottom of screen
                    messages_recycler.scrollToPosition(messages.size() - 1);
                } else {
                    messages_recycler.setVisibility(View.GONE);
                    no_messages_txt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ChatMessagesResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.img_send:
                // if msg editText is empty return don't do any thing
                if (message_ed.getText().toString().isEmpty()) {
                    Toasty.error(Messages.this, "برجاء ادخال الرسالة اولا!", 1500).show();
                    return;
                }
                // get msg from edit text
                String msg = message_ed.getText().toString();

                DateFormat df = new SimpleDateFormat("HH:mm");
                String date = df.format(Calendar.getInstance().getTime());

                // create new message
//                // set type to 1 (text message)
//                message.setType("1");
//                // set room id int
//                message.setRoomId(String.valueOf(roomId));
//                // set user id int
//                message.setUserId(String.valueOf(userId));
//                // set user name
//                message.setUsername(username);
//                // set message content
//                message.setContent(msg);
                // add message to messages list
                messages.add(new ChatMessagesResponseModel.Message(msg, MainActivity.user_data.getId() + "", receiver_id, date));

//                adapter.notifyDataSetChanged();
                // sent message to server
                addMessage(msg, receiver_id);
                // notify adapter that there is new message in this position
                adapter.notifyItemInserted(messages.size());
                // scroll to last item in recycler
                messages_recycler.scrollToPosition(messages.size() - 1);
                // set message box empty
                message_ed.setText("");
                break;
        }
    }

    private void addMessage(String msg, String receiver_id) {
        Call<SendMessageResponseModel> call = ApiClient.getClient().create(ApiService.class).send_msg(MainActivity.user_data.getId() + "", receiver_id, msg);
        call.enqueue(new Callback<SendMessageResponseModel>() {
            @Override
            public void onResponse(Call<SendMessageResponseModel> call, Response<SendMessageResponseModel> response) {
                SendMessageResponseModel responseModel = response.body();
                boolean success = responseModel.getSuccess();
                if (success) {
                    Toasty.success(Messages.this, "تم الارسال بنجاح", 1500).show();
                }
            }

            @Override
            public void onFailure(Call<SendMessageResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register receiver to handle "" UPDATE CHAT ACTIVITY "" Filter
        registerReceiver(messageReceiver, new IntentFilter("UpdateChateActivity"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unregister receiver
        unregisterReceiver(messageReceiver);

    }
}