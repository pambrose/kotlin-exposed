buildscript {
  dependencies {
    classpath("org.flywaydb:flyway-core:11.1.0")
    classpath("org.flywaydb:flyway-database-postgresql:11.1.0")
  }
}

plugins {
  kotlin("jvm") version "2.1.20-Beta1"
  id("org.flywaydb.flyway") version "11.1.0"
  id("com.github.ben-manes.versions") version "0.51.0"
}

group = "com.github.mattbobambrose.vapi4k"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val exposedVersion: String by project
val flywayVersion: String by project
val hikariVersion: String by project
val loggingVersion: String by project
val logbackVersion: String by project
val pgjdbcVersion: String by project
val postgresVersion: String by project
val testcontainersVersion: String by project

dependencies {
  implementation("org.postgresql:postgresql:$postgresVersion")
  implementation("com.impossibl.pgjdbc-ng:pgjdbc-ng-all:$pgjdbcVersion")
  implementation("com.zaxxer:HikariCP:$hikariVersion")

  implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")

  implementation("io.github.oshai:kotlin-logging-jvm:$loggingVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")

  testImplementation("org.flywaydb:flyway-core:$flywayVersion")
  testImplementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")

  testImplementation(kotlin("test"))
  testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
  testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(17)
}

val testJdbcUrl = providers.environmentVariable("JDBC_URL").orElse("jdbc:postgresql://localhost:5433/postgres").get()
val databaseUsername = providers.environmentVariable("DB_USERNAME").orElse("postgres").get()
val databasePassword = providers.environmentVariable("DB_PASSWORD").orElse("docker").get()

flyway {
  url = testJdbcUrl
  user = databaseUsername
  password = databasePassword
  schemas = arrayOf("schema1")
  defaultSchema = "schema1"
  cleanDisabled = true
}

//tasks.named("flywayMigrate") {
//    inputs.files("src/main/resources/db/migration")
//}
