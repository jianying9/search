package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.TagEntity;
import com.search.localservice.TagLocalService;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.dao.condition.Order;
import com.wolf.framework.dao.condition.OrderTypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INQUIRE_TAG,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
returnParameter = {"tag", "total", "lastUpdateTime"},
parametersConfigs = {TagEntity.class},
validateSession = false,
page = true,
response = true,
description = "查询标签统计信息服务")
public class InquireTagServiceImpl implements Service {
    
    @InjectLocalService()
    private TagLocalService tagLocalService;
    
    @Override
    public void execute(MessageContext messageContext) {
        //小写
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageIndex(messageContext.getPageIndex());
        inquireContext.setPageSize(messageContext.getPageSize());
        Condition condition;
        condition = new Condition("state", OperateTypeEnum.LESS, Integer.toString(TagLocalService.STATE_INGORE));
        inquireContext.addCondition(condition);
        Order order = new Order("total", OrderTypeEnum.DESC);
        inquireContext.addOrder(order);
        InquireResult<TagEntity> inquireResult = this.tagLocalService.inquireTag(inquireContext);
        if (inquireResult.isEmpty() == false) {
            List<TagEntity> tagEntityList = inquireResult.getResultList();
            messageContext.setPageTotal(inquireResult.getTotal());
            messageContext.setPageNum(inquireResult.getPageNum());
            messageContext.setEntityListData(tagEntityList);
            messageContext.success();
        }
    }
}
