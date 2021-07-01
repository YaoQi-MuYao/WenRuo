package com.wenruo;

import javax.annotation.concurrent.GuardedBy;
import java.beans.Transient;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: MonitorVehicleTracker
 * @author: muyao
 * @description: 基于监视器模式的车辆追踪
 * @date: 2021/3/4 4:29 下午
 * @version: 1.0
 */
public class MonitorVehicleTracker {

    @GuardedBy("this")
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    public synchronized void setLocations(String id ,int x, int y) {
        MutablePoint loc = locations.get(id);
        if (null == loc) {
            throw new IllegalArgumentException("No such ID: " + id);
        }
        loc.setX(x);
        loc.setY(y);
    }

    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id : m.keySet()) {
            result.put(id, new MutablePoint(m.get(id)));
        }
        return Collections.unmodifiableMap(result);
    }
}
