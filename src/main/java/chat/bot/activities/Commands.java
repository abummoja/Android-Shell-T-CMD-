package chat.bot.activities;

import android.content.*;

public class Commands
{
	public static String[] supported_commands = {"time", "date", "uname", "devicename", "systeminfo", "deviceboard",
		"echo \"value\"", "useof \"item\"", "clearcache", "info",
		"open \"filepath\"", "delete \"filepath\"", "rename \"filepath\""};
	String defaultcmd = "%s is unknown";
	String hlp;
	Context c;
	
	public Commands(){
		
	}
	public Commands(Context ctx){
		this.c = ctx;
	}
	public String reply(String cmd)
	{
		if (cmd.toLowerCase() == "help"){
			hlp = supported_commands.toString();
			}
			return hlp;
	}
}
