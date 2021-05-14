package stu.xuronghao.ledger.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import stu.xuronghao.ledger.R;

public class ColorsHandler {
    private static final int[] colors = {R.color.picaPink, R.color.dining,
            R.color.trans, R.color.cloth, R.color.daily};

    private static final String[] colorStr = {"#EC97B6","#106D9C","#5A92AD","#FFC107","#00FFFF"};

    private static final Map<String,String> colorMap;

    static {
        colorMap = new HashMap<>();
        colorMap.put("餐饮","#EC97B6");
        colorMap.put("交通","#106D9C");
        colorMap.put("服饰","#5A92AD");
        colorMap.put("日用","#FFC107");
        colorMap.put("工资","#EC97B6");
        colorMap.put("奖金","#106D9C");
        colorMap.put("借款","#5A92AD");
        colorMap.put("红包","#FFC107");
        colorMap.put("其他","#00FFFF");
    }

    public static String[] getColorStr(int length){

        return Arrays.copyOfRange(colorStr,0,length);
    }

    public static int[] getColors(){
        return colors;
    }

    public static int getTypeColor(int index){
        return colors[index];
    }

    public static String getTypeColor(String type){return colorMap.get(type);}
}
