package stu.xuronghao.ledger.handler;

import stu.xuronghao.ledger.R;

public class ColorsHandler {
    private static final int[] colors = {R.color.picaPink, R.color.dining,
            R.color.trans, R.color.cloth, R.color.daily};
    public static int[] getColors(){
        return colors;
    }

    public static int getTypeColor(int index){
        return colors[index];
    }
}
