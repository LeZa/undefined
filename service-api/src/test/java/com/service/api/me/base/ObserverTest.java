package com.service.api.me.base;

import org.junit.Test;

import com.build.thinkingc.out.self.base.pattern.observer.observer.CurrentConditionsDisplay;
import com.build.thinkingc.out.self.base.pattern.observer.subject.WeatherData;

//观察者模式  定义了对象之间一对多的依赖，这样一来，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新
// 实现观察者，注册成为观察者(被观察者)
public class ObserverTest {

	@Test
	public void weathreData(){
		WeatherData  wd =  new WeatherData();
		CurrentConditionsDisplay ccd =  new CurrentConditionsDisplay(wd);
		wd.setMeasurements(80, 65, 30.4F);
	}
}
