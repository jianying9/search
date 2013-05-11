package com.search.localservice;

import com.search.entity.TaskEntity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.annotation.InjectDao;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.dao.condition.Order;
import com.wolf.framework.dao.condition.OrderTypeEnum;
import com.wolf.framework.local.LocalServiceConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = TaskLocalService.class,
description = "负责操作task表")
public class TaskLocalServiceImpl implements TaskLocalService {

    @InjectDao(clazz = TaskEntity.class)
    private EntityDao<TaskEntity> taskEntityDao;

    private ObjectNode readJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode;
        try {
            rootNode = mapper.readValue(json, ObjectNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rootNode;
    }

    @Override
    public void insertInfoTask(String source, List<String> sourceIdList) {
        Map<String, String> insertMap = new HashMap<String, String>(8, 1);
        insertMap.put("type", Integer.toString(TaskLocalService.TYPE_INFO));
        insertMap.put("state", Integer.toString(TaskLocalService.STATE_SPIDER));
        insertMap.put("source", source);
        insertMap.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
        ObjectNode rootNode = new ObjectNode(JsonNodeFactory.instance);
        rootNode.putArray("sourceIdArr");
        ArrayNode sourceIdArrNode = (ArrayNode) rootNode.get("sourceIdArr");
        for (String sourceId : sourceIdList) {
            sourceIdArrNode.add(sourceId);
        }
        String context = rootNode.toString();
        insertMap.put("context", context);
        this.taskEntityDao.insert(insertMap);
    }

    @Override
    public TaskEntity insertSearchTask(String source, String location, String tag) {
        Map<String, String> insertMap = new HashMap<String, String>(8, 1);
        insertMap.put("type", Integer.toString(TaskLocalService.TYPE_SEARCH));
        insertMap.put("state", Integer.toString(TaskLocalService.STATE_SPIDER));
        insertMap.put("source", source);
        insertMap.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
        ObjectNode rootNode = new ObjectNode(JsonNodeFactory.instance);
        rootNode.put("location", location);
        rootNode.put("tag", tag);
        String context = rootNode.toString();
        insertMap.put("context", context);
        return this.taskEntityDao.insertAndInquire(insertMap);
    }

    @Override
    public void insertFollowTask(String source, List<String> sourceIdList) {
        Map<String, String> insertMap = new HashMap<String, String>(8, 1);
        insertMap.put("type", Integer.toString(TaskLocalService.TYPE_FOLLOW));
        insertMap.put("state", Integer.toString(TaskLocalService.STATE_SPIDER));
        insertMap.put("source", source);
        insertMap.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
        ObjectNode rootNode = new ObjectNode(JsonNodeFactory.instance);
        rootNode.putArray("sourceIdArr");
        ArrayNode sourceIdArrNode = (ArrayNode) rootNode.get("sourceIdArr");
        for (String sourceId : sourceIdList) {
            sourceIdArrNode.add(sourceId);
        }
        String context = rootNode.toString();
        insertMap.put("context", context);
        this.taskEntityDao.insert(insertMap);
    }

    @Override
    public void updateParseTask(String taskId, List<String> textIdList) {
        TaskEntity taskEntity = this.taskEntityDao.inquireByKey(taskId);
        if (taskEntity != null) {
            String context = taskEntity.getContext();
            ObjectNode rootNode = this.readJson(context);
            rootNode.putArray("textIdArr");
            ArrayNode textIdArrNode = (ArrayNode) rootNode.get("textIdArr");
            if (textIdList.isEmpty() == false) {
                for (String textId : textIdList) {
                    textIdArrNode.add(textId);
                }
            }
            context = rootNode.toString();
            Map<String, String> updateMap = new HashMap<String, String>(4, 1);
            updateMap.put("taskId", taskId);
            updateMap.put("state", Integer.toString(TaskLocalService.STATE_PARSE));
            updateMap.put("context", context);
            this.taskEntityDao.update(updateMap);
        }
    }

    @Override
    public void updateFinishedTask(String taskId) {
        Map<String, String> updateMap = new HashMap<String, String>(2, 1);
        updateMap.put("taskId", taskId);
        updateMap.put("state", Integer.toString(TaskLocalService.STATE_FINISHED));
        this.taskEntityDao.update(updateMap);
    }

    @Override
    public void updateSpiderExceptionTask(String taskId) {
        Map<String, String> updateMap = new HashMap<String, String>(2, 1);
        updateMap.put("taskId", taskId);
        updateMap.put("state", Integer.toString(TaskLocalService.STATE_SPIDER_EXCEPTION));
        this.taskEntityDao.update(updateMap);
    }

    @Override
    public void updateParseExceptionTask(String taskId) {
        Map<String, String> updateMap = new HashMap<String, String>(2, 1);
        updateMap.put("taskId", taskId);
        updateMap.put("state", Integer.toString(TaskLocalService.STATE_PARSE_EXCEPTION));
        this.taskEntityDao.update(updateMap);
    }

    @Override
    public JsonNode parseContext(String context) {
        return this.readJson(context);
    }

    @Override
    public List<TaskEntity> inquireSpiderTask() {
        InquireContext inquireContext = new InquireContext();
        Condition condition = new Condition("state", OperateTypeEnum.EQUAL, Integer.toString(TaskLocalService.STATE_SPIDER));
        inquireContext.addCondition(condition);
        List<TaskEntity> taskEntityList = this.taskEntityDao.inquireByCondition(inquireContext);
        return taskEntityList;
    }

    @Override
    public List<TaskEntity> inquireParseTask() {
        InquireContext inquireContext = new InquireContext();
        Condition condition = new Condition("state", OperateTypeEnum.EQUAL, Integer.toString(TaskLocalService.STATE_PARSE));
        inquireContext.addCondition(condition);
        List<TaskEntity> taskEntityList = this.taskEntityDao.inquireByCondition(inquireContext);
        return taskEntityList;
    }

    @Override
    public InquireResult<TaskEntity> inquireSearchTask(int pageIndex, int pageSize) {
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageIndex(pageIndex);
        inquireContext.setPageSize(pageSize);
        Condition condition = new Condition("type", OperateTypeEnum.EQUAL, Integer.toString(TaskLocalService.TYPE_SEARCH));
        inquireContext.addCondition(condition);
        Order order = new Order("lastUpdateTime", OrderTypeEnum.DESC);
        inquireContext.addOrder(order);
        InquireResult<TaskEntity> inquireResult = this.taskEntityDao.inquirePageByCondition(inquireContext);
        return inquireResult;
    }
}
