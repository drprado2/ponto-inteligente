package com.drprado.pontointeligente.crosscutting.util;

import java.time.Clock;

public class ClockFactory {
    public volatile static Clock currentClock;

    public synchronized static void setClock(Clock clock){
        currentClock = clock;
    }

    public static Clock get(){
        synchronized (ClockFactory.class){
            if(currentClock == null)
                currentClock = Clock.systemDefaultZone();
        }
        return currentClock;
    }
}
