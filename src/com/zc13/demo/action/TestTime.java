package com.zc13.demo.action;

import java.util.Timer;

public class TestTime {

    public static void main(String[] args) {
           Timer timer = new Timer();
           timer.schedule( new MyTimerTask(), 1000 );

       }

}
