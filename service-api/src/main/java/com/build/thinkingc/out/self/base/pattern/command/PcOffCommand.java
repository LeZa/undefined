package com.build.thinkingc.out.self.base.pattern.command;

import com.build.thinkingc.out.self.base.pattern.command.AbstractCommand;

public class PcOffCommand
    extends AbstractCommand{

    @Override
    protected void doExecute() {
        System.out.println( "This is pc off command ");
    }
}
