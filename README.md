
README - OFX (Offline Exchange)


What is it?
-----------
OFX is a graphical application for buying-selling of second-hand items, inspired by OLX.in

Detailed Description
--------------------
OFX is a graphical application for buying and selling second-hand items.
You can register on the portal, login, add items, put them on sale, edit them.
You can browse items added by others, search and buy.
You receive notifications when someone buys your item, along with their contact details, so you can connect with buyers in real life.

Features
--------
* Browse recently added items
* Edit items added by you
* View details about items on sale, Seller details 
* Receive relevant notifications for each item sold, with option to view Buyer's profile
* Edit your profile
* Select items from a diverse range of categories
* Search for items using Basic or Advanced Search
* Strike a deal with another person 
* Register, login and forgot password features
* Cross-platform software: server/client modules run on all platforms supporting Java

System Requirements
-------------------
(Operating system) Microsoft Windows 7 (or above), Java Runtime Environment (JRE) (version 7+)

Downloading
-----------
The latest version on OFX can be obtained by downloading from the official project page at SourceForge (link): <https://sourceforge.net/projects/OFX/>. You can download the source by cloning the project repository from the following link: <git@github.com:avaneeshrastogi/OFX.git>.

Installation
------------
Check if you have Java Runtime Environment (JRE) installed on your system:
- Open the command prompt. Follow the menu path Start > Programs > Accessories > Command Prompt
- Type: java -version and press Enter on your keyboard.
- Result: A message similar to the following indicates that Java is installed and you are ready to use OFX via the Java Runtime Environment
                          Java version "1.4.x_xx" (or a higher version)
                          Java<TM> SE Runtime Environment <build .....>
                          Java hotspot<TM> Client VM <build .....>
- However, if you receive a message with a version lower than "1.4", or any type of error message, or a message similar to:
                          Java command not found
Then you will need to install the Java Runtime Environment on your desktop.

Installing Java Runtime Environment (JRE)
- Visit Oracle's page to download the latest JRE <http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html>.
- Accept the License Agreement.
- Select your operating system and architecture.
- Download the file.
- Execute the downloaded file and follow instructions to install JRE.
- Restart your computer and perform the command line test above to verify installation.

Double click on ServerOFX.jar file to start server module and ClientOFX.jar to start client module.
The Server module logs the activities of all clients, reporting instances of INFO and ALERTs.
The Client module lets you register, login, add items, edit them, purchase items, edit your profile among other things.

Documentation
-------------
The documentation available as of the date of this release is included in HTML format in the directory /dist/javadoc/ of your copy of OFX source code. You can download the source by cloning the project repository from the following link: <git@github.com:avaneeshrastogi/OFX.git>.

Changelog
---------
- Revamped user interface
- Added basic and advanced search feature
- Fixed bug in refresh system of notification system
- Added feature to view buyer's details

Known Issues
------------
- User needs to refresh the list of recent items and notification list manually. This should be ideally automated to refresh periodically.

Licensing
---------
OFX is free and open source software, distributed under the GNU General Public License 3. Please see the file called LICENSE for a copy of the license.

Contacts
--------
- If you have a concrete bug report for OFX, or
- If you want to participate in actively developing OFX, please mail the developer at <rastogi.avaneesh (at) gmail (dot) com>       
The developers would appreciate contribution in any form. Suggestions for improvements are welcome.

Contributor list
----------------
Avaneesh Rastogi
Nishant Gupta
Priyanshu Srivastava

Credits & Acknowledgements
--------------------------
The developers acknowledge the efforts behind the libraries/APIs which makes OFX possible. 
OFX is built using Netbeans IDE (Integrated Development Environment) developed by Oracle Corporation. Please visit the Netbeans homepage at (link): <https://netbeans.org/>. 
OFX uses icons from tango-icon-theme-0.8.90 which is part of the Tango Freedesktop Project.
The developers are grateful to SourceForge <https://sourceforge.net/> and Github <https://github.com/> for hosting the project.

***********************************************************************
  Copyright (C) 2014 Avaneesh Rastogi <rastogi.avaneesh (at) gmail (dot) com>
 
  OFX is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
 
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
 
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 
************************************************************************
