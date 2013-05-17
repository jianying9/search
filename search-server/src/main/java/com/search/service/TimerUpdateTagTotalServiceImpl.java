package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.TagEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TagLocalService;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.TIMER_UPDATE_TAG_TOTAL,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
validateSession = false,
response = true,
description = "定时更新标签统计")
public class TimerUpdateTagTotalServiceImpl implements Service {

    @InjectLocalService()
    private EmployeeLocalService employeeLocalService;
    //
    @InjectLocalService()
    private TagLocalService tagLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageIndex(1);
        inquireContext.setPageSize(20);
        long time = System.currentTimeMillis() - 43200000;
        Condition condition = new Condition("lastUpdateTime", OperateTypeEnum.LESS, Long.toString(time));
        inquireContext.addCondition(condition);
        condition = new Condition("state", OperateTypeEnum.EQUAL, Integer.toString(TagLocalService.STATE_NEW));
        inquireContext.addCondition(condition);
        InquireResult<TagEntity> inquireResult = this.tagLocalService.inquireTag(inquireContext);
        if (inquireResult.isEmpty() == false) {
            //更新时间
            List<TagEntity> tagEntityList = inquireResult.getResultList();
            List<Map<String, String>> updateMapList = new ArrayList<Map<String, String>>(tagEntityList.size());
            int total;
            String tag;
            String state = Integer.toString(TagLocalService.STATE_OLD);
            Map<String, String> updateMap;
            for (TagEntity tagEntity : tagEntityList) {
                tag = tagEntity.getTag();
                total = this.employeeLocalService.countByTag(tag);
                updateMap = new HashMap<String, String>(4, 1);
                updateMap.put("tag", tag);
                updateMap.put("total", Integer.toString(total));
                updateMap.put("state", state);
                updateMapList.add(updateMap);
            }
            //保存
            this.tagLocalService.batchUpdate(updateMapList);
        }
        messageContext.success();
    }
}
