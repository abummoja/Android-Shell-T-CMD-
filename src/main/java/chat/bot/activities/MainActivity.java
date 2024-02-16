package chat.bot.activities;

import android.app.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import chat.bot.*;
import java.io.*;
import java.util.*;
import android.text.method.*;

public class MainActivity extends Activity
{

	TextView msgs;
	EditText uin;
	Button send;
	Calendar cale = Calendar.getInstance();
	String sysprops;
	String tinfo = "~t/tdroid>build(c)2021\ndroid-terminal:lang java;[]t_droid";
	String tuser;
	String[] _commands = {"time", "date", "uname", "devicename", "systeminfo", "deviceboard",
		"echo \"value\"", "useof \"item\"", "clearcache", "info",
		"open \"filepath\"", "delete \"filepath\"", "rename \"filepath\""};
		String help = ("\nThe usage of`function()` is as follows\n\tfunction(parameters) or function(){//code}\n\tThe usage of echo is echo[object]\n\tThe usage of loops is %type% types are int, string, char, obj\n\tother functions type fnc to see");

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sysprops = ">" + Build.TIME + " t~/" + Build.BRAND + ">" + Build.TYPE + ">" + Build.DEVICE + "/";
		msgs = findViewById(R.id.textPanel);
		msgs.append(getApplicationContext().getCacheDir().toString());
		msgs.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					msgs.bringPointIntoView(msgs.length());
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					msgs.bringPointIntoView(msgs.length());
				}
			});
		uin = findViewById(R.id.userInput);
		send = findViewById(R.id.sendButton);
		msgs.append(sysprops);
		send.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					msgs.append("\n[user]: " + uin.getText().toString());
					String in = uin.getText().toString();
					try
					{
						compile(uin, msgs);
						uin.getText().clear();
						msgs.setMovementMethod(new ScrollingMovementMethod());
					}
					catch (Exception e)
					{
						msgs.append(tmessage(e.getLocalizedMessage()));
						msgs.setMovementMethod(new ScrollingMovementMethod());
					}

				}
			});
	}
	public void compile(EditText s, TextView t)
	{
		String str = s.getText().toString().toLowerCase();
		if (str.matches("help"))
		{
			t.append(help);
		}
		else if (str.matches("uname"))
		{
			t.append(tmessage(Build.USER));
		}
		else if (str.matches("systeminfo"))
		{
			t.append(tmessage(System.getProperties().toString()));
		}
		else if (str.matches("time"))
		{
			int h = cale.getTime().getHours();
			int m = cale.getTime().getMinutes();
			t.append(tmessage(h + ":" + m));
		}
		else if ((str.matches("exit")) || (str.matches("q")))
		{
			finish();
		}
		else if (str.matches("clear")||(str.matches("cls")))
		{
			t.setText(sysprops);
		}
		else if (str.matches("date")||(str.matches("_dt")))
		{
			Calendar cal = Calendar.getInstance();
			t.append(tmessage(cal.getTime().toString()));
		}
		else if (str.length() == 0)
		{
			t.append(tmessage("null entry"));
			t.append(tmessage(s.getHint().toString()));
		}
		else if (str.matches("info"))
		{
			t.append(tmessage(tinfo));
		}
		else if (str.matches("listfiles"))
		{
			File fh = new File(Environment.getExternalStorageDirectory().toString());
			String[] fhs = fh.list();
			for(String fs:fhs){
				t.append("\n"+fs);
			}
			String newString = str;
			int index = newString.indexOf(" ");
			String sfhs = newString.substring(1, index);
			if(str.matches(String.format("open %s", sfhs))){
				t.append(String.format("opening %s", sfhs));
			}else{
				compile(s, t);
			}
		}
		else if (str.matches("tree"))
		{
			File disk = new File(Environment.getExternalStorageDirectory().toString());
			File[] all = disk.listFiles();
			t.append(tmessage("executing..."));
			RecursivePrint(all, 0, 0);
		}
		else if (str.matches("contacts"))
		{

		}
		else if(str.matches("systemfonts")||(str.matches("sysfonts"))||(str.matches("fonts"))){
			File fonts = new File("/system/fonts");
			String[] fl = fonts.list();
			for(String fn:fl){
				t.append("\n"+fn);
			}
		}
		else if (str.matches("%int%"))
		{
			int i = 0;
			for (int x = 0;x < 1000;x++)
			{
				i += 1;
				t.append("\n" + i);
			}
		}
		else
		{
			t.append(tmessage(String.format("couldn't find %s", str)));
		}
	}
	public String tmessage(String msg)
	{
		return "\n[t_cmd]: " + msg;
	}
	void RecursivePrint(File[] arr, int index, int level)  
	{ 
		// terminate condition 
		if (index == arr.length) 
			return; 

		// tabs for internal levels 
		for (int i = 0; i < level; i++) 
			msgs.append("\t");

		// for files 
		if (arr[index].isFile()) 
			msgs.append(arr[index].getName()); 

		// for sub-directories 
		else if (arr[index].isDirectory()) 
		{ 
			msgs.append("[" + arr[index].getName() + "]"); 

			// recursion for sub-directories 
			RecursivePrint(arr[index].listFiles(), 0, level + 1); 
		} 

		// recursion for main directory 
		RecursivePrint(arr, ++index, level); 
    }

	@Override
	protected void onRestart()
	{
		super.onRestart();
	} 
	
}
