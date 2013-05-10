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
public class SpiderSearchTaskImpl implements Task {

    private final TaskLocalService taskLocalService;
    private final TaskEntity taskEntity;

    public SpiderSearchTaskImpl(TaskLocalService taskLocalService, TaskEntity taskEntity) {
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
        String tag = contextNode.get("tag").getTextValue();
        String location = contextNode.get("location").getTextValue();
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("source", source);
        parameterMap.put("tag", tag);
        parameterMap.put("location", location);
        FrameworkSessionBeanRemote frameworkSessionBeanRemote = SpiderRemoteManager.getBrowserProxySessionBeanRemote();
        String result = frameworkSessionBeanRemote.execute("GET_SEARCH_TEXT", parameterMap);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readValue(result, JsonNode.class);
        } catch (IOException e) {
        }
        String flag = rootNode.get("flag").getTextValue();
        if (flag.equals("SUCCESS")) {
            JsonNode textIdArrNode = rootNode.get("data").get("textIdArr");
            Iterator<JsonNode> textIdNodeIterator = textIdArrNode.iterator();
            List<String> textIdList = new ArrayList<String>(50);
            String textId;
            while (textIdNodeIterator.hasNext()) {
                textId = textIdNodeIterator.next().getTextValue();
                textIdList.add(textId);
            }
            this.taskLocalService.updateParseTask(this.taskEntity.getTaskId(), textIdList);
        } else {
            this.taskLocalService.updateSpiderExceptionTask(this.taskEntity.getTaskId());
        }
    }
}
