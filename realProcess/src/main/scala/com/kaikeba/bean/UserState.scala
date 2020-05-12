package com.kaikeba.bean

import java.util.Date

import com.kaikeba.tools.{HbaseUtils, TimeUtils}
import org.apache.commons.lang3.StringUtils
import org.apache.hadoop.hbase.TableName

//定义一个用户状态的样例类
case class UserState(
                    var isNew:Boolean=false,       //是否是新来的用户
                    var isFirstHour:Boolean=false, //是否是该小时第一次来
                    var isFirstDay:Boolean=false,  //是否是该天第一次来
                    var isFirstMonth:Boolean=false //是否是该月第一次来
                    )

/**
  * 通过hbase中的表，记录下用户的状态
  */

object UserState {

    //根据用户的id和时间戳获取用户的状态信息，用户的这些状态信息都会记录到hbase表中
     def getUserState(userId:String,timestamp:Long):UserState={
        //先去hbase表中查看是否有记录
            //定义hbase表中的相关信息
                val tableName: TableName = TableName.valueOf("userState")
                val rowkey=userId
                val columnFamily="info"
                val firstVisitTimeColumn="firstVisitTime"
                val lastVisitTimeColumn="lastVisitTime"
           
            //查询数据 
             val firstVisitTimeData: String = HbaseUtils.getData(tableName,rowkey,columnFamily,firstVisitTimeColumn)
             var isNew:Boolean=false        //是否是新来的用户
             var isFirstHour:Boolean=false  //是否是该小时第一次来
             var isFirstDay:Boolean=false   //是否是该天第一次来
             var isFirstMonth:Boolean=false //是否是该月第一次来

             //判断 如果firstVisitTimeData为null，就说明表没有该用户的记录，该用户为新用户
               if(StringUtils.isBlank(firstVisitTimeData)){
                 //这是该用户的第一次访问
                  try{
                     //组装数据，插入数据到hbase表中
                     var map: Map[String, Long] = Map[String,Long]()
                     map +=(firstVisitTimeColumn -> timestamp)
                     map +=(lastVisitTimeColumn -> timestamp)

                    HbaseUtils.putMapData(tableName,rowkey,columnFamily,map)

                  }catch{
                    case e:Exception =>e.printStackTrace()
                  }
                  //第一次进来,重新赋值这些属性
                 isNew=true
                 isFirstHour=true
                 isFirstDay= true
                 isFirstMonth=true

                UserState(isNew,isFirstHour,isFirstDay,isFirstMonth)

                 //不是新用户，然后进行时间比较
               }else{

                 try {
                   //查看最近一次的访问时间
                   val lastVisitTimeData: String = HbaseUtils.getData(tableName, rowkey, columnFamily, lastVisitTimeColumn)

                   if(StringUtils.isNotBlank(lastVisitTimeData)){
                     val lastVistTime: Long = lastVisitTimeData.toLong
                     //判断
                     if(TimeUtils.compareTo(lastVistTime,timestamp,"yyyyMMddHH")){
                        isFirstHour=true
                     }
                     if(TimeUtils.compareTo(lastVistTime,timestamp,"yyyyMMdd")){
                       isFirstDay=true
                     }
                     if(TimeUtils.compareTo(lastVistTime,timestamp,"yyyyMM")){
                       isFirstMonth=true
                     }
                   }

                   //数据落地到hbase中
                    HbaseUtils.putData(tableName,rowkey,columnFamily,lastVisitTimeColumn,timestamp.toString)
                 }catch {
                   case e:Exception => e.printStackTrace()
                 }

                   UserState(isNew,isFirstHour,isFirstDay,isFirstMonth)
               }

     }



}
