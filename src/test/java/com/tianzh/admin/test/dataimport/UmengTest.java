package com.tianzh.admin.test.dataimport;

import com.tianzh.admin.business.analysis.service.HttpRequestService;
import com.tianzh.admin.business.analysis.service.UserService;
import com.tianzh.admin.business.analysis.utils.DateUtils;
import com.tianzh.admin.common.util.encrypt.md5.Md5Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pig on 2015-06-05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:web/WEB-INF/applicationContext.xml"})
public class UmengTest {

    @Autowired
    UserService userService;

    @Autowired
    HttpRequestService httpRequestService;

    @Test
    public void obtainNewUsersTest() throws IOException {


        String url = "http://202.107.192.23:6821/chn-data/service/cpsettle.do";
        String merchantId = "SZJBXLPAY1001";
        String key = "K@A@*+NXxY+@*#b-";
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(url).append("?").append("merchantId=").append(merchantId).append("&").append("startDate=")
                .append(DateUtils.specifyDate(1)).append("&").append("endDate=").append(DateUtils.specifyDate(1))
                .append("&signMsg=").append(Md5Utils.getMD5(("merchantId=" + merchantId + "&startDate=" + DateUtils.specifyDate(1) + "&endDate=" + DateUtils.specifyDate(1) + "&key=" + key).getBytes()));
        String responseStr = httpRequestService.get(url);


    }

    @Test
    public void testParseRestApi() {
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost("https://api.parse.com/1/push");
//        HttpPost httpPost = new HttpPost("https://api.parse.com/1/events/AppOpened");
        httpPost.setHeader("X-Parse-Application-Id", "x3Fjrvi3WoBhheja0fuByyO3CcFS35EtbS6YH1b2");
        httpPost.setHeader("X-Parse-REST-API-Key", "USGkLawb7gkGztG8AKRx9sYrQI6zm5Y195faxemu");
        httpPost.setHeader("Content-Type", "application/json");

        try {
            StringEntity entity = new StringEntity("{\"channels\": [\"channel01\"],\"data\": {\"alert\": \"The Giants won against the Mets 2-3.\"}}", "UTF-8");
//            StringEntity entity = new StringEntity("{}", "UTF-8");

            httpPost.setEntity(entity);
            //post请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);

            System.out.println("responseCode ----> " + httpResponse.getStatusLine().getStatusCode());

            //getEntity()
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                //打印响应内容

                System.out.println("responseStr ----> " + EntityUtils.toString(httpEntity, "UTF-8"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
//            closeableHttpClient.close();
        }
    }


}
