package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.Device;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface DeviceRepository extends R2dbcRepository<Device, Long> {
}
