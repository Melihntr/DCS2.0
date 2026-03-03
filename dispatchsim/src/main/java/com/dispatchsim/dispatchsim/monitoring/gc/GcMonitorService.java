package com.dispatchsim.dispatchsim.monitoring.gc;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

@Service
public class GcMonitorService {

    private final List<GarbageCollectorMXBean> gcBeans =
            ManagementFactory.getGarbageCollectorMXBeans();

    @Scheduled(fixedRate = 5000)
    public void logGcStats() {
        for (GarbageCollectorMXBean gc : gcBeans) {
            System.out.println(
                    "GC: " + gc.getName() +
                    " Count: " + gc.getCollectionCount() +
                    " Time: " + gc.getCollectionTime()
            );
        }
    }
}