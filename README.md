[![CI Build](https://github.com/axel3rd/mpg-grandslam-populator/workflows/CI%20Build/badge.svg)](https://github.com/axel3rd/mpg-grandslam-populator/actions?query=workflow%3A%22CI+Build%22) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=org.blonding.mpg%3Agrandslam-populator-batch&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.blonding.mpg%3Agrandslam-populator-batch) [![Security Analysis](https://github.com/axel3rd/mpg-grandslam-populator/workflows/Security%20Analysis/badge.svg)](https://github.com/axel3rd/mpg-grandslam-populator/actions?query=workflow%3A%22Security+Analysis%22)

# MPG GrandSlam Populator

Routines to populate [MPG GrandSlam](https://github.com/mansuydejean/mpggrandslam) database.

## Usage

1. Prerequisite: [java](https://www.java.com/fr/download/) should be installed and on your PATH.
1. Download package (via Release button on right of this page).
1. Provide `application.properties` file (see below for details).
1. Execute:

```
java -jar grandslam-populator-batch-x.y.jar
```

## Configuration

File `application.properties` (commented option are optional):

```
# MPG credentials
mpg.email = foo.bar@gmail.com
mpg.password = KXXXXXXXU

# Database connection
spring.datasource.url = jdbc:mysql://localhost:3306/db
spring.datasource.username = foo
spring.datasource.password = bar

# Only check MPG data to evaluate leagues/users includes/excludes.
# Use '--job.check.only=true' as command line parameter
#job.check.only = true

# Include/exclude leagues
#mpg.leagues.include = KX24XMUJ,KLGXSSUM
#mpg.leagues.exclude = LJT3FXDX

# Include/exclude users (MPG id) ; by default users intersection of leagues is used
#mpg.users.include = 955966
#mpg.users.exclude = 1570437,2237823




# Default logs
#logging.level.root=WARN
#logging.level.o.s.batch.core.job.SimpleStepHandler=INFO
#logging.level.org.blonding.mpg=INFO

# Temporary display all SQL queries
#spring.jpa.properties.hibernate.format_sql = true
#logging.level.org.hibernate.SQL = DEBUG
```

## Build process

**Prerequisite**:

1. [Java](https://www.java.com/fr/download/)
1. [Maven](https://maven.apache.org/)

**Execute**:

```
mvn package
```

**Release**:

```
git reset --hard origin/main 
git branch -m next-version 
mvn -B clean release:clean release:prepare -Dusername=yourGitHubLogin -Dpassword=yourGitHubPasswordOrToken
```

After that, you would have to create pull-request from `next-version` branch and rebase it on `main` for next version development.
