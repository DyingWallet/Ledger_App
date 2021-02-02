package stu.xuronghao.ledger.handler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GetHttpResponse {
    //设置为服务器的ip及端口
    private String
//            url     = "http://149.28.26.151:8090/",
//            url = "http://192.168.31.95:8090/",
            url = "http://192.168.123.64:8090/",
            result  = "",
            jsonStr = "",
            service = "",
            params  = "";
    private int i = 1;

    public void setService(String service) {
        this.service = service;
    }

    public void setParams(String tag, String value) {
        if (i == 1) {
            params += "?" + tag + "=" + value;
            i++;
        } else {
            params += "&" + tag + "=" + value;
        }
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getUrl() {
        return url + service + params;
    }

    public String Getter() {
        //建立线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        //建立&设置连接
        GetSender sender = new GetSender(url + service + params);
        //执行并获取Future对象，获取线程执行结果
        Future<String> future = pool.submit(sender);
        result = "";
        //当线程执行完毕
        while (true) {
            if (future.isDone()) {
                try {
                    if (future.get() != null)
                        result = future.get().toString();
                    //                    Log.w("Getter future: ", result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    pool.shutdown();
                }
                break;
            }
        }
        return result;
    }

    public String Poster() {
        ExecutorService pool = Executors.newCachedThreadPool();
        PostSender sender = new PostSender(url + service, jsonStr);
        Future<String> future = pool.submit(sender);
        result = "";
        while (true) {
            if (future.isDone()) {
                try {
                    if (future.get() != null)
                        result = future.get().toString();
                    //                    Log.w("Poster future: ", result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    pool.shutdown();
                }
                break;
            }
        }
        return result;
    }
}
