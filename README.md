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
#mpg.users.exclude=1570437,2237823

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
