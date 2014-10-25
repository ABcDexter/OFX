
README - GRAFCOM


What is it?
-----------
Grafcom is a graphical interface for popular commands on Windows and Linux-based systems.
It allows you to execute commands and select associated options without typing them.
The results of command execution are visible in an attached terminal windows.
Grafcom helps users learn commands and options by providing relevant descriptions.

Detailed Description
--------------------
Grafcom is a graphical interface for common networking and file utilities for Linux and Windows environment
Grafcom provides an environment for mastering the command line by providing descriptions of commands and 
associated options of commonly used operating system utilities.
The program can be used by all users, but is specifically aimed at inexperienced users.
It allows the user to graphically specify the tool and options to be used, and outputs the result.
It also has the ability to enter commands manually (using a keyboard).

Features
--------
* Ability to execute commands without using the keyboard
* Run commands with superuser privileges
* Relevant descriptions of selected command and its options
* Save output to external file for preview later
* Maintains history of commands executed
* Ability to interact directly by typing the commands
* Sound effects to notify exit status of execution
* Cross-platform software: runs on all platforms supporting Java

System Requirements
-------------------
(Operating system) Microsoft Windows 7 (or above), Java Runtime Environment (JRE) (version 6+)

Downloading
-----------
The latest version on Grafcom can be obtained by downloading from the official project page at SourceForge (link): <https://sourceforge.net/projects/grafcom/>. You can download the source by cloning the project repository from the following link: <git@github.com:avaneeshrastogi/Grafcom.git>.

Installation
------------
Check if you have Java Runtime Environment (JRE) installed on your system:
- Open the command prompt. Follow the menu path Start > Programs > Accessories > Command Prompt
- Type: java -version and press Enter on your keyboard.
- Result: A message similar to the following indicates that Java is installed and you are ready to use Grafcom via the Java Runtime Environment
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

Double click on Grafcom.jar file to start application.

Documentation
-------------
The documentation available as of the date of this release is included in HTML format in the directory /dist/javadoc/ of your copy of Grafcom source code. You can download the source by cloning the project repository from the following link: <git@github.com:avaneeshrastogi/Grafcom.git>.

Changelog
---------
- Revamped user interface
- Added command execution timeout
- Fixed indefinite command execution
- Improved GUI responsiveness
- Added Windows internal commands, unsupported options removed
- Added GUI elements viz. Command Progress Bar, Status Bar, Menu Bar etc.
- Added way to abort command execution using button
- Added Terminal background/foreground color change
- Added support for changing working directory

Known Issues
------------
- Commands which prompt user for more information subsequently (for example passwords) result in termination with return value 1.

Licensing
---------
Grafcom is free and open source software, distributed under the GNU General Public License 3. Please see the file called LICENSE for a copy of the license.

Contacts
--------
- If you have a concrete bug report for Grafcom, or
- If you want to participate in actively developing Grafcom, please mail the developer at <rastogi.avaneesh (at) gmail (dot) com>       
The developers would appreciate contribution in any form. Suggestions for improvements are welcome.

Contributor list
----------------
Avaneesh Rastogi
Nishant Gupta
Priyanshu Srivastava

Credits & Acknowledgements
--------------------------
The developers acknowledge the efforts behind the Apache Commons Exec library which makes Grafcom possible. Commons-exec is used as the backbone of this project - to reliably execute external processes. Please visit the library's homepage at (link): <http://commons.apache.org/proper/commons-exec/>.
Grafcom is built using Netbeans IDE (Integrated Development Environment) developed by Oracle Corporation. Please visit the Netbeans homepage at (link): <https://netbeans.org/>. 
Grafcom uses icons from tango-icon-theme-0.8.90 which is part of the Tango Freedesktop Project.
The developers are grateful to SourceForge <https://sourceforge.net/> and Github <https://github.com/> for hosting the project.

***********************************************************************
  Copyright (C) 2014 Avaneesh Rastogi <rastogi.avaneesh (at) gmail (dot) com>
 
  Grafcom is free software: you can redistribute it and/or modify
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