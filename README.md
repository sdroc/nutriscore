# Nutriscore
# Purpose
The purpose is to create a reusable nutriScore component
It's a demonstration of swing usage
# Environment 
Use netbeans and Jdk 8
# Architecture
Architecture is split in different folder as:
* ui:for graphical component
* service: for service component instanciate at application start (can use springboot)
* domain: for business object used in application
# Design
The main application create a Frame NutriScroFrame with:
* NutriScore that display the law of nutri score
* NutriScoreStep represents different step
* SelectedNutriScoreStep represent the selection component

NutriScore use a rootPane with gridLayout.

The glasspane is used to display the selection component (SelectedNutriScoreStep)

In other part, for events ans models use classical pattern of swing with
selection model bean and listener when selection change
for glasspane, not use layout because the select position change. Compute the bounds
of selected. Allow to create also soft transition when nutriscre change.

For Nutri score service, use a stream supplied by the response of http request every 10s. If connection lost, the stream is not supplied and blocked until request success 

#Improvements
* Manage most calculation on component resize
* Manage size of corner  on component resize
* Use api random


# Build
Install maven tool for build application (http://maven.apache.org/download.cgi)

Create executable jar

mvn clean package

Create distribution

Distribution can be created for linux and/or windows target

mvn install -Plinux,windows

Note: build with Maven version 3.3.6
# Unitary test
JUnit used to check NutriScoreSelectionModel
# Install
Copy the result of target folder nutriscore-1.0-windows.zip or nutriscore-1.0-linux.tar.gz and decompress

# Use
Set JAVA_HOME to jre 8 or higher

Run the run.sh for Linux or run.bat for Windows

Use key A to disable/enable soft score transition
