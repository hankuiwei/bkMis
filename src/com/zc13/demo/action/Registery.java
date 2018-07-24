package com.zc13.demo.action;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
public class Registery {
   
    public static void main(String[] args) {
    	
    	Preferences pre= Preferences.systemRoot().node("/zbx");

    	try {
			System.out.println(pre.nodeExists("/zbxxx"));
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.print(pre.get("DisplayVersion", "jajj"));

    }
}
