package com.kaikeba.`trait`

import com.kaikeba.bean.Message
import org.apache.flink.streaming.api.scala.DataStream

/**
  * 数据处理的接口
  * 规范代码开发，便于统一管理
  */
trait DataProcess {

    def process(dataStream:DataStream[Message])

}
