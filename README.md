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
* Injecting key-value pairs using #{ _${key}_ } and keeping variable type as Map

# @ConfigurationProperties
* Maps multiple properties in single java object. Use @Configuration on top to make it a spring bean. @Autowired then.
* Takes prefix of all properties as an input e.g. @ConfigurationProperties("db") will map all properties having db as key prefix

# Actuator ConfigProps Endpoint
* To identify spring-boot default config keys & then override the values using application.properties.
* Add starter-actuator dependency and expose all endpoints using management.endpoints.web.exposure.include=* property
* Access http://localhost:8080/actuator/configprops to find spring config key and then override.

# YAML Configuration
* Less verbose; Use spaces instead of tabs; colon : in place of = 

# Spring Profiles: Env Specific Config (Goal 2) 
* Profile is preset configuration values. Spring uses default profile if not specified
* Convention to create profile application-<profile-name>.extn e.g. application-test.yml
* Put every profile in parallel to main file. Keep common properties in default profile.
* Set spring.profiles.active=profile-name in main properties file to always override default profile
* Choose profile while running java -jar app.jar --spring.profiles.active=test
* Multiple profiles can be active at once e.g. --spring.profiles.active=test,test-qa1
* test-q1 takes the precedence here; default profile is always active
* Use @Profile("profile-name") at Class level to tell spring-boot which bean to initialize based on active profile.
* @Profile("default") is always there if not explicitly specified
