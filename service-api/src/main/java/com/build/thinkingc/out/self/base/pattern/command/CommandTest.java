package com.build.thinkingc.out.self.base.pattern.command;

import com.build.thinkingc.out.self.base.pattern.command.pb.Light;

public class CommandTest {

	//命令模式   将请求封装成对象，以便使用不同的请求、队列或者日志来参数化其他对象。 命令模式也支持可撤销的操作
	//   调用者，命令，命令对象
	public static void main( String sck[] ){
		RemoteControl rc =  new RemoteControl();
		Light light = new Light();
		Command lightOnCommand =  new LightOnCommand( light );
		Command lightOffCommand =  new LightOffCommand( light );
		rc.setCommand(0, lightOnCommand, lightOffCommand);
		
	}
}
