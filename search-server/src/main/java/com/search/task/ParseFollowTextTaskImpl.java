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
public class ParseFollowTextTaskImpl implements Task {

    private final TaskLocalService taskLocalService;
    private final EmployeeLocalService employeeLocalService;
    private final TaskEntity taskEntity;

    public ParseFollowTextTaskImpl(TaskLocalService taskLocalService, EmployeeLocalService employeeLocalService, TaskEntity taskEntity) {
        this.taskLocalService = taskLocalService;
        this.employeeLocalService = employeeLocalService;
        this.taskEntity = taskEntity;
    }

    @Override
    public void doWhenRejected() {
        this.taskLocalService.updateParseExceptionTask(this.taskEntity.getTaskId());
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
        JsonNode sourceIdArrNode;
        JsonNode sourceIdNode;
        String sourceId;
        Iterator<JsonNode> sourceIdNodeIterator;
        String flag;
        List<String> sourceIdList = new ArrayList<String>(500);
        try {
            while (textIdNodeIterator.hasNext()) {
                textIdNode = textIdNodeIterator.next();
                textId = textIdNode.getTextValue();
                parameterMap.put("textId", textId);
                result = frameworkSessionBeanRemote.execute("PARSE_FOLLOW_TEXT", parameterMap);
                mapper = new ObjectMapper();
                resultNode = mapper.readValue(result, JsonNode.class);
                flag = resultNode.get("flag").getTextValue();
                if (flag.equals("SUCCESS")) {
                    dataNode = resultNode.get("data");
                    sourceIdArrNode = dataNode.get("sourceIdArr");
                    sourceIdNodeIterator = sourceIdArrNode.iterator();
                    while (sourceIdNodeIterator.hasNext()) {
                        sourceIdNode = sourceIdNodeIterator.next();
                        sourceId = sourceIdNode.getTextValue();
                        if (sourceIdList.contains(sourceId) == false) {
                            sourceIdList.add(sourceId);
                        }
                    }
                }
            }
        } catch (IOException e) {
        }
        if (sourceIdList.isEmpty()) {
            this.taskLocalService.updateParseExceptionTask(this.taskEntity.getTaskId());
        } else {
            this.employeeLocalService.batchInsertEmployee(source, sourceIdList);
            this.taskLocalService.updateFinishedTask(this.taskEntity.getTaskId());
        }
    }
}
