
[![Build Status](https://ci.inria.fr/malai/job/latexdraw/badge/icon)](https://ci.inria.fr/malai/job/latexdraw/)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=net.sf.latexdraw%3Alatexdraw&metric=coverage)](https://sonarcloud.io/dashboard?id=net.sf.latexdraw%3Alatexdraw)
[![ncloc](https://sonarcloud.io/api/project_badges/measure?project=net.sf.latexdraw%3Alatexdraw&metric=ncloc)](https://sonarcloud.io/dashboard?id=net.sf.latexdraw%3Alatexdraw)
[![java](https://img.shields.io/badge/java-11-blue.svg)](https://www.oracle.com/technetwork/java/javase/overview/index.html)
[![java](https://img.shields.io/badge/license-GPL3-green.svg)](https://github.com/arnobl/latexdraw/blob/master/latexdraw-core/net.sf.latexdraw/license.txt)<br/>

latexdraw
=========

A vector drawing editor for LaTeX.

Documentation:
https://github.com/arnobl/latexdraw/wiki/Manual

Binaries to download on Sourceforge:
http://sourceforge.net/projects/latexdraw/

**Development version**

You can test the next 4.0 development verion using the following binaries.
- for Linux (Fedora): https://ci.inria.fr/malai/job/latexdraw4/lastSuccessfulBuild/artifact/target/
- for Linux (Debian/Ubuntu): https://ci.inria.fr/malai/job/latexdraw4deb/lastSuccessfulBuild/artifact/target/
- for Windows: https://ci.inria.fr/malai/job/latexdraw4win/lastSuccessfulBuild/artifact/target/
- MacOsX: https://ci.inria.fr/malai/job/latexdraw4mac/lastSuccessfulBuild/artifact/target/

*These binaries do not require any Java installation anymore.*
They embed a sliced JVM directly.
Run the app by launching the latexdraw-run file located in the bin folder.


**Support**

You can support this software by making a donation:
http://sourceforge.net/donate/index.php?group_id=156523


**Linux packaging** 

[![Packaging status](https://repology.org/badge/vertical-allrepos/latexdraw.svg)](https://repology.org/metapackage/latexdraw)


**Documentation**

Look at the online manual to see the installation requirements (in particular regarding LaTeX):
https://github.com/arnobl/latexdraw/wiki/Manual


This installer will install LaTeXDraw in the selected directory and create a directory for shared templates.
This last action can require administrator privileges.

**Mac**

A Mac app bundle is now available so that you should not use this installer.
See: https://sourceforge.net/projects/latexdraw/files/latexdraw/


**Windows**

On Windows you must use the install_windows.vbs to install LaTeXDraw.
This script activates the "run as administrator" feature to install
files in dedicated directories such as "Program Files". It may ask you your password.


**Debian/Ubuntu/Fedora/Opensuse**

The scripts `install_debian_ubuntu`, `install_fedora` or `install_opensuse` will ask
administrator privileges before launching the installer.

Warning: on Fedora 25 with Wayland, the installation will fail since Wayland forbids graphical applications
to run in sudo/su mode. See: https://bugzilla.redhat.com/show_bug.cgi?id=1274451
The workaround consists of installing LaTeXDraw using a X11 sessions, or manually (see below). We will fix that in a next release.


**Running the installer**

If you want to manually launch the installer (i.e. without through a script as explained above)
in a console, the command is `java -jar installer.jar` (but you must have admin rights).

The shared templates are located in the following folder:
 - for Unix, `/usr/share/latexdraw`
 - for Mac OS X, `/Users/Shared/latexdraw`
 - for Vista, `ProgramData\latexdraw`
 - for other Windows, `All Users\Application Data\latexdraw`

For Linux, the installer will create a script in `/usr/bin` to launch LaTeXDraw (if the installer is launched as root).
On Windows the installer creates a shortcut.

The first execution of LaTeXDraw will create the profile of the current user in the following location:
 - for Unix, `~/.latexdraw`
 - for Max OS X, `<user>/Library/Preferences/latexdraw`
 - for Vista, `<user>\AppData\Local\latexdraw`
 - for other Windows, `<user>\Application Data\latexdraw`

This profile contains the preferences of the user and its templates.


**HOW TO INSTALL LATEXDRAW MANUALLY**

If you want to install LaTeXDraw without using the installer you must place `LaTeXDraw.jar`,
`release_notes.txt`, `licence.txt`, `help/` and `lib/` in the same directory.
You can then double-click on `LaTeXDraw.jar` to run the application.


**Build LaTeXDraw**

To compile you need JDK14 and Maven:
https://adoptopenjdk.net

The default Maven profile is for Linux and does not work for Windows and MacOSX
If you want to compile the project on MacOSX use the `mac` profile.
On Windows, use the `win` profile.
To do so, you have to add the option `-P mac` or `-P win` in the following Maven commands.

If you just want to compile the app:
`mvn clean compile`

If you want to build the app (and produce platform-specific installer files):
`mvn clean package`

If you want to skip the tests
`mvn -DskipTests=true clean package`

The packaging step is specific for each OS (Fedora, Debian, Windows, MacOSX, etc.):
it uses tools these OS provide to build installation files.
These tools are:
- On Fedora, you need `rpmbuild`:
 `dnf install rpm-build`
- On Debian/Ubuntu, you need `fakeroot`: `apt install fakeroot`
- On MacOSX, it seems to work without any supplementary installation
 
