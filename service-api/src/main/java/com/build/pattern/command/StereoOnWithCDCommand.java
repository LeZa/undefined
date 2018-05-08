package com.build.pattern.command;

import com.build.pattern.command.pb.Stereo;

public class StereoOnWithCDCommand 
	implements Command{
	
	Stereo stereo;
	
	public StereoOnWithCDCommand( Stereo stereo ){
		this.stereo = stereo;
	}
	
	public void execute(){
		
	}
	
	public void undo(){
		
	}
}
