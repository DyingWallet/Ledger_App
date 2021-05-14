package stu.xuronghao.ledger.AAChartCoreLib.AAOptionsModel;

public class AAPosition {
    public String align;
    public String verticalAlign;
    public Number x;
    public Number y;

    public AAPosition align(String prop) {
        align = prop;
        return this;
    }

    public AAPosition verticalAlign(String prop) {
        verticalAlign = prop;
        return this;
    }

    public AAPosition align(Number prop) {
        x = prop;
        return this;
    }

    public AAPosition y(Number prop) {
        y = prop;
        return this;
    }

}
