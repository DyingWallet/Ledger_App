package stu.xuronghao.ledger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.ChatInfo;

public class ChatListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ChatInfo> msgList;

    public ChatListAdapter(Context context, List<ChatInfo> msgList) {
        this.inflater = LayoutInflater.from(context);
        this.msgList = msgList;
    }

    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class MsgHolder {
        public TextView msg;
        public ImageView print;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MsgHolder holder;
        ChatInfo info = msgList.get(position);
        holder = new MsgHolder();
        if (info.getIsMeSend() == 1) {
            convertView = inflater.inflate(R.layout.chat_item_right, null);
            holder.msg = convertView.findViewById(R.id.txv_chat_item_user);
            holder.print = convertView.findViewById(R.id.img_chat_item_user);
        } else {
            convertView = inflater.inflate(R.layout.chat_item_left, null);
            holder.msg = convertView.findViewById(R.id.txv_chat_item_npc);
            holder.print = convertView.findViewById(R.id.img_chat_item_npc);
        }
        convertView.setTag(holder);

        holder.msg.setText(info.getContent());
        //        holder.print.setImageResource(info.getImg());

        if (info.getIsMeSend() == 1)
            holder.print.setImageResource(R.drawable.background);
        else
            holder.print.setImageResource(R.drawable.app_icon);
        return convertView;
    }
}
