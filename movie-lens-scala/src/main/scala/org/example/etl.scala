package org.example

import com.datastax.spark.connector._
import com.datastax.spark.connector.rdd.reader.RowReaderFactory
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Movie {
  def main(args: Array[String]) {
    //val conf = new SparkConf(true).set("spark.cassandra.connection.host", "localhost")
    //import org.apache.spark.SparkConf
    val sparkConf: SparkConf = new SparkConf().setAppName("SOME APP NAME").setMaster("local").set("spark.cassandra.connection.host", "localhost")
    val sc = new SparkContext(sparkConf)

    val test_spark_rdd = sc.cassandraTable("spark_demo", "raw_files")
    test_spark_rdd.first
  }
}