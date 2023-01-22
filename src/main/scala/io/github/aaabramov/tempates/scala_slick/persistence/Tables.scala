package io.github.aaabramov.tempates.scala_slick.persistence

import io.github.aaabramov.tempates.scala_slick.model.{User, UserId}
import io.github.aaabramov.tempates.scala_slick.persistence.PsqlProfile.api._

class UsersTable(tag: Tag) extends Table[User](tag, "users") {

  def name: Rep[String] = column[String]("name")

  def state: Rep[String] = column[String]("state")

  def id: Rep[UserId] = column[UserId]("id", O.PrimaryKey, O.AutoInc)

  override def * =
    (
      name,
      state.?,
      id,
    ).mapTo[User]

}

object Tables {

  val users = TableQuery[UsersTable]

}
