package com.autoliba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.activities.MainActivity;
import com.autoliba.model.ChatMessagesResponseModel;

import java.util.List;

/**
 * Created by Ma7MouD on 11/21/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    Context context;
    List<ChatMessagesResponseModel.Message> list;

    public MessagesAdapter(Context context, List<ChatMessagesResponseModel.Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder holder, int position) {

        int userID = MainActivity.user_data.getId();
        ChatMessagesResponseModel.Message message_data = list.get(position);
        if (userID == Integer.parseInt(message_data.getSenderId())) {
            holder.received_msg_layout.setVisibility(View.GONE);
            holder.send_tv_time.setText(message_data.getMsgDate());
            holder.send_msg_txtV.setText(message_data.getMessage());

        } else {
            holder.send_msg_layout.setVisibility(View.GONE);
            holder.received_tv_time.setText(message_data.getMsgDate());
            holder.received_msg_txtv.setText(message_data.getMessage());
//            holder.received_tv_username.setText(message_data.);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout send_msg_layout, received_msg_layout;
        TextView send_tv_time, received_tv_time, send_msg_txtV, received_tv_username, received_msg_txtv;

        public ViewHolder(View itemView) {
            super(itemView);
            send_msg_layout = itemView.findViewById(R.id.send_msg_layout_id);
            received_msg_layout = itemView.findViewById(R.id.received_msg_layout_id);
            send_tv_time = itemView.findViewById(R.id.send_tv_time);
            received_tv_time = itemView.findViewById(R.id.received_tv_time);
            send_msg_txtV = itemView.findViewById(R.id.send_tv_message_content);
            received_msg_txtv = itemView.findViewById(R.id.received_tv_message_content);
            received_tv_username = itemView.findViewById(R.id.received_tv_username);
        }
    }
}
