Dim Shell, DesktopPath, URL
Set Shell = CreateObject("WScript.Shell")
DesktopPath = Shell.SpecialFolders("Desktop")
Set URL = Shell.CreateShortcut(DesktopPath & "\latexdraw.LNK")
URL.TargetPath = WScript.Arguments(0)
URL.Save
