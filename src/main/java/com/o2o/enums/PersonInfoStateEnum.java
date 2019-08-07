package com.o2o.enums;

public enum PersonInfoStateEnum {
    LOGIN_FAILED(-1, "登录失败"), LOGIN_SUCCESS(0, "登录成功"),
    REGISTER_FAILED(-2,"注册失败"),REGISET_SUCCESS(1,"注册成功"),
    INNER_ERROR(-1001, "操作失败"),EMPTY(-1002, "注册或登录信息为空");

    private int state;

    private String stateInfo;

    PersonInfoStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PersonInfoStateEnum stateOf(int index) {
        for (PersonInfoStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
