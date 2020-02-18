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

@Value to inject property value in a Class using ${"propertyKey"} syntax. This syntax can be used to refer another property.
