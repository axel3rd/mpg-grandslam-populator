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

# Database connection
spring.datasource.url=jdbc:mysql://localhost:3306/db
spring.datasource.username=foo
spring.datasource.password=bar


# Temporary display all SQL queries
#spring.jpa.properties.hibernate.format_sql=true
#logging.level.org.hibernate.SQL=DEBUG

```