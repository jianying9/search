package com.search.task;

import com.search.entity.TaskEntity;
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

/**
 *
 * @author aladdin
 */
public class SpiderFollowTaskImpl implements Task {
    
    private final TaskLocalService taskLocalService;
    private final TaskEntity taskEntity;
    
    public SpiderFollowTaskImpl(TaskLocalService taskLocalService, TaskEntity taskEntity) {
        this.taskLocalService = taskLocalService;
        this.taskEntity = taskEntity;
    }
    
    @Override
    public void doWhenRejected() {
        this.taskLocalService.updateSpiderExceptionTask(this.taskEntity.getTaskId());
    }
    
    @Override
    public void run() {
        JsonNode contextNode = this.taskLocalService.parseContext(this.taskEntity.getContext());
        String source = this.taskEntity.getSource();
        JsonNode sourceIdArrNode = contextNode.get("sourceIdArr");
        Iterator<JsonNode> sourceIdNodeIterator = sourceIdArrNode.iterator();
        String sourceId;
        JsonNode sourceIdNode;
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("source", source);
        FrameworkSessionBeanRemote frameworkSessionBeanRemote = SpiderRemoteManager.getBrowserProxySessionBeanRemote();
        String result;
        ObjectMapper mapper;
        List<String> textIdList = new ArrayList<String>(20);
        JsonNode resultNode;
        JsonNode dataNode;
        String flag;
        String textId;
        try {
            while (sourceIdNodeIterator.hasNext()) {
                sourceIdNode = sourceIdNodeIterator.next();
                sourceId = sourceIdNode.getTextValue();
                parameterMap.put("sourceId", sourceId);
                result = frameworkSessionBeanRemote.execute("GET_FOLLOW_TEXT", parameterMap);
                mapper = new ObjectMapper();
                resultNode = mapper.readValue(result, JsonNode.class);
                flag = resultNode.get("flag").getTextValue();
                if (flag.equals("SUCCESS")) {
                    dataNode = resultNode.get("data");
                    textId = dataNode.get("textId").getTextValue();
                    textIdList.add(textId);
                }
            }
        } catch (IOException e) {
        }
        if(textIdList.isEmpty()) {
            this.taskLocalService.updateSpiderExceptionTask(this.taskEntity.getTaskId());
        } else {
            this.taskLocalService.updateParseTask(this.taskEntity.getTaskId(), textIdList);
        }
    }
}
