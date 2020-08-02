package com.homvee.insurancesale.enums;

public enum  AppointmentSateEnum {
    SUCCESS("预约成功"),
    FAIL_BUSY("客户繁忙"),
    FAIL_NOT_CONNECTION("联系不上"),
    FAIL_PURCHASED("已购买"),
    FAIL_NOT_RECOGNIZED("不认可"),
    EXPIRED("已过期"),
    FINISHED("已完成"),
    ;
    private String detail;

    AppointmentSateEnum(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

}
