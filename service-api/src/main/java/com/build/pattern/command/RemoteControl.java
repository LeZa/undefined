package com.build.pattern.command;

public class RemoteControl {

	Command[] onCommands;
	Command[] offCommands;
	Command undoCommand;
	
	public RemoteControl(){
		onCommands =  new Command[7];
		offCommands = new Command[7];
		
		Command noCommand =  new NoCommand();
		for( int i = 0; i < 7;i++){
			onCommands[i] = noCommand;
			offCommands[i] = noCommand;
		}
		undoCommand =  noCommand;
	}
	
	public void setCommand( int slot,Command onCommand,Command offCommand){
		onCommands[ slot ] = onCommand;
		offCommands[ slot ] = offCommand;
	}
	
	public void onCommandButton( int slot ){
		onCommands[ slot ].execute();
		undoCommand = onCommands[slot];
	}
	
	public void offCommandButton( int slot ){
		offCommands[ slot ].execute();
		undoCommand =  offCommands[slot];
	}
	
	public void undoCommandButton(){
		undoCommand.undo();
	}
	public String toString(){
		return "";
	}
	
}
