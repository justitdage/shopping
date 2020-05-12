package com.kaikeba.bean

case class ChannelUserFreshness(
                var  channelID:String, //频道id
                var  newCount:Long,    //新用户
                var  oldCount:Long,    //老用户  时间维度的老用户
                var  timeStamp:Long,   // 时间戳
                var  dateFiled:String, // 时间字段
                var  groupField:String //按照不同的时间维度+频道分组
                               )