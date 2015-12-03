package com.tianzh.admin.business.analysis.constant;

/**
 * Created by pig on 2015-06-05.
 */
public class Constant {
    public class OrderStatus{
        public static final int ORDERSUCESS = 0;
        public static final int ORDERFAIL = 1;

    }

    public class ChargeOffStatus{
        public static final int WAITING = 0;
        public static final int FINISHED = 1;

    }

    public class CooperationType{
        public static final int ACooperation = 0;
        public static final int SCooperation = 1;

    }


    public class ChargeOffType{
        public static final int REALTIME = 0;
        public static final int DELAY = 1;

    }

    public class ProductStatus{
        public static final int NORMAL = 0;
        public static final int CLOSED = 1;
    }


    public class KeyType{
        public static final int UMENGKEY = 0;
        public static final int TALKINGDATAKEY = 1;
        //易接支付
        public static final int TPPKEY1 = 2;
        //乐途
        public static final int TPPKEY2 = 3;
        //元朗
        public static final int TPPKEY3 = 4;
        //掌支付
        public static final int TPPKEY4 = 5;
        //朱雀
        public static final int TPPKEY5 = 6;
    }


    public class ReferenceKey{
        public static final String PRODUCTKEY = "p_k_";
        public static final String THIRDPARTKEY = "t_p_k_";
        public static final String PRODUCTNAMEKEY = "p_n_k_";
        public static final String PRODUCTKEYKEY = "p_k_k_";
        public static final String PRODUCTIDKEY = "p_i_k_";
        public static final String UMENGKEY = "u_m_k_";
        public static final String UMENGACCOUNTPWDKEY = "u_m_a_p_k_";


        public static final String USERKEY = "u_k_";
        public static final String USERCOMPANYNAMEKEY = "u_c_n_k_";
        public static final String USERIDKEY = "u_i_k_";
        public static final String USERDISCOUNTKEY = "u_d_k_";
        public static final String USERCHARGEOFFTYPEKEY = "u_c_o_k_";
        public static final String USERCOOPERATIONTYPEKEY = "u_c_p_k_";
        public static final String USERSHARINGKEY = "u_s_k_";


        public static final String PRODUCTIDENTIFICATIONKEY = "p_i_k_";

        public static final String PRODUCTAPPKEY = "p_a_k_";
    }


    public static class Provider {
        public static final int CHINA_MOBILE = 0;
        public static final int CHINA_UNICOM = 1;
        public static final int CHINA_TELECOM = 2;
    }

}
