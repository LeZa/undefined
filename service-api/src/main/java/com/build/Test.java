package com.build;

import com.build.config.RandomUtil;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static void main(String sck[]) {
        System.out.println(getOrderNum());
    }

    /**
     * 四位分站编号(不够前补0)
     * 八位用户编号(不够前补0)
     * 十四位时间戳(yyyyMMddHHmmss)
     * 六位随机数
     *
     * @return
     */

    private static String getOrderNum() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = df.format(new Date());
        // 得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        // 设置是否使用分组
        nf.setGroupingUsed(false);
        // 设置最大整数位数
        nf.setMaximumIntegerDigits(4);
        // 设置最小整数位数
        nf.setMinimumIntegerDigits(4);
        String substationIdstr = nf.format(445);
        nf.setMaximumIntegerDigits(8);
        nf.setMinimumIntegerDigits(8);
        String uidStr = nf.format(1);
        System.out.println("中文");
        return "first..." + substationIdstr + "..second..." + uidStr + "..third..." + timestamp + "..fourth..." + RandomUtil.getInstance().genString(6, 2);
    }

    public void keis(){
        String str = "{\"appid\":\"wxd1553eb16d6f6e60\",\"busi_partner\":\"101001\",\"col_oidpartner\":\"201706151001824854\",\"dt_order\":\"20171225180343\",\"id_no\":\"131126198707203019\",\"id_type\":\"0\",\"info_order\":\"aa\",\"money_order\":\"0.50\",\"name_goods\":\"开悟者车载香水10ml\",\"name_user\":\"苏世乐\",\"no_order\":\"04450002525720171225180343259036\",\"notify_url\":\"http://124.204.40.226:8585/lianlian-pay/asyncnotify/shopasyncnotify\",\"oid_partner\":\"201706151001824854\",\"openid\":\"oEc6GwUjvkW4Brzv2kpgYX0LCsns\",\"pay_type\":\"W\",\"return_url\":\"http://wx.dev.91haolei.net:8888/wx/pay/llsyncshop\",\"risk_item\":\"{\\\"frms_ware_category\\\":\\\"1009\\\",\\\"user_info_bind_phone\\\":\\\"13521888925\\\",\\\"user_info_dt_register\\\":\\\"20171225104210\\\",\\\"user_info_mercht_userno\\\":\\\"25257\\\"}\",\"seller_send_valid\":\"10080\",\"sign\":\"aDvdTIDs8btMsIQ+wvPs5VbPOH3jAewOxaVl2Cf2DIKew/U3Fx2Q06tQ2arjI0EvYeJmfJhKLcHNotkL1C2TnVCRNVA+g3NJXuhE8DfF0A8CA2rljza6bG7M5GCke0Li61o6nYCFlBEbIAAuW+ipm+VY7Z9CIiaUff0mXc5lU+E=\",\"sign_type\":\"RSA\",\"user_id\":\"04450002525720171225180343259036\"}";

    }
}
