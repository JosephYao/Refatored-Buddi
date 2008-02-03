//************************
// File: Uninstall from Registry.js (Registry Uninstall script for Buddi Installer)
// Author: (c) Wyatt Olson <wyatt.olson@gmail.com>
//  You are not allowed to copy or modify this file, or the rest of Moss Installer, without
//  written permisson from Wyatt Olson.
//
// This script will delete registry entries which were created at Buddi install time.  Note
// that deleting Regitry entries is an inherently dangerous operation, and can result in a
// completely corrupted Windows installation.  (This is why non-centralized sources of 
// registry information are a much better design choice.  Things like Apple .app packages
// are far safer from a system point of view, as all registration information is included
// in the package, rather than in a central location).
//
// USE THIS SCRIPT AT YOUR OWN RISK!!! I WILL NOT BE HELD LIABLE FOR ANY DAMAGE WHICH THIS
// SCRIPT MAY CAUSE!!!
//************************

//Initialize the needed objects
var objShell = new ActiveXObject("Wscript.Shell");

//Display a confirmation message.
var reply = objShell.popup("This script will attempt to uninstall all Buddi entries from the\nregistry.  You cannot undo this; to re-enable the associations you will\nneed to install again.\n\nPlease note that deleting registry entries is a dangerous operation.\nYou agree to run this script at your own risk.  I will not\nbe held liable for any damages resulting from the exeution of this script.\n\nDo you want to remove all Buddi entries from the registry?", -1 , "Remove Buddi Registry Entries", 4 + 16);
if (reply == 7){	
	objShell.popup("Exiting");
	WScript.Quit(0);
}

//Remove Buddi associations.  Since WSH is so lame, it returns an error if you try to delete something
// which has already been deleted.  For this reason, we must first write blank values to these keys.
// Yes, it's annoying.
//Buddi Data is first...
objShell.regwrite("HKCR\\.buddi3\\", "");
objShell.regwrite("HKCR\\BuddiData\\", "");
objShell.regwrite("HKCR\\BuddiData\\DefaultIcon\\", "");
objShell.regwrite("HKCR\\BuddiData\\shell\\open\\command\\", "");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3\\Application", "");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3\\OpenWithList\\a", "");

//Buddi Plugins are next...
objShell.regwrite("HKCR\\.buddi3plugin\\", "");
objShell.regwrite("HKCR\\BuddiPlugin\\", "");
objShell.regwrite("HKCR\\BuddiPlugin\\DefaultIcon\\", "");
objShell.regwrite("HKCR\\BuddiData\\shell\\open\\command\\", "");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3plugin\\Application", "");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3plugin\\OpenWithList\\a", "");

//Buddi Languages now...
objShell.regwrite("HKCR\\.lang\\", "");
objShell.regwrite("HKCR\\BuddiLang\\", "");
objShell.regwrite("HKCR\\BuddiLang\\DefaultIcon\\", "");
objShell.regwrite("HKCR\\BuddiData\\shell\\open\\command\\", "");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.lang\\Application", "");
objShell.regwrite("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.lang\\OpenWithList\\a", "");

//We must delete keys in the reverse order that we created them, so that you delete child hives first.
//Buddi Data is first...
objShell.regdelete("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3\\OpenWithList\\a");
objShell.regdelete("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3\\Application");
objShell.regdelete("HKCR\\BuddiData\\shell\\open\\command\\");
objShell.regdelete("HKCR\\BuddiData\\shell\\open\\");
objShell.regdelete("HKCR\\BuddiData\\shell\\");
objShell.regdelete("HKCR\\BuddiData\\DefaultIcon\\");
objShell.regdelete("HKCR\\BuddiData\\");
objShell.regdelete("HKCR\\.buddi3\\");

//Buddi Plugins are next...
objShell.regdelete("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3plugin\\OpenWithList\\a");
objShell.regdelete("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.buddi3plugin\\Application");
objShell.regdelete("HKCR\\BuddiPlugin\\shell\\open\\command\\");
objShell.regdelete("HKCR\\BuddiPlugin\\shell\\open\\");
objShell.regdelete("HKCR\\BuddiPlugin\\shell\\");
objShell.regdelete("HKCR\\BuddiPlugin\\DefaultIcon\\");
objShell.regdelete("HKCR\\BuddiPlugin\\");
objShell.regdelete("HKCR\\.buddi3plugin\\");

//Buddi Languages now...
objShell.regdelete("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.lang\\OpenWithList\\a");
objShell.regdelete("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\.lang\\Application");
objShell.regdelete("HKCR\\BuddiLang\\DefaultIcon\\");
objShell.regdelete("HKCR\\BuddiLang\\shell\\open\\command\\");
objShell.regdelete("HKCR\\BuddiLang\\shell\\open\\");
objShell.regdelete("HKCR\\BuddiLang\\shell\\");
objShell.regdelete("HKCR\\BuddiLang\\");
objShell.regdelete("HKCR\\.lang\\");

objShell.popup("Buddi associations have been removed from the registry"); 	
