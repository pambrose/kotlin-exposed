import com.github.mattbobambrose.vapi4k.Tables.NamesTable
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.postgresql.ds.PGSimpleDataSource
import org.testcontainers.containers.PostgreSQLContainer

class SimpleTest {

  companion object {
    private lateinit var postgresContainer: PostgreSQLContainer<*>
    private lateinit var pgds: PGSimpleDataSource

    @BeforeAll
    @JvmStatic
    fun setup() {
      postgresContainer =
        PostgreSQLContainer("postgres:16.6")
          .apply {
            withDatabaseName("postgres")
            withUsername("postgres")
            withPassword("docker")
            start()
          }

      pgds =
        PGSimpleDataSource().apply {
          setURL(postgresContainer.jdbcUrl)
          user = postgresContainer.username
          password = postgresContainer.password
        }

      Flyway.configure().dataSource(pgds).load().migrate();
    }

    @AfterAll
    @JvmStatic
    fun teardown() {
      postgresContainer.stop()
    }
  }

  @Test
  fun `test inserting and counting names`() {
    Database.connect(pgds)

    transaction {
      repeat(10) {
        NamesTable.insert { it[name] = "John Doe - $it" }
      }
    }

    transaction {
      assertEquals(10, NamesTable.selectAll().count())
      assertEquals(10, NamesTable.selectAll().toList().size)
    }
  }
}