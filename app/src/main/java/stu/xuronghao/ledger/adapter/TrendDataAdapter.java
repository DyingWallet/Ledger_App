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
import stu.xuronghao.ledger.entity.TrendData;

public class TrendDataAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<TrendData> dataList;

    public TrendDataAdapter(Context context, List<TrendData> dataList) {
        this.inflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //外部静态类
    static class TrendItemHolder {
        public ImageView img;
        public TextView month;
        public TextView income;
        public TextView cost;
        public TextView surplus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrendItemHolder holder;

        if (convertView == null) {
            holder = new TrendItemHolder();
            convertView = inflater.inflate(R.layout.trend_chart_item, null);

            holder.month = convertView.findViewById(R.id.txv_Trend_Month);
            holder.income = convertView.findViewById(R.id.txv_Trend_Month_Income);
            holder.cost = convertView.findViewById(R.id.txv_Trend_Month_Cost);
            holder.surplus = convertView.findViewById(R.id.txv_Trend_Month_Surplus);
            holder.img = convertView.findViewById(R.id.img_Trend_Type_Item);
            convertView.setTag(holder);
        } else {
            holder = (TrendItemHolder) convertView.getTag();
        }
        double surplus = dataList.get(position).getSurplus();
        String monthStr = dataList.get(position).getMonth() + "月";

        if (surplus > 0) {
            holder.img.setImageResource(R.drawable.icon_good);
            monthStr += "盈:";
            holder.surplus.setTextColor(holder.surplus.getResources().getColor(R.color.green));
        } else {
            holder.img.setImageResource(R.drawable.icon_bad);
            monthStr += "亏:";
            holder.surplus.setTextColor(holder.surplus.getResources().getColor(R.color.roseRed));
        }
        holder.month.setText(monthStr);
        holder.surplus.setText(surplus + "元");
        holder.income.setText(dataList.get(position).getIncome() + "元");
        holder.cost.setText(dataList.get(position).getCost() + "元");

        return convertView;
    }
}
