package com.nexmore.sims.excel.vo;

import lombok.Data;

@Data
public class ExcelVO {

    public enum ERROR_TYPE{
        NON_EQP_ID, NON_TANGO_EQP_ID, NON_IP_ADDRESS
    }

    private String EQP_ID;
    private String SVC_NAME;
    private String IP_ADDRESS;
    private String USER_ID;
    private ERROR_TYPE resultCode;
}
