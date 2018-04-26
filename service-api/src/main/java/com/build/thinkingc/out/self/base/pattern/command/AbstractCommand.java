package com.build.thinkingc.out.self.base.pattern.command;

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
