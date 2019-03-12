# selenium-testng-pom-sample
Automating a couple GMail tests with Java, TestNG, Selenium, and Page Object Model


To Run
1. Pull down the code: git clone https://github.com/wonkas-factory/selenium-testng-pom-sample.git
1. cd to selenium-testng-pom-sample
1. Update .config/test_data.yml with valid GMail credentials
1. Gmail API credentials
    1. Get credential.json file here: https://developers.google.com/gmail/api/quickstart/java 
    1. Rename credentials.json file to {user}_credentials.json ex Alice or Bob
    1. Place json files in in src/main/resources/
    1. Tokens will be placed in ./token/{user}/StoredCredential once authenticated
1. Run 'mvn clean test'
1. Video of 2 tests running: https://youtu.be/QPoNCU15r14

