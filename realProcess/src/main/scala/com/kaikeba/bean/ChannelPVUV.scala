package com.kaikeba.bean

//定义频道实时的PVUV样例类
case class ChannelPVUV(
                      var channelID:String, //频道id
                      var userID:String,    //用户id
                      var  pv:Long,         // PV
                      var  uv:Long,         // UV
                      var  timeStamp:Long,  // 时间戳
                      var  dateField:String,// 时间字段
                      var  groupField:String//按照不同的时间维度+频道分组
                      )
