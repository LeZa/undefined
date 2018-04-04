package com.soundgroup.battery.logic;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import com.cube.logic.proc.*;
import com.soundgroup.battery.logic.proc.BatteryInfoProcess;
import com.soundgroup.battery.logic.proc.CloseBatteryProcess;
import com.soundgroup.battery.logic.proc.OpenBatteryProcess;
import com.soundgroup.battery.logic.proc.ReceiveBatteryProcess;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.event.EventEnum;

/**
 * @ClassName: ProcessManager
 * @author A18ccms a18ccms_gmail_com 
 * @date Jul 9, 2015 12:44:57 PM
 */
@Component
public class ProcessManager {

	private static final Logger LOG = Logger.getLogger(CubeRun.class);
	private static ProcessManager INSTANCE;

    public Map<EventEnum, Process> processMap = new HashMap<EventEnum, Process>();

    public static ProcessManager getInstance() {
        return INSTANCE;
    }

    public Process getProcess(EventEnum eventEnum) {
        return processMap.get(eventEnum);
    }


    @PostConstruct
    private void init() {
        LOG.info("init the INSTANCE OF ProcessManager");
        INSTANCE = new ProcessManager();

        /**
         * open battery process
         */
        INSTANCE.processMap.put(EventEnum.OPEN_BATTERY, new OpenBatteryProcess());

        /**
         * close battery process
         */
        INSTANCE.processMap.put(EventEnum.CLOSE_BATTERY,new CloseBatteryProcess());

        /**
         * http get battery info process
         */
        INSTANCE.processMap.put(EventEnum.HTTP_BATTERY_INFO,new BatteryInfoProcess());

        /**
         * save battery info process
         */
        INSTANCE.processMap.put(EventEnum.SAVE_BATTERY_INFO,new ReceiveBatteryProcess());
    }
}
