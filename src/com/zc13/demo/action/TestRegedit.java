package com.zc13.demo.action;
import java.util.prefs.Preferences;
	public class TestRegedit {

			public static final String REALKEY= "zbxhere";
			public static void main(String[] args) {
				 
		Preferences p = Preferences.userRoot();
		 // p.put(REALKEY, "bar");

		  // read back from HKEY_CURRENT_USER
		  System.out.println(p);
		  System.out.println(p.get(REALKEY, "HKCU houston we have a problem"));

	}
}
