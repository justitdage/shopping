package com.kaikeba.bean

//用户上网类型样例类
case class UserNetWork(
            var network:String,      //上网类型
            var count:Long,          //次数
            var newCount:Long,       //新用户
            var oldCount:Long,       //老用户
            var timestamp:Long,      //时间戳
            var dateField:String     //拼接rowkey的时间字段
                 )
