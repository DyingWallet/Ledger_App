package stu.xuronghao.ledger.handler;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class GetSender implements Callable<String> {
    private String url = "";

    public GetSender(String url) {
        this.url = url;
    }

    @Override
    public String call() throws Exception {
        String res = null;
//        Log.w("In Call. Before Get: ", "The url is: " + url);
        res = GetRequest();
        Log.w("In Call. After Get: ", "The result is: " + res);
        return res;
    }

    private String GetRequest() {
        HttpURLConnection connection = null;
        StringBuilder result = new StringBuilder();
        try {
            //建立连接
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String line = null;
                InputStream stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                stream.close();
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
