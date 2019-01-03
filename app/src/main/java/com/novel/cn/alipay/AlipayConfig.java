package com.novel.cn.alipay;

/**
 * Created by jackieli on 2019/1/2.
 */

public class AlipayConfig {

    /**
     * 支付宝支付业务：入参app_id，上文创建应用时候，已经得到
     * 由于App支付功能需要签约，因此需要上传公司信息和证件等资料进行签约
     * 以下参数，由上传完整公司信息后即可得到
     */
    public static final String APPID = "2016091900544255";

    // 商户PID,用于支付宝账户登录授权业务的入参 pid。
    public static final String PARTNER = "XXXXXXXXXXXXX";

    // 商户收款账号(可不填，支付成功会返回该账号)

    public static final String SELLER = "XXXXXXXXXX";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     * 可以用时间戳
     */
    public static final String TARGET_ID = OrderInfoUtil2_0.getOutTradeNo();

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     *  使用支付宝提供的工具生成RSA公钥和私钥
     *  工具地址：https://doc.open.alipay.com/docs/doc.htmtreeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIkQQiVMrMe8RDQvCcVrsrUe7ma7j0bEk7kBShIaa3pBBEkjUCxHV3Mf9SATl9n6aG/U3ZiFhx96OB4a+tW6sqgl7WM//4/01VnPSNdD5+IA0f0vgF4il86aoZ+sR0RzJMrKj2XUddWzzRC/bwt5RoXVIGoff4yw//5RgPAq1yUiiflnEPFBt/FOib91HAfu5PXa8613jJJRumzDIXvEHTfS64P0XqrRQvOf/WyrKgSmZ3w6D8SRLWVJOecXSXVmccvHXanH+vUtGfCvc0FmqsUeu4WFE5ItTImgS4q2cugFG01ZN5GJIAlHuFSDlXWUrntoVK0/uvhl99lHNOYMapAgMBAAECggEARDFNhCsc/RCMRx3k8dwpKA+IoIyH1Z1A0Do44uDf0/FbojHzwIcDNNrFaFhuMbmHuP5BYm8/4uDKR6+/Pmx4wwhIfOOIXPh20FpIj13NWsN8aji5Wo69Mr+nDcP3Vyy6BClvUV3wpcBKVQSJcVfqBlca/27fWN9lBN2LrusiWuPIUHyHZF9TwnTT14oxQVUot0pZsGqcUdbDAiTLjPuKePMxELPclE2aKiUjbRA3Qpah1Wv5OejlIxq2vEBL+LU0hbAEJ7qfldK1r66MbRqD+tGjOtMLaVTvKAnGQGiGpoCAiTH2t8id02u0+WQXzSocaVypl/nxLdG/N0IR7zm20QKBgQDctZ7DZzFMgXW5B+dLqimBXafdEzmrgOT5clByhPIUeM5zdQifWqdd+2wQ/5zABTC+sHS2x40tqi7kaTy5L+bFLrjZDbGM/BILZl3Z0qcFeaVKLY/oAlbdW/8XyPHdK7eqf2RRBkwPRC7K+widlqwfEQUBtogSyr8nIXcp3oUm/QKBgQCeZyQKzV/2xWshfpi+bTXIN3kfzisnS5cXuSa1neO5Bp8qAKBtiIjVUMMOC0Iov64o0o0TyvAcnsZ9jJgGUJea5d3MaWv3afGOlzRmnLtukyfO0xIlg0z5yXRBXbjvsOLV7b4sderjuOdzV35seEcGXyk14OqT4rKrYr7xW62MHQKBgBPXsEJZELGUIgVUlJLU5Y5f4SubAuBMYkma4WiqUylMAyOWLgRFni5w4DJXCLyqussqChQ0tJiaaETYwUDU8aZgoPXCqWfHccfcmCtxKNrxKM8WcY2mSDD2iGSXCEeeGpEViutN6aClvJ87GncwiKDyYsTYqDsoRy85bwEwpD/xAoGAHILMBfW5VB1sjvbOX1gg2gzCDGt7cPM2GtsEkKP+nQsIEvNR7OUqW9rRgPGd0t/NZSOgqPNaDIy3hVmM83YGE6dUKLrA6CN1iwjEMcP8KIbsH1n2ZjIf5MXR2wIXi2z3JzJHjJEc2igp4KTa6IMtpYPzUvNMxZmENzzqyV6K+jECgYEAzCggeP6sunAwlzhazLNIXQqkh1vxgua0gs7OO5bvonA5LVV+Ji3Rx1my/d4NEf5WF+wqcv7laHlaWibfVIQAdVZZQ566csgJh1x5CNckGx/AD1zk2wLc9B8pq5BFeycwY70uA8FWzR6XWo7OK4+xL7hdsG1dncs4MdqvD8ADh6U=";
    public static final String RSA_PRIVATE = "";

}
