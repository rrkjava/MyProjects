MECA API 
========

#Build War specific environment properties

mvn clean install (By default dev environment)

mvn clean install -P test   (For test environment) 

mvn clean install -P staging   (For staging environment)

mvn clean install -P prod   (For production environment)
