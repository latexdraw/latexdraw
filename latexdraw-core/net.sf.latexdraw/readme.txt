
This installer will install LaTeXDraw in the selected directory and create a directory for shared templates. 
This last action can require administrator privileges.


*** Current issues ***

Hardware acceleration.
  In the case of a glitches, go in the preferences, in the quality tab, and check/uncheck the OpenGL check box.
  This option may lead to glitches with some graphical cards.

  In the case of a blanck screen (i.e. you cannot access the preferences through the user interface),
  you can modify the preferences file that is located: ~/.latexdraw/.preferences.xml on Linux;
  AppData\Local\latexdraw.preferences.xml on Windows (AppData may be a hidden directory);
  /Library/Preferences/latexdraw/.preferences.xml on MacOSX. Then, modify or create the .preferences.xml file as explained here:
  https://bugs.launchpad.net/latexdraw/+bug/1581483/comments/3


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
