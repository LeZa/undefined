package com.build.thinkingc.out.self.base.pattern.observer.observer;

import com.build.thinkingc.out.self.base.pattern.observer.DisplayElement.DisplayElement;
import com.build.thinkingc.out.self.base.pattern.observer.subject.Subject;

public class CurrentConditionsDisplay 
	implements Observer,DisplayElement{
	
	@SuppressWarnings("unused")
	private Subject weatherData;
	private float temperature;
	private float humidity;
	
	
	public CurrentConditionsDisplay( Subject weatherData){
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}
	
	@Override
	public void display() {
		System.out.println(" Current conditions: "+ temperature + "F degrees and "+ humidity + " %humidity");
	}

	@Override
	public void update(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		display();
	}

}
