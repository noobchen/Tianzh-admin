package com.tianzh.admin.test.dataimport;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.model.ProductDetial;
import com.tianzh.admin.business.analysis.model.ProductIdentification;
import com.tianzh.admin.business.analysis.service.ProductDetialService;
import com.tianzh.admin.business.analysis.service.ProductIdentificationService;
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
public class ProductIdentificationTest {

    @Autowired
    ProductIdentificationService productIdentificationService;


    @Test
    public void addProductIdentificationTest(){

        ProductIdentification identification = new ProductIdentification();

        identification.setProductId(2);
        identification.setProductName("病毒战争");
        identification.setUserId(11);
        identification.setUserCompanyName("权限管理员");
        identification.setProdIdentification("public");
        identification.setChargeOffType(Constant.ChargeOffType.REALTIME);
        identification.setCooperationType(Constant.CooperationType.ACooperation);
        identification.setDiscount(0.45f);
        identification.setStatus(Constant.ProductStatus.NORMAL);
        identification.setCreateTime(Calendar.getInstance().getTime());

        try {
            productIdentificationService.addProductIdentification(identification);
            System.out.println("添加成功！！！");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加失败！！！");

        }
    }


    @Test
    public void queryProductIdentificationTest(){
        ProductIdentification identification = new ProductIdentification();

//        identification.setUserId(11);
//        identification.setProductId(2);
        identification.setProdIdentification("bdzz-channel01");

        List<ProductIdentification> productIdentifications = productIdentificationService.queryProductIdentification(identification);
        System.out.println("查询结果：" + productIdentifications);
    }


    @Test
    public void delProductIdentificationTest(){
        ProductIdentification identification = new ProductIdentification();
        identification.setId(3);
        try {
            productIdentificationService.delProductIdentification(identification);
            System.out.println("删除成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除失败！！！");
        }
    }


    @Test
    public void editProductIdentification(){
        ProductIdentification identification = new ProductIdentification();

        identification.setId(2);
        identification.setChargeOffType(Constant.ChargeOffType.REALTIME);
        identification.setCooperationType(Constant.CooperationType.ACooperation);
        identification.setDiscount(0.25f);

        try {
            productIdentificationService.editProductIdentification(identification);
            System.out.println("修改成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("修改失败！！！");

        }
    }
}
