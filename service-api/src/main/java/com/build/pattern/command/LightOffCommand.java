package com.build.pattern.command;

import com.build.pattern.command.pb.Light;

public class LightOffCommand
	implements Command{

	Light light;
	
	public LightOffCommand( Light light ){
			this.light = light;
	}
	
	public void execute(){
		light.off();
	}
	
	public void undo(){
		light.on();
	}
	
}
