package com.tianzh.admin.test.dataimport;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.model.ProductAnalysis;
import com.tianzh.admin.business.analysis.service.HttpRequestService;
import com.tianzh.admin.business.analysis.service.OrderService;
import com.tianzh.admin.business.analysis.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pig on 2015-06-05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:web/WEB-INF/applicationContext.xml"})
public class OrderTest {

    @Autowired
    OrderService orderService;

    @Autowired
    HttpRequestService httpRequestService;

    @Test
    public void testAddOrder() {
        String host = "http://192.168.1.107";
        String[] urls = {"/zhuquenotify.do?agent_number=1442195865&order_id=200012412&cpparam=|dtyhf|tz05&imei=860763020840890&imsi=460026353620114&report_type=1&pay_price=15.000000&order_time=1445654680&post_time=1445654694&sign=8008ac95db0071cd759e62f176667711"};

        for (String url : urls) {
            url = host + url;

            try {
                httpRequestService.get(url);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }


    @Test
    public void queryOrdersTest() {
        Order order = new Order();

        order.setProductId(3);
//        order.setUserId(11);
        order.setProdIdentification("nsxq");
//        order.setStatus(Constant.OrderStatus.ORDERSUCESS);
        String startDate = "2015-06-08";
        String endDate = "2015-06-08";
        List<Order> orders = orderService.queryOrders(order, startDate, endDate);

        System.out.println("共：" + orders.size() + "条");
    }


    @Test
    public void analysisOrdersTest() {

        List<ProductAnalysis> productAnalysises = null;
        try {
            productAnalysises = orderService.analysisOrders(null, "2015-06-08");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(productAnalysises);
    }

    private int random() {
        return (int) (Math.random() * 100);
    }
}
