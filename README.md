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

# Environment Bean (not recommended)
* To look up profiles or properties into in any Class
* Just @Autowired Environment class

# Config as a microservice (Goal 3 & 4)
* Create a microservice just for providing config to all microservices and all instances of each service.
* This helps making all services be consistent as far as configuration is concerned.
* Options to create a config service
  * Apache Zookeeper
  * ETCD - distributed key-value store
  * Hashicorp Consul
  * Spring Cloud Configuration Server (recommended in spring-boot env)
  
# Spring Cloud Config Server
* It can connect to Git Repo and listens to changes committed.
* Connecting to Git also helps maintain version history
* Create spring-boot app with Config Server as dependency
* Add @EnableConfigServer to Main class
* See spring-boot-config-server repo for implementation and once completed follow below steps

# Spring Cloud Config Client
* Add spring-cloud-starter-config dependency, spring-cloud.version property and dependencies management tag from spring initializer pom.xml
* Add config-server uri in YAML file by setting spring.cloud.config.uri=http://localhost:8888/
* Configure spring-boot-config-server to supply application specific config by just creating another yml file in git repo with value same as of spring.application.name property e.g. spring-boot-config-client in this case.

# Refresh Properties w/o client app restart
* Changing and committing config file in gitrepo doesn't publish new values to config-client because client loads the properties during boot only.
* config-client needs to be restarted to get new config file changes from config-server
* To avoid restart of client follow below steps:
  * Have actuator dependency in pom.xml
  * Add @RefreshScope to Classes/Beans which needs to refresh their dependencies when a refresh is triggered
  * Make config change to git repo file
  * Make a POST call to http://localhost:8080/actuator/refresh to trigger the refresh
  * Post call will return response telling which properties were refreshed

# Auto refresh config for multiple instances of a service
1. we have one concept called spring cloud bus where all the instances will be listening to a queue(like RabbitMQ) and if any one instance calls refresh url a message will be send to Rabbit MQ and all other instances will listen to message do refresh automatically.. By this method if we do refresh call from any one instance is enough, automatically other instances will be refreshed.
2. Web-hook along with spring cloud bus: In this we need to configure spring-cloud-config-server url(/monitor) as a web-hook in git repository setting, when ever a commit is made automatically this web-hook will be called and spring-cloud-config server will send a message to RabbitMQ and all the instances listening to this Queue will refresh their properties automatically. This we does not need to even do a single refresh on any instance.

# Microservices configuration best practices
* Service specific config which is not expected to change should not go in config  server
* Service specific config which is expected to change should go in config  server
* Try to use default values for each property for dev env using ${actual_value : default_value} to quickly start service in dev.
* Config server can be secured using Spring Security
* Credentials/Sensitive info in git should be stored in ENCRYPTED form. Use JCE(Java Cryptography Extension) on config server to encrypt/decrypt
