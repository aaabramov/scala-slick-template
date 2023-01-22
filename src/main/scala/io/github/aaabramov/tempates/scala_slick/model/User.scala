package io.github.aaabramov.tempates.scala_slick.model

case class UserId(value: Long) extends AnyVal

case class User(name: String, state: Option[String], id: UserId = UserId(0L))
