package com.build.thinkingc.out.self.base.pattern.command;

import com.build.thinkingc.out.self.base.pattern.command.pb.Light;

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
