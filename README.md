# 603
Steps to use this repository: <br />

##Github
Down load the source code from Github
Extract the file<br />
database.sql and react folder will be directed in the following steps.

##Mysql
Download Mysql, (Mysql Version above 5.6)<br />
Install mysql, set password (ask the admin)<br />
Open MySQL command Line Client, input the password.<br />
Input the following commands to import the database:<br />
mysql> create DATABASE greenleaves;<br />
mysql> use greenleaves;<br />
mysql> source C:/database.sql (The path to database.sql)<br />
mysql> select * from gl_member;<br />
You will see a table with registered member if every step goes well:<br />

##Eclipse
Import the downloaded project ......\2DV603-master\greenleaves<br />
Run BaseApplication.java in the main folder<br />
You will see the Spring symbol (V2.1.4) without errors if every step goes well:<br />

##Cmd
Install [Node.js and NPM](https://nodejs.org/en/). <br />
Download Node.js, open cmd and input the following commands:<br />
Cd .....\603\2DV603-master\react (The path to react)<br />
Npm install  <br />
Npm start <br />

For now, localhost:8080 and localhost:3000 should be available.
