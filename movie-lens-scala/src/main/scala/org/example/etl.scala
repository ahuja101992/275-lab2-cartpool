package main.scala.org.example

import com.datastax.spark.connector._
import com.datastax.spark.connector.rdd.reader.RowReaderFactory
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object etl {
  case class RawFileData(Filename: String, Line: Int, Contents: String )
  case class User(Id: Int, Gender: String, Age: Int, Occupation: Int, ZipCode: String )
  case class Movie(Id: String, Title: String, Genres: String)
  case class Rating(UserId: Int, MovieId: Int, Rating: Float)

  def main(args: Array[String]) {
    val sparkConf: SparkConf = new SparkConf().setAppName("SOME APP NAME").setMaster("local").set("spark.cassandra.connection.host", "localhost")
    val sc = new SparkContext(sparkConf)

    val movies = sc.cassandraTable[Movie]("spark_demo", "movies")
    val top_10 = movies.groupBy( x => x.Genres ).map( x => (x._1)).top(10)
    top_10.take(10).foreach(println)
    //println(top_10.toString())

    //val raw_files = sc.cassandraTable[RawFileData]("spark_demo", "raw_files" )

//    val users = raw_files.filter( raw => raw.Filename == "users.dat" ).
//      map(raw => raw.Contents.trim).map( raw => raw.split("::")).
//      map(raw=>User(raw(0).toInt, raw(1), raw(2).toInt, raw(3).toInt, raw(4)))
//
//    users.saveToCassandra("spark_demo", "users")

//    val movies = raw_files.filter( raw => raw.Filename == "movies.csv" ).
//      map(raw => raw.Contents.trim).map( raw => raw.split(",")).
//      map(raw=>Movie(raw(0),raw(1),raw(2)))
//
//    movies.saveToCassandra("spark_demo", "movies")

//    val ratings = raw_files.filter( raw => raw.Filename == "ratings.dat" ).
//      map(raw => raw.Contents.trim).map( raw => raw.split("::")).
//      map(raw=>Rating(raw(0).toInt,raw(1).toInt,raw(2).toFloat))
//
//    ratings.saveToCassandra("spark_demo", "ratings")
  }
}