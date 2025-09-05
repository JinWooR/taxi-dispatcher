package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.InternalSearchDriverNearbyGeoAdapter;
import com.taxidispatcher.modules.driver.application.port.in.InternalSearchDriverNearbyGeoCommand;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternalSearchDriverNearbyGeoService implements InternalSearchDriverNearbyGeoAdapter {
    private final DriverRepository driverRepository;

    private class GeoRange {
        public double maxLat;
        public double minLat;
        public double maxLng;
        public double minLng;

        private final double M_PER_DEG_LAT = 110_574d; // 위도 1도 ≈ 110.574 km
        private final double M_PER_DEG_LNG_AT_EQUATOR = 111_320d; // 경도 1도(적도)

        public GeoRange(double lat, double lng, int distance) {
            double dLat = distance / M_PER_DEG_LAT;
            double metersPerDegLng = M_PER_DEG_LNG_AT_EQUATOR * Math.cos(Math.toRadians(lat));
            // 극지대 근처 cos(lat)로 0에 가까워질 수 있으니 최소값 가드
            metersPerDegLng = Math.max(metersPerDegLng, 1e-9);

            double dLng = distance / metersPerDegLng;

            double minLat = clamp(lat - dLat, -90d, 90d);
            double maxLat = clamp(lat + dLat, -90d, 90d);
            double minLng = normalizeLng(lng - dLng);
            double maxLng = normalizeLng(lng + dLng);

            this.maxLat = maxLat;
            this.minLat = minLat;
            this.maxLng = maxLng;
            this.minLng = minLng;
        }

        /** [-180, 180]로 경도 정규화 */
        private double normalizeLng(double lng) {
            double x = lng;
            while (x <= -180d) x += 360d;
            while (x > 180d)  x -= 360d;
            return x;
        }

        private double clamp(double v, double lo, double hi) {
            return Math.max(lo, Math.min(hi, v));
        }
    }

    @Override
    public List<UUID> handle(InternalSearchDriverNearbyGeoCommand command) {
        var geoRange = new GeoRange(command.x(), command.y(), command.distance());

        List<DriverId> driverIdList = driverRepository.findByNearbyGeoDrivers(command.driverIds(), geoRange.maxLat, geoRange.minLat, geoRange.maxLng, geoRange.minLng);

        return driverIdList.stream().map(DriverId::id).toList();
    }
}
