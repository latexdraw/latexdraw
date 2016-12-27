
Java 8 is required to run LaTeXDraw: https://www.java.com/

This installer will install LaTeXDraw in the selected directory and create a directory for shared templates. 
This last action can require administrator privileges.

*** Windows ***

On Windows you must use the install_windows.vbs to install LaTeXDraw.
This script activates the "run as administrator" feature to install 
files in dedicated directories such as "Program Files". It may ask you your password.


*** Debian/Ubuntu ***

The script install_debian_ubuntu will ask administrator privileges before launching the installer.


*** Running the installer ***

If you want to launch the installer in a console, the command is "java -jar installer.jar".

The shared templates are in the following folder is:
 - for Unix, /usr/share/latexdraw
 - for Mac OS X, /Users/Shared/latexdraw
 - for Vista, ProgramData\latexdraw
 - for other Windows, All Users\Application Data\latexdraw 
 
For Linux, a script will be created in /usr/bin to launch LaTeXDraw (if the installer is launched as root).
No shortcut is created for Windows.

The first execution of LaTeXDraw will create the profile of the current user in:
 - for Unix, ~/.latexdraw
 - for Max OS X, <user>/Library/Preferences/latexdraw
 - for Vista, <user>\AppData\Local\latexdraw
 - for other Windows, <user>\Application Data\latexdraw

This profile contains the preferences of the user and its templates.


*** HOW TO INSTALL LATEXDRAW MANUALLY ***

If you want to install LaTeXDraw without using the installer you must place "LaTeXDraw.jar", "release_notes.txt", "licence.txt", "help/" and "lib/" in the same directory.
