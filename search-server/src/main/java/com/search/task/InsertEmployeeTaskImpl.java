package com.search.task;

import com.search.entity.EmployeeEntity;
import com.search.localservice.EmployeeLocalService;
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
public class InsertEmployeeTaskImpl implements Task {

    private final EmployeeLocalService employeeLocalService;
    private final String empId;

    public InsertEmployeeTaskImpl(EmployeeLocalService employeeLocalService, String empId) {
        this.employeeLocalService = employeeLocalService;
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
        String result = frameworkSessionBeanRemote.execute("GET_INFO", parameterMap);
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
            Map<String, String> infoMap = new HashMap<String, String>(8, 1);
            String gender = dataNode.get("gender").getTextValue();
            String nickName = dataNode.get("nickName").getTextValue();
            String empName = dataNode.get("empName").getTextValue();
            String location = dataNode.get("location").getTextValue();
            String tag = dataNode.get("tag").getTextValue();
            infoMap.put("empId", this.empId);
            infoMap.put("gender", gender);
            infoMap.put("nickName", nickName);
            infoMap.put("empName", empName);
            infoMap.put("location", location);
            infoMap.put("tag", tag);
            //保存
            EmployeeEntity empEntity = this.employeeLocalService.inquireByEmpId(empId);
            if(empEntity == null) {
                this.employeeLocalService.insert(infoMap);
            } else {
                this.employeeLocalService.update(infoMap);
            }
            //获取关注信息
            result = frameworkSessionBeanRemote.execute("GET_FOLLOW", parameterMap);
            mapper = new ObjectMapper();
            try {
                resultNode = mapper.readValue(result, JsonNode.class);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            flag = resultNode.get("flag").getTextValue();
            if (flag.equals("SUCCESS")) {
                dataNode = resultNode.get("data");
                JsonNode sourceIdArrNode = dataNode.get("sourceIdArr");
                Iterator<JsonNode> sourceIdNodeIterator = sourceIdArrNode.iterator();
                List<String> sourceIdList = new ArrayList<String>(50);
                JsonNode sourceIdNode;
                while (sourceIdNodeIterator.hasNext()) {
                    sourceIdNode = sourceIdNodeIterator.next();
                    sourceId = sourceIdNode.getTextValue();
                    if (sourceIdList.contains(sourceId) == false) {
                        sourceIdList.add(sourceId);
                    }
                }
                this.employeeLocalService.batchInsertEmployee(source, sourceIdList);
            }
        } else {
            this.employeeLocalService.updateToException(this.empId);
        }
    }
}
