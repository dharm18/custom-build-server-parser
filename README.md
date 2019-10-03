# custom-build-server-parser

To run this project.
1. Start HSQLDB from data folder. For instance,
  java -cp ..\lib\hsqldb.jar org.hsqldb.server.Server --database.0 file:logdb/logdb --dbname.0 logdb

![Alt text](https://github.com/dharm18/dharm18.github.io/blob/master/images/server-build-loge-parser-db.PNG?raw=true "HSQLDB instance running in file based server mode.")

2. LogParser class has main method which takes filepath argument and process the file.

3. LogParserTest file contains unit test cases to support the logic
