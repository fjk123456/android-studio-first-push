package com.example.data;

import java.io.Serializable;

public class GeolgicalBound_DC implements Serializable {
    //地层界线_基本信息，简写为DC
    private String DC_Relationship;  //接触关系 定义选择，自动判断
    private String DC_strike;  //接触面走向
    private String DC_dip;  //接触面倾向
    private String DC_dip_angle;  //接触面倾角
    private String DC_Position1;  //层 1 方位  描述层位相对位置
    private String DC_Strata_Code1;  //层 1 地层代号
    private String DC_Strata_Charect1;  //层 1 岩性
    private String DC_Strike1;  //层 1 走向
    private String DC_Dip1;  //层 1 倾向
    private String DC_Dip_angle1;  // 层 1 倾角
    private String DC_Description1;  //层 1 地层描述
    private String DC_Position2;  //层 2方位  描述层位相对位置
    private String DC_Strata_Code2;  //层 2 地层代号
    private String DC_Strata_Charect2;  //层 2 岩性
    private String DC_Strike2;  //层 2 走向
    private String DC_Dip2;  //层 2 倾向
    private String DC_Dip_angle2;  // 层 2 倾角
    private String DC_Description2;  //层 2 地层描述
    private String DC_recorder;  //记录
    private String DC_Record_data;  //记录日期
    private String DC_checker;  //校核
    private String DC_Check_data;  //校核日期
    private String DC_Remark;  // 备注
    private String DC_updatetime;  //更新时间

    //地层界线_控制点,简写为DCKZD
    private String DCKZD_x;  //坐标x
    private String DCKZD_y;  // 坐标 Y
    private String DCKZD_h;  //高程
    private String DCKZD_remark;  // 备注
}
