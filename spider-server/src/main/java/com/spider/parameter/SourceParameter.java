package com.spider.parameter;

import com.wolf.framework.data.DataTypeEnum;
import com.wolf.framework.service.parameter.Parameter;
import com.wolf.framework.service.parameter.ParameterConfig;
import com.wolf.framework.service.parameter.ParameterTypeEnum;
import com.wolf.framework.service.parameter.ParametersConfig;

/**
 * 第三方源帐号信息
 *
 * @author aladdin
 */
@ParametersConfig()
public class SourceParameter implements Parameter {
    //

    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_60, desc = "第三方来源帐号", filterTypes = {})
    private String userName;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_10, desc = "来源标识", filterTypes = {})
    private String source;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "密码", filterTypes = {})
    private String password;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_60, desc = "文本ID", filterTypes = {})
    private String textId;
    //
    @ParameterConfig(parameterTypeEnum = ParameterTypeEnum.JSON, dateTypeEnum = DataTypeEnum.UUID, desc = "文本ID数组", filterTypes = {})
    private String textIdArr;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_60, desc = "第三方ID", filterTypes = {})
    private String sourceId;
    //
    @ParameterConfig(parameterTypeEnum = ParameterTypeEnum.JSON, dateTypeEnum = DataTypeEnum.UUID, desc = "第三方ID数组", filterTypes = {})
    private String sourceIdArr;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "昵称")
    private String nickName;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "姓名")
    private String empName;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "地区", filterTypes = {})
    private String location;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_4000, desc = "标签", filterTypes = {})
    private String tag;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_10, desc = "性别", filterTypes = {})
    private String gender;
}
