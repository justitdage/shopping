package com.kaikeba.bean


//用户上网游览器类型样例类
case class UserBrowser(
                      var browser: String,   //游览器类型
                      var count:Long,        //总数
                      var newCount:Long,     //新用户数
                      var oldCount:Long,     //老用户数
                      var timeStamp: Long,   //时间戳
                      var dateField:String   //拼接字段
                      )
