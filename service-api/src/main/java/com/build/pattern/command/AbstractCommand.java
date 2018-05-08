package com.build.pattern.command;

public abstract class AbstractCommand
    implements Command{


    @Override
    public void execute() {
        doExecute();
    }

    @Override
    public void undo(){

    }


    protected abstract  void doExecute();
}
