package com.search.task;

import com.search.entity.TaskEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TaskLocalService;
import com.spider.remote.SpiderRemoteManager;
import com.wolf.framework.remote.FrameworkSessionBeanRemote;
import com.wolf.framework.task.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

/**
 *
 * @author aladdin
 */
public class ParseInfoTextTaskImpl implements Task {

    private final TaskLocalService taskLocalService;
    private final EmployeeLocalService employeeLocalService;
    private final TaskEntity taskEntity;

    public ParseInfoTextTaskImpl(TaskLocalService taskLocalService, EmployeeLocalService employeeLocalService, TaskEntity taskEntity) {
        this.taskLocalService = taskLocalService;
        this.employeeLocalService = employeeLocalService;
        this.taskEntity = taskEntity;
    }

    @Override
    public void doWhenRejected() {
        this.taskLocalService.updateExceptionTask(this.taskEntity.getTaskId());
    }

    @Override
    public void run() {
        JsonNode contextNode = this.taskLocalService.parseContext(this.taskEntity.getContext());
        String source = this.taskEntity.getSource();
        ArrayNode textIdArrNode = (ArrayNode) contextNode.get("textIdArr");
        Iterator<JsonNode> textIdNodeIterator = textIdArrNode.iterator();
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("source", source);
        FrameworkSessionBeanRemote frameworkSessionBeanRemote = SpiderRemoteManager.getBrowserProxySessionBeanRemote();
        String result;
        String textId;
        JsonNode textIdNode;
        ObjectMapper mapper;
        JsonNode resultNode;
        JsonNode dataNode;
        String sourceId;
        String gender;
        String nickName;
        String empName;
        String location;
        String tag;
        String flag;
        List<Map<String, String>> infoMapList = new ArrayList<Map<String, String>>(20);
        Map<String, String> infoMap;
        try {
            while (textIdNodeIterator.hasNext()) {
                textIdNode = textIdNodeIterator.next();
                textId = textIdNode.getTextValue();
                parameterMap.put("textId", textId);
                result = frameworkSessionBeanRemote.execute("PARSE_INFO_TEXT", parameterMap);
                mapper = new ObjectMapper();
                resultNode = mapper.readValue(result, JsonNode.class);
                flag = resultNode.get("flag").getTextValue();
                if (flag.equals("SUCCESS")) {
                    dataNode = resultNode.get("data");
                    infoMap = new HashMap<String, String>(8, 1);
                    sourceId = dataNode.get("sourceId").getTextValue();
                    gender = dataNode.get("gender").getTextValue();
                    nickName = dataNode.get("nickName").getTextValue();
                    empName = dataNode.get("empName").getTextValue();
                    location = dataNode.get("location").getTextValue();
                    tag = dataNode.get("tag").getTextValue();
                    infoMap.put("sourceId", sourceId);
                    infoMap.put("gender", gender);
                    infoMap.put("nickName", nickName);
                    infoMap.put("empName", empName);
                    infoMap.put("location", location);
                    infoMap.put("tag", tag);
                    infoMapList.add(infoMap);
                }
            }
        } catch (IOException e) {
        }
        this.employeeLocalService.batchUpdate(source, infoMapList);
        this.taskLocalService.updateFinishedTask(this.taskEntity.getTaskId());
    }
}
