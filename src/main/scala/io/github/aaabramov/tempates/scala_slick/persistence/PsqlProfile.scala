package io.github.aaabramov.tempates.scala_slick.persistence

import com.github.tminglei.slickpg.{ExPostgresProfile, PgCirceJsonSupport}
import io.github.aaabramov.tempates.scala_slick.model.UserId

trait PsqlProfile extends ExPostgresProfile with PgCirceJsonSupport {

  override val api: PsqlAPI.type = PsqlAPI

  override def pgjson: String = "jsonb"

  object PsqlAPI extends API with JsonImplicits {
    implicit val userIdIdCol: BaseColumnType[UserId] =
      MappedColumnType.base[UserId, Long](_.value, UserId)
  }

  type Database = api.Database

}

object PsqlProfile extends PsqlProfile
