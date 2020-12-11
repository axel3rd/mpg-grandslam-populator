# MPG GrandSlam Populator

Routines to populate [MPG GrandSlam](https://github.com/mansuydejean/mpggrandslam) database.

## Configuration

File `application.properties`

```
# MPG credentials
mpg.email = foo.bar@gmail.com
mpg.password = KXXXXXXXU

# Include/exclude leagues
mpg.leagues.include = KX24XMUJ,KLGXSSUM
mpg.leagues.exclude = LJT3FXDX

# Include/exclude users (MPG id) ; by default users intersection of leagues is used
mpg.users.include = 955966
mpg.users.exclude=1570437,2237823

# Database connection
spring.datasource.url = jdbc:mysql://localhost:3306/db
spring.datasource.username = foo
spring.datasource.password = bar

# Only check MPG data to evaluate leagues/users includes/excludes.
# Use '--job.check.only=true' as command line parameter
#job.check.only = true

# Temporary display all SQL queries
#spring.jpa.properties.hibernate.format_sql = true
#logging.level.org.hibernate.SQL = DEBUG

```