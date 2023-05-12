package utils.DBUtils

import perycreep.Main.databaseName
import slick.jdbc.JdbcBackend.Database

object DBUtils {
  val db = Database.forConfig(databaseName)
}
