# Redis-poc


In the this application there are sample operation with redis with diffrent datatypes of redis using spring boot(JAVA) and Web-API to savestring to redis db and getkeys from our redis db .

  - String
  - Hash
  - SET
  - SORTED SET
  - LIST
  
above are the data type on which I have done crude operation.

# Prerequisite:

  - Redis server running on localhost
  - IDE- Spring-tool-Suite(STS) or eclipse


Application will Start from the class:
  -  [SpringPocApplication]  this class will Start the Spring boot application on port 8080 in main method there are multiple method call for all crude operation,and what will happen by the method call which is present in the [RedisOperations] class in all the methods are written with all the comments which describe the funcationalty of the method.
  - [RedisController] class in this class  two api endpoint present which  
  - 1) is saving key value pair as String
  - 2) to get keys present in your redis db
  
When Application successfully started open it the browser Swagger-Ui  http://localhost:8080/Swagger-ui.html in RedisController.

 [SpringPocApplication]: <https://github.com/Abhijeet-Behare/redis-poc/blob/master/redis-poc/src/main/java/com/abhijeet/poc/RedisPocApplication.java>
   [RedisOperations]: <https://github.com/Abhijeet-Behare/redis-poc/blob/master/redis-poc/src/main/java/com/abhijeet/poc/RedisOperations.java>
   [RedisController]: <https://github.com/Abhijeet-Behare/redis-poc/blob/master/redis-poc/src/main/java/com/abhijeet/poc/controller/RedisController.java>
   
  
  
