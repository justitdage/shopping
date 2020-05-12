package com.kaikeba.batch.bean

//创建OrderRecordWide样例类，添加上述需要拓宽的字段
case class OrderRecordWide (
                             var orderId: String,
                             var userId: String,
                             var merchantId: String,
                             var orderAmount: Double,
                             var payAmount: Double,
                             var payMethod: String,
                             var payTime: String,
                             var benefitAmount: Double,
                             var voucherAmount: Double,
                             var commodityId: String,
                             var activityNum: String,
                             var createTime: String,
                             var day: String,  //日
                             var month: String,//月
                             var year: String) //年

