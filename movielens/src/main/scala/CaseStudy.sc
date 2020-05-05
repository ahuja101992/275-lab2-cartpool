import com.datastax.spark.connector._
import org.apache.spark.SparkContext, org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object CaseStudy {

  def main(args: Array[String]) {
    val conf = new SparkConf(true).set("spark.cassandra.connection.host", "localhost")
    val sc = new SparkContext(conf)

    val test_spark_rdd = sc.cassandraTable("spark_demo", "raw_files")
    test_spark_rdd.first
  }
}