# bsf-accounts-service
is a Spring boot Service with Java 11 which is designed to transfer amount between the accounts and retrieving account by number
## Workflow
 * API to transfer money amount from account to another
 * API to retrieve account by account number

 ## How to run bsf-accounts-service
 * Service require Java 11

                curl -s "https://get.sdkman.io" | bash
        		source "$HOME/.sdkman/bin/sdkman-init.sh"
        		sdk version
        		sdk install java 11.0.11.j9-adpt
        		java -version
 * Go to the project root path and run the following command to compile and run project
      mvn -N io.takari:maven:wrapper
     ./mvnw package && java -jar target/bsf-accounts-service-0.0.1-SNAPSHOT.jar
 * bsf-accounts-service can be build using docker to run it , go to the project root path and run the following command

       mvn clean install
       docker build -t bsf/bsf-accounts-service .
       docker run -p 8080:8080 bsf/bsf-accounts-service

 ## Service CURLs
 * To transfer from account to another

        curl -X POST "http://localhost:8080/v1/api/transfers" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"amount\": 2000, \"fromAccount\": \"12111456798765510\", \"toAccount\": \"10211456798765135\"}"
 * To retrieve account by account number

        curl -X GET "http://localhost:8080/v1/api/accounts/12111456798765510" -H "accept: */*"

        Response:
        {
          "number": "12111456798765510",
          "ownerName": "ABANOUB NASSER",
          "bankName": "ADCB",
          "balance": 23000
        }
 ## URLs
 Swagger URL: http://localhost:8080/v1/swagger-ui/index.html
 H2 console URL : http://localhost:8080/v1/h2
