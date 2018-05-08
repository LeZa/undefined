package com.build.pattern.command;

public interface Command {

	public void execute();
	
	public void undo();
}
