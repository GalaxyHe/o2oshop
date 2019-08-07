package com.o2o.dto;

import com.o2o.enums.PersonInfoStateEnum;
import com.o2o.pojo.PersonInfo;
import lombok.ToString;

/**
 * @author He
 * @Date 2019/8/2
 * @Time 20:53
 * @Description TODO
 **/

@ToString
public class PersonInfoExecution {
    //结果状态
    private int state;

    //标识状态
    private String stateInfo;

    PersonInfo personInfo;

    public PersonInfoExecution() {
    }

    public PersonInfoExecution(PersonInfoStateEnum personInfoStateEnum) {
        this.state = personInfoStateEnum.getState();
        this.stateInfo = personInfoStateEnum.getStateInfo();
    }

    public PersonInfoExecution(PersonInfoStateEnum personInfoStateEnum, PersonInfo personInfo) {
        this.state = personInfoStateEnum.getState();
        this.stateInfo = personInfoStateEnum.getStateInfo();
        this.personInfo = personInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
