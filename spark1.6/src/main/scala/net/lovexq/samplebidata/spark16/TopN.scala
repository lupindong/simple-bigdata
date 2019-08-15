package net.lovexq.samplebidata.spark16

import net.lovexq.samplebidata.AppUtil
import org.apache.spark.SparkContext

/**
  * TOP N 求解
  *
  * @author LuPindong
  * @time 2019-08-08 17:18
  */
object TopN {

  def main(args: Array[String]): Unit = {

    val conf = AppUtil.getSparkConf("TopN Application")
    val sc = new SparkContext(conf)

    val filePath = "file:///D:/home/lovexq/cloudera/topn"

    val dataRDD = sc.textFile(filePath).cache

    var n = 5

    if (!args.isEmpty) {
      n = args(0).toInt
    }

    println(s"n is ${n}")

    dataRDD
      // 过滤出非空行，且有四列的数据
      .filter(e => e != null && !e.trim.isEmpty && e.trim.split(",").length == 4)
      // 按逗号分割每行数据，取payment的值
      .map(_.trim.split(",")(2))
      .map((_, 1))
      .sortByKey(false)
      .take(n)
      .foreach(println)

    sc.stop()
  }
}
