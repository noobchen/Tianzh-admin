package com.tianzh.admin.business.analysis.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pig on 2015-06-06.
 */
@Component
public class HttpClientServiceImpl implements HttpRequestService {


    @Override
    public String get(String url, HashMap<String, String> params) throws IOException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        if (params != null) {
            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append(url).append("?");

            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuffer.append(entry.getKey());
                urlBuffer.append("=");
                urlBuffer.append(entry.getValue());
                urlBuffer.append("&");
            }

            url = urlBuffer.toString().substring(0, urlBuffer.toString().lastIndexOf("&"));
        }

        HttpGet httpGet = new HttpGet(url);
        try {
            //执行get请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //响应状态
//            System.out.println("status:" + httpResponse.getStatusLine());
            //判断响应实体是否为空
            if (entity != null) {
//                System.out.println("contentEncoding:" + entity.getContentEncoding());
                return EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            //关闭流并释放资源
            closeableHttpClient.close();

        }
        return null;
    }

    @Override
    public String get(String url) throws IOException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpGet httpGet = new HttpGet(url);
        try {
            //执行get请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //响应状态
            System.out.println("status:" + httpResponse.getStatusLine());
            //判断响应实体是否为空
            if (entity != null) {
//                System.out.println("contentEncoding:" + entity.getContentEncoding());
                return EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            //关闭流并释放资源
            closeableHttpClient.close();

        }
        return null;
    }

    @Override
    public String post(String url, HashMap<String, String> params) throws Exception {
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost(url);
        // 创建参数队列
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        try {
            //请求参数中假如有中文需要UrlEncode
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams, "UTF-8");

            httpPost.setEntity(entity);
            //post请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            //getEntity()
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                //打印响应内容
                return EntityUtils.toString(httpEntity, "UTF-8");
            }

        } catch (Exception e) {
            throw e;
        } finally {
            //释放资源
            closeableHttpClient.close();
        }

        return null;
    }
}
