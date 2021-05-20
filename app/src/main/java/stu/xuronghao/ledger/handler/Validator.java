package stu.xuronghao.ledger.handler;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private Validator() {
    }

    /**
     * 检查邮箱格式
     *
     * @param email   输入的邮箱
     * @param context toast显示的页面context
     * @return 检查结果
     */
    private static boolean checkEmailFormat(String email, Context context) {
        if (null == email || "".equals(email)) {
            Toast toast = Toast.makeText(context, ConstantVariable.HINT_EMPTY_EMAIL, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        String regEx1 = ConstantVariable.EMAIL_REGEX;
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            Toast toast = Toast.makeText(context, ConstantVariable.HINT_WRONG_EMAIL_FORMAT, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        return true;
    }

    /**
     * 检查昵称输入格式是否正确
     *
     * @param nickName 待检查的昵称
     * @param context  toast显示的页面context
     * @return 检查结果
     */
    private static boolean checkNickName(String nickName, Context context) {
        if (!"".equals(nickName) && (nickName.length() < 3 || nickName.length() > 16)) {
            Toast toast = Toast.makeText(context, ConstantVariable.HINT_WRONG_EMAIL_FORMAT, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        return true;
    }

    /**
     * 检查密码输入格式
     *
     * @param passwd  代价查密码
     * @param context toast显示的页面context
     * @return 检查结果
     */
    private static boolean checkPasswd(String passwd, Context context) {
        //密码格式
        if ("".equals(passwd)) {
            Toast toast = Toast.makeText(context, ConstantVariable.HINT_EMPTY_PASSWD, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        if (passwd.length() < 6) {
            Toast toast = Toast.makeText(context, ConstantVariable.HINT_WRONG_PASSWD_LENGTH, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        return true;
    }

    /**
     * 确认密码输入
     *
     * @param passwd  密码
     * @param confirm 确认密码
     * @param context toast显示的页面context
     * @return 检查结果
     */
    private static boolean confirmPasswd(String passwd, String confirm, Context context) {
        if (!passwd.equals(confirm)) {
            Toast toast = Toast.makeText(context, ConstantVariable.HINT_CONFIRM_PASSWD, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        return true;
    }

    /**
     * 校验注册输入
     *
     * @param userNo     邮箱
     * @param userName   用户名
     * @param userPasswd 密码
     * @param confirm    确认密码
     * @param context    toast显示的页面context
     * @return 检查结果
     */
    public static boolean checkSignUpInput(String userNo, String userName,
                                           String userPasswd, String confirm, Context context) {
        return checkEmailFormat(userNo, context) && checkNickName(userName, context) &&
                checkPasswd(userPasswd, context) && confirmPasswd(userPasswd, confirm, context);
    }

    /**
     * 校验登陆输入
     *
     * @param userNo     邮箱
     * @param userPasswd 密码
     * @param context    toast显示的页面context
     * @return
     */
    public static boolean checkLoginInput(String userNo, String userPasswd, Context context) {
        return checkEmailFormat(userNo, context) && checkPasswd(userPasswd, context);
    }

    /**
     * 校验账目输入信息
     *
     * @param event   事件
     * @param money   金额
     * @param context toast显示的页面context
     * @return 检查结果
     */
    public static boolean checkBillInfoInput(String event, String money, Context context) {
        if (!event.isEmpty()) {
            if (!money.isEmpty() && Double.parseDouble(money) != 0) {
                return true;
            } else {
                Toast toast = Toast.makeText(context, ConstantVariable.HINT_EMPTY_AMOUNT, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(context, ConstantVariable.HINT_EMPTY_EVENT, Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }

    public static boolean checkVoiceParseResult(String moneyStr, String typeStr, int typeCode) {
        return !ConstantVariable.NULL_STR.equals(moneyStr) &&
               !ConstantVariable.NULL_STR.equals(typeStr) &&
                ConstantVariable.ERROR_CODE != typeCode;
    }

    public static boolean checkDashBoardBudget(String budget) {
        return !budget.isEmpty() && Double.parseDouble(budget) >= 0;
    }
}