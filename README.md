Chat
====
This is a web based XMPP chat client with basic security labelling within the XEP-0258 spec (using hardcoded security labels rather than discovering the catalog).

Building
--------
To build the chat client package run the following:

	cd chat_client
	mvn clean package
	
Installation
------------
1. `unzip client-xxx-build.zip`
2. `cd build`
3. `chmod u+x build.sh`
4. `dos2unix build*`
5. Edit `build.properties` file and customise as required.
6. `cp *.war chat.war`
7. `./build.sh build.properties`
8. Load `chat.war` into your applcation server.

If the build customisation fails, delete `chat.war` and repeat step 6 onwards.