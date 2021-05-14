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
import stu.xuronghao.ledger.entity.TotalFee;
import stu.xuronghao.ledger.handler.ConstantVariable;

public class PieDataAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<TotalFee> feeList;

    public PieDataAdapter(Context context, List<TotalFee> feeList) {
        this.inflater = LayoutInflater.from(context);
        this.feeList = feeList;
    }

    @Override
    public int getCount() {
        return feeList.size();
    }

    @Override
    public Object getItem(int position) {
        return feeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //外部静态类
    static class PieItemHolder {
        public ImageView imageView;
        public TextView type;
        public TextView fee;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PieItemHolder holder;

        if (convertView == null) {
            holder = new PieItemHolder();
            convertView = inflater.inflate(R.layout.pie_chart_item, null);
            holder.imageView = convertView.findViewById(R.id.img_Pie_Type_Item);
            holder.type = convertView.findViewById(R.id.txv_Fee_Type);
            holder.fee = convertView.findViewById(R.id.txv_Fee);
            convertView.setTag(holder);
        } else {
            holder = (PieItemHolder) convertView.getTag();
        }
        TotalFee data = feeList.get(position);
        String feeText = data.getFee() + ConstantVariable.TEXT_YUAN;
        holder.imageView.setImageResource(data.getImg());
        holder.type.setText(data.getType());
        holder.fee.setText(feeText);

        return convertView;
    }
}
