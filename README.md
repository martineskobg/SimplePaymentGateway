# QA sanity check application for Payment transaction gateway

### Description
This project was created to test stability of a RoR (ruby on rails)
**Payment transaction gateway** application.

#### Practical tasks:
**Task 1**\
**Description**: Prepare a Java console application that is able to perform sanity checks
to the Sanity checks Application:
1. It should be executed from the terminal
2. It should either include a configuration file or accept additional parameters like endpoint, transaction types


**Task 2**\
**Description**: It should ensure the API is working properly by performing different kind of requests
1. send a valid payment transaction request and expect an approved response.
2. send a valid void transaction request and expect an approved response.
3. send a valid payment transaction with an invalid authentication and expect an appropriate response (401 etc)
4. send a void transaction pointing to a non-existent payment transaction and expect 422 etc
5. send a void transaction pointing to an existent void transaction and expect 422 etc


**Task 3**\
**Description**:Create a proper structure of the project. It should be easily extendable with new methods, requests and etc.


**Task 4**\
**Description**:Cover the functionality of the sanity checks app with unit tests/specs using JUnit or similar testing framework

### Instruction
Create a standard Maven project
Then copy all dependencies from the pom.xml file in this project
If you have already run Payment transaction API on your PC, you should be able
to run the project and test the API.

#### Prerequisites for Payment transaction API:
Set up the Payment Gateway API locally using this github repo
1. https://github.com/eMerchantPay/codemonsters_api_full

##### Useful links:
1. https://www.baeldung.com/java-httpclient-basic-auth
2. https://mkyong.com/java/java-properties-file-examples/
3. https://www.tabnine.com/code/java/methods/com.google.gson.JsonObject/add
4. https://mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api