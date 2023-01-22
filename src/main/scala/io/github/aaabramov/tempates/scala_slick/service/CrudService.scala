package io.github.aaabramov.tempates.scala_slick.service

import io.github.aaabramov.tempates.scala_slick.persistence.PsqlProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
import PsqlProfile.api._
import com.typesafe.scalalogging.StrictLogging

trait CrudService extends StrictLogging {
  val db: PsqlProfile.Database
  implicit val ec: ExecutionContext

  protected def exec[R](action: slick.dbio.DBIOAction[R, NoStream, Nothing]): Future[R] =
    db
      .run(action)
      .andThen {
        case Success(value) =>
        case Failure(ex) =>
          logger.error(s"Error during database query/action: ${ex.getMessage}", ex)
      }
}
