package com.kaikeba.bean

import com.alibaba.fastjson.{JSON, JSONObject}

//定义样例类封装消息
case class UserScan(
                    var browserType:String,
                    var categoryID:String,
                    var channelID:String,
                    var city:String,
                    var country:String,
                    var entryTime:String,
                    var leaveTime:String,
                    var network:String,
                    var produceID:String,
                    var province:String,
                    var source:String,
                    var userID:String
                   )

//定义message样例类，封装用户访问日志信息
case class Message(
                  var userScan:UserScan,
                  var count:Int,
                  var timestamp:Long
                  )

object UserScan{

  def toBean(jsonContent:String):UserScan={
     //解析json串
       val jSONObject: JSONObject = JSON.parseObject(jsonContent)
       val browserType: String = jSONObject.getString("browserType")
       val categoryID: String = jSONObject.getString("categoryID")
       val channelID: String = jSONObject.getString("channelID")
       val city: String = jSONObject.getString("city")
       val country: String = jSONObject.getString("country")
       val entryTime: String = jSONObject.getString("entryTime")
       val leaveTime: String = jSONObject.getString("leaveTime")
       val network: String = jSONObject.getString("network")
       val produceID: String = jSONObject.getString("produceID")
       val province: String = jSONObject.getString("province")
       val source: String = jSONObject.getString("source")
       val userID: String = jSONObject.getString("userID")
       UserScan(browserType,
                categoryID,
                channelID,
                city,
                country,
                entryTime,
                leaveTime,
                network,
                produceID,
                province,
                source,
                userID
               )
  }
}