package io.github.aaabramov.tempates.scala_slick.service

import io.github.aaabramov.tempates.scala_slick.model.{User, UserId}
import io.github.aaabramov.tempates.scala_slick.persistence.PsqlProfile
import io.github.aaabramov.tempates.scala_slick.util.Mutation

import scala.concurrent.{ExecutionContext, Future}

class UsersService(
  override val db: PsqlProfile.Database
)(override implicit val ec: ExecutionContext)
    extends CrudService {

  import PsqlProfile.api._
  import io.github.aaabramov.tempates.scala_slick.persistence.Tables.users

  private val insertQuery =
    users returning users.map(_.id) into ((user, newId) => user.copy(id = newId))

  def insert(user: User): Future[User] = {
    val action = for {
      exists <- insertQuery += user
    } yield exists

    exec(action)
  }

  def findOne(id: UserId): Future[Option[User]] = {
    val query = users.filter(_.id === id).result.headOption

    exec(query)
  }

  def findByName(name: String): Future[Option[User]] = {
    val query = users.filter(_.name === name).result.headOption

    exec(query)
  }

  def update(id: UserId)(mutation: Mutation[User]): Future[User] = {
    logger.debug(s"Update owner by id: $id")

    val action = for {
      entity <- users.filter(_.id === id).forUpdate.result.headOption
      updated <- entity.map(mutation) match {
        case Some(updated) =>
          users
            .filter(_.id === id)
            .update(updated)
            .map(_ => updated)

        case _ =>
          DBIO.failed(new RuntimeException(s"No such user with id=$id"))
      }
    } yield updated

    exec {
      action.transactionally
    }
  }

}
