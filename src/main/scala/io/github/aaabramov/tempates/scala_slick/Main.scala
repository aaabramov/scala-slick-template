package io.github.aaabramov.tempates.scala_slick

import com.typesafe.config.ConfigFactory
import io.github.aaabramov.tempates.scala_slick.model.User
import io.github.aaabramov.tempates.scala_slick.persistence.migration.DatabaseMigration
import io.github.aaabramov.tempates.scala_slick.service.UsersService
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.{Await, Future}

object Main {

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.load()
    DatabaseMigration.migrate(config)

    val db = Database.forConfig("psql", config)

    val usersService = new UsersService(db)

    val userName = "Joe Doe"

    val program = for {
      found <- usersService.findByName(userName)
      result <- found
        .map(Future.successful)
        .getOrElse(usersService.insert(User(userName, None)))
    } yield result

    val result = Await.result(program, 10.seconds)

    println(result)

  }

}
