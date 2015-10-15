# BankAccountNumber-sampleservice
#
# Author: vpotry, Oct 2015

Sample restful service for old-style (BBAN) bank account number validation and test-creation
Build with gradle version 2.3 (but should work fine with newer versions).
Reguires JDK 1.7 or higher

################
# Description  #
################

Provides simple Spring/rest controller (BankAccountController) with /validate/{account} and /newaccount services
Returns json-string based on BankAccountNumber domain-class

Project structure:
  [src/main/java]
      *sample.account
        .configuration
            <Spring bean configuration class(es)>
        .controller
            <Spring rest-controller class(es)>
        .domain
            <Domain class(es)>
        .service
            <Service class(es) to use  from Contorller>
        .main
            <Spring boot-application startup class>
        .util
            <Helper/Util classes>

  build.gradle # gradle build file
  gradlew/gradlew.bat # gradle wrarpper for building project
  [gradle]
    <gradle-wrapper files>
    
###################################
# How to build (JDK 1.7 required) #  
###################################
 * With gradle 2.3 or newer
   > gradle clean build
   
 * With gradle wrapper (Downloads https://services.gradle.org/distributions/gradle-2.3-bin.zip at 1st run, may take minute or two... )
   > gradlew.bat build
   
 * Build result (case BUILD SUCCESSFUL):
   Runnable jar should be found in: build\libs\accountsample.jar
   
  * Build result (case BUILD FAILED):
    Check JAVA_HOME environmental variable - it should point to JDK 1.7 directory 

    
##############
# How to run #
##############

Application runs on spring-boot default servlet engine: Apache Tomcat/8.0.26 @default port 8080
To start application verify that you have Java version >= 1.7

 * Started from command line: 
  > java -jar accountsample.jar

 * If startup fails check these at first:
   - java -version -> 1.7 (or higher)
   - Is port 8080 already reserved?
   
  
# URLs to access rest-service:
 Validation: http://<HOST>:8080/validate/some-bank-account-number
  Examples: 
    http://localhost:8080/validate/34647500000789
    http://localhost:8080/validate/346475-789
 
 Random-bankaccount number generation: http://<HOST>:8080/newaccount
 