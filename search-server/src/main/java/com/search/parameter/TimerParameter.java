package com.search.parameter;

import com.wolf.framework.data.DataTypeEnum;
import com.wolf.framework.service.parameter.Parameter;
import com.wolf.framework.service.parameter.ParameterConfig;
import com.wolf.framework.service.parameter.ParametersConfig;

/**
 * 第三方源帐号信息
 *
 * @author aladdin
 */
@ParametersConfig()
public class TimerParameter implements Parameter {
    //

    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_60, desc = "定时器状态", filterTypes = {})
    private String timerState;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_10, desc = "操作：start,stop", filterTypes = {})
    private String option;
}
