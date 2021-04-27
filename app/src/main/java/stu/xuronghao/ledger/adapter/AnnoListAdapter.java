package stu.xuronghao.ledger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.AnnoInfo;

public class AnnoListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<AnnoInfo> annoList;

    public AnnoListAdapter(Context context, List<AnnoInfo> annoList) {
        this.inflater = LayoutInflater.from(context);
        this.annoList = annoList;
    }

    @Override
    public int getCount() {
        return annoList.size();
    }

    @Override
    public Object getItem(int position) {
        return annoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class AnnoHolder {
        public TextView title;
        public TextView datetime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AnnoHolder holder;

        if (convertView == null) {
            holder = new AnnoHolder();
            convertView = inflater.inflate(R.layout.anno_list_item, null);
            holder.title = convertView.findViewById(R.id.txv_Anno_Item_Title);
            holder.datetime = convertView.findViewById(R.id.txv_Anno_Item_Date);
            convertView.setTag(holder);
        } else {
            holder = (AnnoHolder) convertView.getTag();
        }

        holder.title.setText(annoList.get(position).getAnnoTitle());
        holder.datetime.setText(annoList.get(position).getAnnoDate());
        return convertView;
    }
}
