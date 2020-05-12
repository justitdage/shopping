package com.kaikeba.batch.task

import com.kaikeba.batch.bean.{OrderRecord, OrderRecordWide}
import org.apache.commons.lang3.time.FastDateFormat
import org.apache.flink.api.scala.DataSet
import org.apache.flink.api.scala._
object PreProcessTask {

  def process(orderDataSet:DataSet[OrderRecord]) = {
    orderDataSet.map{
      order => {
        OrderRecordWide(order.orderId,
          order.userId,
          order.merchantId,
          order.orderAmount,
          order.payAmount,
          order.payMethod,
          order.payTime,
          order.benefitAmount,
          order.voucherAmount,
          order.commodityId,
          order.activityNum,
          order.createTime,
          formatDateTime(order.payTime, "yyyyMMdd"),
          formatDateTime(order.payTime, "yyyyMM"),
          formatDateTime(order.payTime, "yyyy")
        )
      }
    }
  }

  def formatDateTime(date:String, format:String) = {
    val timestampFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")

    val timestamp = timestampFormat.parse(date.trim).getTime

    val formatDate = FastDateFormat.getInstance(format)
    formatDate.format(timestamp)
  }

  def main(args: Array[String]): Unit = {
    println(formatDateTime("2018-11-28 00:00:00", "yyyy-MM"))
    println(formatDateTime("2018-11-28 00:00:00", "yyyy-MM-dd"))
    println(formatDateTime("2018-11-28 00:00:00", "yyyy-MM-dd-HH"))
  }
}
