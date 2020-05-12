package com.kaikeba.batch.bean

case class MerchantCountMoney(
                               merchantId:String,  //商家id
                               orderNum:Int,       //订单数
                               payAmount:Double,    //支付金额
                               time:String         //时间
                             )