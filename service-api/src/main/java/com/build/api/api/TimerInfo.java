package com.build.api.api;

import java.util.Calendar;
import java.util.TimerTask;

public class TimerInfo extends TimerTask {

	@Override
	public void run() {
		System.out.println(Calendar.getInstance().getTimeInMillis()+"........................"+"Timer task.");
	}

}
