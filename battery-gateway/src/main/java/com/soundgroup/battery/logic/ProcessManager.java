package com.soundgroup.battery.logic;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import com.soundgroup.battery.logic.proc.CloseBatteryProcess;
import com.soundgroup.battery.logic.proc.OpenBatteryProcess;
import com.soundgroup.battery.logic.proc.ReceiveBatteryProcess;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.event.EventEnum;

/**
 * @ClassName: ProcessManager
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
         * save battery info process
         */
        INSTANCE.processMap.put(EventEnum.SAVE_BATTERY_INFO,new ReceiveBatteryProcess());
    }
}
