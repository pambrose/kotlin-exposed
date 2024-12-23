default: versioncheck

dbclean:
	./gradlew flywayClean

dbinfo:
	./gradlew flywayInfo

dbvalidate:
	./gradlew flywayValidate

dbmigrate:
	./gradlew flywayMigrate

cli-info-test:
	flyway -environment=testing info

cli-info-prod:
	flyway -environment=prod info

versioncheck:
	./gradlew dependencyUpdates

upgrade-wrapper:
	./gradlew wrapper --gradle-version=8.12 --distribution-type=bin
