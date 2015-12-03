package com.tianzh.admin.test.dataimport;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.model.ProductDetial;
import com.tianzh.admin.business.analysis.service.ProductDetialService;
import com.tianzh.admin.business.analysis.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;

/**
 * Created by pig on 2015-06-06.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:web/WEB-INF/applicationContext.xml"})
public class ProductDetialTest {

    @Autowired
    ProductDetialService productDetialService;


    @Test
    public void addProductDetialTest(){
        ProductDetial productDetial = new ProductDetial();

        productDetial.setStatus(Constant.ProductStatus.NORMAL);
        productDetial.setProductId(3);
        productDetial.setKeyType(Constant.KeyType.UMENGKEY);
        productDetial.setThirdPartyKey("5562f26567e58e76b500347bxxxxxxx");
        productDetial.setCreateTime(Calendar.getInstance().getTime());
        try {
            productDetialService.addProductDetial(productDetial);

            System.out.println("添加产品Key成功！！！");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加产品Key失败！！！");

        }
    }


    @Test
    public void queryProductDetialTest(){
//        List<ProductDetial> productDetials = productDetialService.queryProductDetials(3);
//        System.out.println("查询结果："+productDetials);
    }


    @Test
    public void delProductDetialTest(){
        ProductDetial detial = new ProductDetial();

        detial.setId(4);

        try {
            productDetialService.delProductDetial(detial);
            System.out.println("删除成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除失败！！！");
        }
    }


    @Test
    public void editProductDetial(){
        ProductDetial detial = new ProductDetial();
        detial.setId(4);
        detial.setThirdPartyKey("ssdfsdfsdfsadfasdfsdasadaaaaaaaaaaaaaaaa");
        detial.setKeyType(Constant.KeyType.TALKINGDATAKEY);
        detial.setStatus(Constant.ProductStatus.CLOSED);
        try {
            productDetialService.editProductDetial(detial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
