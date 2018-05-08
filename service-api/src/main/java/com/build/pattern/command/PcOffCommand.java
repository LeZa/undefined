package com.build.pattern.command;

import com.build.pattern.command.AbstractCommand;

public class PcOffCommand
    extends AbstractCommand{

    @Override
    protected void doExecute() {
        System.out.println( "This is pc off command ");
    }
}
