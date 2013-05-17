package com.search.task;

import com.search.entity.EmployeeEntity;
import com.search.entity.TaskEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TagLocalService;
import com.search.localservice.TaskLocalService;
import com.spider.remote.SpiderRemoteManager;
import com.wolf.framework.remote.FrameworkSessionBeanRemote;
import com.wolf.framework.task.Task;
import com.wolf.framework.task.TaskExecutor;
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
public class SearchTaskImpl implements Task {

    private final TaskExecutor taskExecutor;
    private final TaskLocalService taskLocalService;
    private final TagLocalService tagLocalService;
    private final EmployeeLocalService employeeLocalService;
    private final TaskEntity taskEntity;

    public SearchTaskImpl(TaskExecutor taskExecutor, TaskLocalService taskLocalService, TagLocalService tagLocalService, EmployeeLocalService employeeLocalService, TaskEntity taskEntity) {
        this.taskExecutor = taskExecutor;
        this.taskLocalService = taskLocalService;
        this.tagLocalService = tagLocalService;
        this.employeeLocalService = employeeLocalService;
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
        String result = frameworkSessionBeanRemote.execute("GET_SEARCH", parameterMap);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resultNode;
        try {
            resultNode = mapper.readValue(result, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String flag = resultNode.get("flag").getTextValue();
        if (flag.equals("SUCCESS")) {
            JsonNode sourceIdNode;
            String sourceId;
            List<String> sourceIdList = new ArrayList<String>(500);
            JsonNode dataNode = resultNode.get("data");
            JsonNode sourceIdArrNode = dataNode.get("sourceIdArr");
            Iterator<JsonNode> sourceIdNodeIterator = sourceIdArrNode.iterator();
            while (sourceIdNodeIterator.hasNext()) {
                sourceIdNode = sourceIdNodeIterator.next();
                sourceId = sourceIdNode.getTextValue();
                sourceIdList.add(sourceId);
            }
            //抓取人员
            if (sourceIdList.isEmpty() == false) {
                Task task;
                String empId;
                EmployeeEntity empEntity;
                List<Task> taskList = new ArrayList<Task>(sourceIdList.size());
                for (String sId : sourceIdList) {
                    empId = this.employeeLocalService.createEmpId(source, sId);
                    empEntity = this.employeeLocalService.inquireByEmpId(empId);
                    if (empEntity == null) {
                        task = new InsertEmployeeTaskImpl(this.employeeLocalService, this.tagLocalService, empId);
                    } else {
                        task = new UpdateEmployeeTaskImpl(this.employeeLocalService, this.tagLocalService, empId);
                    }
                    taskList.add(task);
                }
                this.taskExecutor.syncSubmit(taskList);
            }
            this.taskLocalService.updateFinishedTask(this.taskEntity.getTaskId());
        } else {
            this.taskLocalService.updateSpiderExceptionTask(this.taskEntity.getTaskId());
        }
    }
}
