package stu.xuronghao.ledger.handler.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class PostSender implements Callable<String> {
    private String url = "";
    private String jsonStr = "";

    public PostSender(String url, String jsonStr) {
        this.url = url;
        this.jsonStr = jsonStr;
    }

    @Override
    public String call() throws Exception {
        String res = null;
        Log.w("In Call. Before Post: ", "The url is: " + url);
        Log.w("Ready to Post Json: ", "The jsonStr is: " + jsonStr);
        res = postRequest();
        Log.w("In Call. After Post: ", "The result is: " + res);
        return res;
    }

    private String postRequest() {

        HttpURLConnection connection = null;
        StringBuilder result = new StringBuilder();

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            //POST请求
            connection.setRequestMethod("POST");
            //10s超时
            connection.setConnectTimeout(5000);
            //允许输出
            connection.setDoOutput(true);
            //允许输入
            connection.setDoInput(true);
            //不使用缓存
            connection.setUseCaches(false);
            //设置名-值对以及编码
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            //连接
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(jsonStr);
            //刷新缓冲区，发送数据
            writer.flush();
            outputStream.close();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                //回收资源
                inputStream.close();
                reader.close();
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.disconnect();
        }
        return null;
    }
}
