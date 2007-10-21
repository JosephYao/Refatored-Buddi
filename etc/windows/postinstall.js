//************************
// File: postinstall.js (Postinstall script for Buddi Installer)
// Author: (c) Wyatt Olson <wyatt.olson@gmail.com>
//  You are not allowed to use this file, or the rest of Moss Installer, without
//  written permisson from Wyatt Olson.
//
// Creates a Start Menu icon and associates data files in the registry.
//
// -The first argument passed to this script (objArgs(0)) is the installation 
//  location (folder to which the package.zip will be extracted).
//************************

//Initialize the needed objects
var objShell = new ActiveXObject("Wscript.Shell");
var objArgs = WScript.Arguments;

//Set up a shortcut in the Start Menu
var strPrograms = objShell.SpecialFolders("Programs");
var objShellLink = objShell.CreateShortcut(strPrograms + "\\Buddi.lnk");
objShellLink.TargetPath = objArgs(0) + "\\Buddi.exe";
objShellLink.Arguments = "--windows-installer";
objShellLink.Description = "Personal Budgeting Program";
objShellLink.WorkingDirectory = objArgs(0);
objShellLink.Save();

//Associate the files with Buddi
//Buddi Data is first...
objShell.regwrite("HKCR\\.buddi3\\", "BuddiData");
objShell.regwrite("HKCR\\BuddiData\\", "Buddi Data File");
objShell.regwrite("HKCR\\BuddiData\\DefaultIcon\\", objArgs(0) + "\\BuddiData.ico");
objShell.regwrite("HKCR\\BuddiData\\shell\\open\\command\\", "\"" + objArgs(0) + "\\Buddi.exe\" --windows-installer \"%1\"");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3\\Application", objArgs(0) + "\\Buddi.exe");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3\\OpenWithList\\a", objArgs(0) + "\\Buddi.exe");

//Buddi Plugins are next...
objShell.regwrite("HKCR\\.buddi3plugin\\", "BuddiPlugin");
objShell.regwrite("HKCR\\BuddiPlugin\\", "Buddi Plugin");
objShell.regwrite("HKCR\\BuddiPlugin\\DefaultIcon\\", objArgs(0) + "\\BuddiPlugin.ico");
objShell.regwrite("HKCR\\BuddiData\\shell\\open\\command\\", "\"" + objArgs(0) + "\\Buddi.exe\" --windows-installer \"%1\"");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3plugin\\Application", objArgs(0) + "\\Buddi.exe");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3plugin\\OpenWithList\\a", objArgs(0) + "\\Buddi.exe");

//Buddi Languages now...
objShell.regwrite("HKCR\\.lang\\", "BuddiLang");
objShell.regwrite("HKCR\\BuddiLang\\", "Buddi Translation");
objShell.regwrite("HKCR\\BuddiLang\\DefaultIcon\\", objArgs(0) + "\\BuddiLanguage.ico");
objShell.regwrite("HKCR\\BuddiData\\shell\\open\\command\\", "\"" + objArgs(0) + "\\Buddi.exe\" --windows-installer \"%1\"");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.lang\\Application", objArgs(0) + "\\Buddi.exe");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.lang\\OpenWithList\\a", objArgs(0) + "\\Buddi.exe");