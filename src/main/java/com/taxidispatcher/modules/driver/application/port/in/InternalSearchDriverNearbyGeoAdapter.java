package com.taxidispatcher.modules.driver.application.port.in;


import java.util.List;
import java.util.UUID;

public interface InternalSearchDriverNearbyGeoAdapter {
    List<UUID> handle(InternalSearchDriverNearbyGeoCommand command);
}
