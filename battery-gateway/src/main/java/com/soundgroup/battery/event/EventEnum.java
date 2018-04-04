package com.soundgroup.battery.event;

public enum EventEnum {

     OPEN_BATTERY( (short) 4),

     CLOSE_BATTERY( (short) 5),

     HTTP_BATTERY_INFO( (short) 6),

     SAVE_BATTERY_INFO( (short) 7);

    private short v;

    private EventEnum(short v) {
        this.v = v;
    }

    public short
    getVal() {
        return v;
    }

    public static EventEnum valuesOf(short e) {
        EventEnum[] vs = EventEnum.values();
        if (vs == null || vs.length == 0) {
            return null;
        }
        for (EventEnum event : vs) {
            if (event.getVal() == e) {
                return event;
            }
        }
        return null;
    }

}
