package io.github.aaabramov.tempates.scala_slick.persistence.migration

import com.typesafe.config.Config
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory

object DatabaseMigration {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def migrate(config: Config): Unit = {

    val server = config.getString("psql.properties.serverName")
    val port   = config.getInt("psql.properties.portNumber")
    val db     = config.getString("psql.properties.databaseName")

    val user = config.getString("psql.properties.user")
    val pass = config.getString("psql.properties.password")

    logger.info("Connecting for migration...")

    val flyway = Flyway
      .configure()
      .dataSource(s"jdbc:postgresql://$server:$port/$db", user, pass)
      .load()

    logger.info("Gonna migrate...")

    val result = flyway.migrate()

    logger.info("Migration done...")
    result.migrations.forEach(m => logger.info(m.toString))

  }

}
