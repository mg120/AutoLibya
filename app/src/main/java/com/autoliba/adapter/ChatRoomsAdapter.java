package com.autoliba.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.activities.Messages;
import com.autoliba.model.GetChattersModel;

import java.util.List;

/**
 * Created by Ma7MouD on 11/18/2018.
 */

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ViewHolder> {

    Context context;
    List<GetChattersModel.Message> list;

    public ChatRoomsAdapter(Context context, List<GetChattersModel.Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ChatRoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_room_layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatRoomsAdapter.ViewHolder holder, int position) {
        holder.userName_txt.setText(list.get(position).getName());
        holder.lastMsg_txt.setText(list.get(position).getLastMessage());
        holder.relativeLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName_txt, lastMsg_txt;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            userName_txt = itemView.findViewById(R.id.tv_title);
            lastMsg_txt = itemView.findViewById(R.id.tv_desc);
            relativeLayout = itemView.findViewById(R.id.rllt_body);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();

            String receiver_Id = list.get(pos).getId();
            Intent intent = new Intent(context, Messages.class);
//            intent.putExtra("room_Id" , list.get(pos).)
            intent.putExtra("receiver_Id", receiver_Id);
            intent.putExtra("receiver_name", list.get(pos).getName());
            context.startActivity(intent);
        }
    }
}