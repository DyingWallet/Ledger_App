package stu.xuronghao.ledger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import stu.xuronghao.ledger.R;

public class BillDataAdapter extends BaseAdapter {

    //获取一个LayoutInflater来导入布局
    private LayoutInflater                     inflater;
    private ArrayList<HashMap<String, Object>> listItem;

    public BillDataAdapter(Context context, ArrayList<HashMap<String, Object>> listItem) {
        this.inflater = LayoutInflater.from(context);
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        //获取数据条目数
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        //获取索引为position的数据
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        //获取指定数据的索引在表中对应的行索引
        return position;
    }

    //convertView+ViewHolder来重写getView

    //外部静态类
    static class ViewHolder {
        public ImageView imageView;
        public TextView  title;
        public TextView  content;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.bill_data_item, null);
            holder.imageView = convertView.findViewById(R.id.img_Bill_Data_Item);
            holder.title = convertView.findViewById(R.id.txv_Bill_Data_Item_Tit);
            holder.content = convertView.findViewById(R.id.txv_Bill_Data_Item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, Object> item = listItem.get(position);

        holder.imageView.setImageResource((Integer) item.get("ItemImage"));

        holder.title.setText((String) item.get("ItemTitle"));
        holder.content.setText((String) item.get("ItemContent"));

        return convertView;
    }
}
