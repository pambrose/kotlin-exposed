package com.github.mattbobambrose.vapi4k

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Tables {
  object NamesTable : IntIdTable("schema1.names", "primary_id") {
    val created = datetime("created")
    val name = text("name")
  }
}