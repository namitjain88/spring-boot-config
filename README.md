# spring-boot-config
A sample project to understand spring boot configuration and it's uses
# Example Configuration
* Database Connection Strings
* Credentials e.g. file share, S3
* Feature Flags to turn on/off a feature e.g. b/w 8PM to 11PM
* Business Login Configuration Parameters e.g. offering a temp discount
* Scenario Testing e.g. 10% traffic to scenario-A and rest to scenario-B
* Spring Boot Configuration
# Ways to provide configuration
* Properties
* YAML
* JSON
* XML Based (Not Recommended)
# Why do we need configuration?
* Multiple Microservices
* Multiple instance of each microservice
# Goals of configuration
* Externalized (should not part of code/jar)
* Environment specific (e.g. different db in dev, test and prod)
* Consistent (all copies should refer same configuration in an env)
* Version History (e.g. commit history if config files are part of code)
* Real-Time management

# @Value
@Value to inject property value in a Class using ${"propertyKey"} syntax. This syntax can be used to refer another property.
Note: application.properties doesn't help in externalizing configuration as it is part of jar.

To externalize/override properties, create another application.properties file in same path as of jar. This will override common property value with of external file.

Override via command line argument while executing jar e.g java -jar app.jar --my.greeting="Hello world from command line argument"

Override precedence: command line > application.properties w/ jar > application.properties IN jar.

# 3 @Value tricks
* colon (:) to supply default value when actual key/value missing in properties file. e.g. @Value("${main value : default value}")
* Injecting list of values in java List<> type variable. e.g. my.list.values=One,Two,Three then use @Value w/ List of String