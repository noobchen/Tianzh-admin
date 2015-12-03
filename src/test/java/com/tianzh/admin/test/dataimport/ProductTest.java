package com.tianzh.admin.test.dataimport;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.model.Product;
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
public class ProductTest {

    @Autowired
    ProductService productService;

    @Test
    public void addProductTest() {
        Product product = new Product();

        product.setName("弄死小强3");
        product.setStatus(Constant.ProductStatus.NORMAL);
        product.setCreateTime(Calendar.getInstance().getTime());

        try {
            productService.addProduct(product);
            System.out.println("添加产品成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加产品失败！！！");
        }
    }

    @Test
    public void queryProductTest(){
        String name = "病毒";

//        List<Product> products = productService.queryProduct(name);

//        System.out.println("查询结果:" + products);
    }

    @Test
    public void editProductTest(){
        Product product = new Product();

        product.setId(2);
        product.setName("病毒战争");
        product.setStatus(Constant.ProductStatus.NORMAL);

        try {
            productService.editProduct(product);
            System.out.println("修改成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("修改失败！！！");
        }
    }


    @Test
    public void deleteProductTest(){
        Product product = new Product();

        product.setId(4);

        try {
            productService.deleteProduct(product);
            System.out.println("删除成功！！！");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("修改失败！！！");

        }
    }
}
