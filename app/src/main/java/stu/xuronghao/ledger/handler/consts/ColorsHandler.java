package stu.xuronghao.ledger.handler.consts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import stu.xuronghao.ledger.R;

public class ColorsHandler {
    private static final int[] colors = {R.color.themePink, R.color.wightBlue,
            R.color.lightEarth, R.color.deepPink, R.color.indigo};

    public static final String PICA_PINK = "#EC97B6";
    public static final String ROSE_RED = "#FA6594";
    public static final String LIGHT_BLUE = "#DEEFFF";
    public static final String LIGHT_EARTHY = "#FFEADC";
    public static final String DEEP_PINK = "#C8839C";
    public static final String INDIGO = "#B5C9D9";
    private static final String[] colorStr = {ROSE_RED, LIGHT_BLUE, LIGHT_EARTHY, DEEP_PINK, INDIGO};

    private static final Map<String, String> colorMap;

    static {
        colorMap = new HashMap<>();
        colorMap.put("餐饮", ROSE_RED);
        colorMap.put("交通", LIGHT_BLUE);
        colorMap.put("服饰", LIGHT_EARTHY);
        colorMap.put("日用", DEEP_PINK);
        colorMap.put("工资", ROSE_RED);
        colorMap.put("奖金", LIGHT_BLUE);
        colorMap.put("补贴", LIGHT_EARTHY);
        colorMap.put("红包", DEEP_PINK);
        colorMap.put("其他", INDIGO);
    }

    private ColorsHandler() {
    }

    public static String[] getColorStr(int length) {

        return Arrays.copyOfRange(colorStr, 0, length);
    }

    public static int[] getColors() {
        return colors;
    }

    public static int getTypeColor(int index) {
        return colors[index];
    }

    public static String getTypeColor(String type) {
        return colorMap.get(type);
    }
}
