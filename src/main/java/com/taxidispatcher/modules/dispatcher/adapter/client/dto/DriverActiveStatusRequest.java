package com.taxidispatcher.modules.dispatcher.adapter.client.dto;

public record DriverActiveStatusRequest(DriverActiveStatus activeStatus) {
    public enum DriverActiveStatus {
        LEAVE_WORK, // 퇴근
        WAITING, // (배차) 대기중
        IN_OPERATION, // 배차로 인한 운행중
        PAUSE; // (퇴근X) 잠시 휴식 시간
    }

    public static DriverActiveStatusRequest start() {
        return new DriverActiveStatusRequest(DriverActiveStatus.IN_OPERATION);
    }

    public static DriverActiveStatusRequest waiting() {
        return new DriverActiveStatusRequest(DriverActiveStatus.WAITING);
    }
}
