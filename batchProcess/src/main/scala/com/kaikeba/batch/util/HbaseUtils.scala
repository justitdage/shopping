package com.kaikeba.batch.util

import java.util

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}

/**
  * 开发hbase的工具类
  */
object HbaseUtils {
     //1、获取配置对象
    private val conf: Configuration = HBaseConfiguration.create()
     //2、设置配置参数
    conf.set("hbase.zookeeper.quorum",GlobalConfigUtil.hbaseZookeeperQuorum)
     //3、获取hbase数据库连接
    private val conn: Connection = ConnectionFactory.createConnection(conf)

     //4、获取admin对象
     private val admin: Admin = conn.getAdmin


  /**
    * 创建表的方法
    * @param tableName
    * @param columnFamily
    * @return
    */
     def createTable(tableName:TableName,columnFamily:String):Table={
        //1、构建表的描述器
           val tableDescriptor = new HTableDescriptor(tableName)

        //2、构建列族描述器
            val columnDescriptor = new HColumnDescriptor(columnFamily)

         // 关联表描述器与列族描述器
          tableDescriptor.addFamily(columnDescriptor)

       //3、表不存在就创建表
       this.synchronized{
           if(!admin.tableExists(tableName)){
               admin.createTable(tableDescriptor)
          }
       }


       conn.getTable(tableName)

  }

  /**
    * 查询数据
    * @param tableName
    * @param rowkey
    * @param columnFamily
    * @param column
    * @return
    */
   def getData(tableName: TableName,rowkey:String,columnFamily:String,column:String):String={
     //获取table对象
     val table: Table = createTable(tableName,columnFamily)
     var content=""
     try{
         val rowkeyBytes: Array[Byte] = Bytes.toBytes(rowkey)
         val get = new Get(rowkeyBytes)
         val result: Result = table.get(get)
         val value: Array[Byte] = result.getValue(Bytes.toBytes(columnFamily),Bytes.toBytes(column))
         if(value != null && value.size >0){
           content= Bytes.toString(value)
         }

     }catch {
       case e:Exception => e.printStackTrace()
     }finally {
        table.close()
     }

     content

   }

  /**
    * 插入数据  一条条插入
    * @param tableName
    * @param rowkey
    * @param columnFamily
    * @param column
    * @param data
    */
    def putData(tableName: TableName,rowkey:String,columnFamily:String,column:String,data:String): Unit ={
         val table: Table = createTable(tableName,columnFamily)
         try {
           val put = new Put(rowkey.getBytes)
           put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(data))
           table.put(put)
         }catch {
           case e:Exception =>e.printStackTrace()
         }finally {
           table.close()
         }
    }

  /**
    * map封装数据插入
    * @param tableName
    * @param rowkey
    * @param columnFamily
    * @param map
    */
  def putMapData(tableName: TableName,rowkey:String,columnFamily:String,map:Map[String,Object]): Unit ={
    val table: Table = createTable(tableName,columnFamily)
    try {
       val put = new Put(rowkey.getBytes)
       val puts = new util.ArrayList[Put]()

       //遍历map查询数据
      if(map.size >0){
        for((k,v) <- map){
          put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(k.toString), Bytes.toBytes(v.toString))
          puts.add(put)
        }

        table.put(puts)
      }

    }catch {
      case e:Exception =>e.printStackTrace()
    }finally {
      table.close()

    }
  }

  /**
    * 删除表
    * @param tableName
    */
  def dropTable(tableName: TableName)={
    try {

      if (admin.tableExists(tableName)) {
        admin.disableTable(tableName)
        admin.deleteTable(tableName)
      }
    }catch {
      case e:Exception =>e.printStackTrace()
    }finally {
      admin.close()
    }
  }


  def main(args: Array[String]): Unit = {
       //putData(TableName.valueOf("test"),"123","info","message","this is a test")
       //println(getData(TableName.valueOf("test"),"123","info","message"))


  }
}
