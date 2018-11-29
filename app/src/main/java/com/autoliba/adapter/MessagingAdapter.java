package com.autoliba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.R;
import com.autoliba.activities.Chat;
import com.autoliba.activities.MainActivity;
import com.autoliba.model.ChatMessagesResponseModel;
import com.autoliba.model.MessageType;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by hendiware on 2016/12 .
 */

public class MessagingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatMessagesResponseModel.Message> messages;
    private Context context;

    /**
     * Constructor for Adapter
     *
     * @param messages list of messages will showed
     * @param context  context
     */
    public MessagingAdapter(List<ChatMessagesResponseModel.Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * check the type of view and return holder
         */
        if (viewType == MessageType.SENT_TEXT) {
            Toast.makeText(context, "kk", Toast.LENGTH_SHORT).show();
            return new SentTextHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sent_message_text, parent, false));
        } else if (viewType == MessageType.RECEIVED_TEXT) {
            Toast.makeText(context, "hh", Toast.LENGTH_SHORT).show();
            return new ReceivedTextHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_received_message_text, parent, false));
        }else {
            Toast.makeText(context, "gggg", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mHolder, int position) {

        int type = getItemViewType(position);

        ChatMessagesResponseModel.Message message_data = messages.get(position);
        /**
         * check message type and init holder to user it and set data in the right place for every view
         */
        if (type == MessageType.SENT_TEXT) {
            SentTextHolder holder = (SentTextHolder) mHolder;
            holder.tvTime.setText(message_data.getMsgDate());
            holder.tvMessageContent.setText(message_data.getMessage());

        } else if (type == MessageType.RECEIVED_TEXT) {
            ReceivedTextHolder holder = (ReceivedTextHolder) mHolder;
            holder.tvTime.setText(message_data.getMsgDate());
//            holder.tvUsername.setText(message_data.get());
            holder.tvMessageContent.setText(message_data.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        /**
         * check the user id to detect if message sent or received
         * then check if message is text or img
         */

        int userID = MainActivity.user_data.getId();
        ChatMessagesResponseModel.Message message = messages.get(position);

        if (userID == Integer.parseInt(message.getSenderId())) {

            return MessageType.SENT_TEXT;

        } else {

            return MessageType.RECEIVED_TEXT;

        }
    }

    // sent message holders
    class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView tvTime;

        public SentMessageHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

    // sent message with type text
    class SentTextHolder extends SentMessageHolder {

        TextView tvMessageContent;

        public SentTextHolder(View itemView) {
            super(itemView);
            tvMessageContent = itemView.findViewById(R.id.tv_message_content);
        }
    }

    // received message holders
    class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView tvUsername;
        TextView tvTime;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvUsername = itemView.findViewById(R.id.tv_username);
        }
    }

    // received message with type text
    class ReceivedTextHolder extends ReceivedMessageHolder {
        TextView tvMessageContent;

        public ReceivedTextHolder(View itemView) {
            super(itemView);
            tvMessageContent = itemView.findViewById(R.id.tv_message_content);
        }
    }

}
