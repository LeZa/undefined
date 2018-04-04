package com.soundgroup.battery.logic;

import com.soundgroup.battery.event.CubeMsg;
import com.soundgroup.battery.exception.IllegalDataException;

import java.sql.SQLException;

public interface Process {
    public void excute(CubeMsg msg) throws IllegalDataException, SQLException;
}
