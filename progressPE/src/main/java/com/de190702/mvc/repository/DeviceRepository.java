package com.de190702.mvc.repository;

import com.de190702.mvc.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    // Q1.1 - danh sách mặc định, sắp xếp Installation Date giảm dần
    List<Device> findAllByOrderByInstallationDateDesc();

    // Q1.2 - tìm kiếm wildcard (LIKE %x%), không phân biệt hoa thường, vẫn sort giảm dần
    List<Device> findByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(String deviceName);

    // Q3 - kiểm tra Device Code trùng
    Optional<Device> findByDeviceCode(String deviceCode);
}