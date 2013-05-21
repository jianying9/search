package com.search.task;

import com.search.entity.TagEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TagLocalService;
import com.spider.remote.SpiderRemoteManager;
import com.wolf.framework.remote.FrameworkSessionBeanRemote;
import com.wolf.framework.task.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author aladdin
 */
public class LocalUpdateEmployeeTaskImpl implements Task {

    private final EmployeeLocalService employeeLocalService;
    private final TagLocalService tagLocalService;
    private final String empId;

    public LocalUpdateEmployeeTaskImpl(EmployeeLocalService employeeLocalService, TagLocalService tagLocalService, String empId) {
        this.employeeLocalService = employeeLocalService;
        this.tagLocalService = tagLocalService;
        this.empId = empId;
    }

    @Override
    public void doWhenRejected() {
    }

    @Override
    public void run() {
        String[] sourceInfo = this.employeeLocalService.parseEmpId(this.empId);
        String source = sourceInfo[0];
        String sourceId = sourceInfo[1];
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("source", source);
        parameterMap.put("sourceId", sourceId);
        FrameworkSessionBeanRemote frameworkSessionBeanRemote = SpiderRemoteManager.getBrowserProxySessionBeanRemote();
        //获取人员信息
        String result = frameworkSessionBeanRemote.execute("GET_LOCAL_INFO", parameterMap);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resultNode;
        JsonNode dataNode;
        try {
            resultNode = mapper.readValue(result, JsonNode.class);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        String flag = resultNode.get("flag").getTextValue();
        if (flag.equals("SUCCESS")) {
            dataNode = resultNode.get("data");
            Map<String, String> dataMap = new HashMap<String, String>(8, 1);
            String gender = dataNode.get("gender").getTextValue();
            String nickName = dataNode.get("nickName").getTextValue();
            String empName = dataNode.get("empName").getTextValue();
            String location = dataNode.get("location").getTextValue();
            String tag = dataNode.get("tag").getTextValue();
            dataMap.put("empId", this.empId);
            dataMap.put("gender", gender);
            dataMap.put("nickName", nickName);
            dataMap.put("empName", empName);
            dataMap.put("location", location);
            dataMap.put("tag", tag);
            //保存
            this.employeeLocalService.update(dataMap);
            //解析标签
            if (tag.isEmpty() == false) {
                String[] tagArr = tag.split(" ");
                List<String> insertTagList = new ArrayList<String>(tagArr.length);
                List<String> updateTagList = new ArrayList<String>(tagArr.length);
                TagEntity tagEntity;
                for (String t : tagArr) {
                    if (insertTagList.contains(t) == false && updateTagList.contains(t) == false) {
                        tagEntity = this.tagLocalService.inquireByTag(t);
                        if (tagEntity == null) {
                            insertTagList.add(t);
                        } else {
                            updateTagList.add(t);
                        }
                    }
                }
                if (insertTagList.isEmpty() == false) {
                    this.tagLocalService.batchInsert(insertTagList);
                }
                if (updateTagList.isEmpty() == false) {
                    this.tagLocalService.batchUpdateNewState(updateTagList);
                }
            }
        }
    }
}
