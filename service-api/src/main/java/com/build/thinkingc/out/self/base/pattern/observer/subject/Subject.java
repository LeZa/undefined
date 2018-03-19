package com.build.thinkingc.out.self.base.pattern.observer.subject;

import com.build.thinkingc.out.self.base.pattern.observer.observer.Observer;

public interface Subject {

	public void registerObserver( Observer o);
	
	public void removeObserver( Observer o);
	
	public void notifyObservers();
}
