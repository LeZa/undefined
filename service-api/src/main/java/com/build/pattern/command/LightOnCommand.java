package com.build.pattern.command;

import com.build.pattern.command.pb.Light;

public class LightOnCommand
	implements Command{
	
	Light light;
	
	public LightOnCommand( Light light ){
		this.light = light;
	}
	
	public void execute(){
		light.on();
	}
	
	public void undo(){
		
		light.off();
	}

}
