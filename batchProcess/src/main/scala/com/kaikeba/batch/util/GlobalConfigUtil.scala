package com.kaikeba.batch.util

import com.typesafe.config.{Config, ConfigFactory}

/**
  * 加载配置文件工具类
  */
object GlobalConfigUtil {

  private val config: Config = ConfigFactory.load()

  def hbaseZookeeperQuorum = config.getString("hbase.zookeeper.quorum")
  def main(args: Array[String]): Unit = {

    println(hbaseZookeeperQuorum)

  }
}
