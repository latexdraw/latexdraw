
Java 8 is required to run LaTeXDraw: https://www.java.com/
Look at the online manual to see the installation requirements (in particular regarding LaTeX):
https://github.com/arnobl/latexdraw/wiki/Manual


This installer will install LaTeXDraw in the selected directory and create a directory for shared templates. 
This last action can require administrator privileges.

*** Mac ***

A Mac app bundle is now available so that you should not use this installer.
See: https://sourceforge.net/projects/latexdraw/files/latexdraw/


*** Windows ***

On Windows you must use the install_windows.vbs to install LaTeXDraw.
This script activates the "run as administrator" feature to install 
files in dedicated directories such as "Program Files". It may ask you your password.


*** Debian/Ubuntu/Fedora/Opensuse ***

The scripts 'install_debian_ubuntu', 'install_fedora', or 'install_opensuse' will ask
administrator privileges before launching the installer.

Warning: on Fedora 25 with Wayland, the installation will fail since Wayland forbids graphical applications
to run in sudo/su mode. See: https://bugzilla.redhat.com/show_bug.cgi?id=1274451
The workaround consists of installing LaTeXDraw using a X11 sessions, or manually (see below). We will fix that in a next release.


*** Running the installer ***

If you want to manually launch the installer (i.e. without through a script as explained above)
in a console, the command is "java -jar installer.jar" (but you must have admin rights).

The shared templates are located in the following folder:
 - for Unix, /usr/share/latexdraw
 - for Mac OS X, /Users/Shared/latexdraw
 - for Vista, ProgramData\latexdraw
 - for other Windows, All Users\Application Data\latexdraw 
 
For Linux, a script will be created in /usr/bin to launch LaTeXDraw (if the installer is launched as root).
A shortcut is created for Windows.

The first execution of LaTeXDraw will create the profile of the current user in the following location:
 - for Unix, ~/.latexdraw
 - for Max OS X, <user>/Library/Preferences/latexdraw
 - for Vista, <user>\AppData\Local\latexdraw
 - for other Windows, <user>\Application Data\latexdraw

This profile contains the preferences of the user and its templates.


*** HOW TO INSTALL LATEXDRAW MANUALLY ***

If you want to install LaTeXDraw without using the installer you must place "LaTeXDraw.jar",
"release_notes.txt", "licence.txt", "help/" and "lib/" in the same directory.
You can then double-click on LaTeXDraw.jar to run the application.
