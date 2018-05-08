package com.build.pattern.observer.subject;

import com.build.pattern.observer.observer.Observer;

public interface Subject {

	public void registerObserver( Observer o);
	
	public void removeObserver( Observer o);
	
	public void notifyObservers();
}
