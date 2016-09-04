
Set objFSO = CreateObject("Scripting.FileSystemObject")
Set objFile = objFSO.GetFile("installer.jar")
Set objShell = CreateObject("Shell.Application")

params = "-jar " & objFSO.GetAbsolutePathName(objFile) & " " & objFSO.GetAbsolutePathName(objFile)

objShell.ShellExecute "javaw", params, "", "runas"
