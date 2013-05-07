package com.search.localservice;

import com.search.entity.TaskEntity;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.local.Local;
import java.util.List;
import org.codehaus.jackson.JsonNode;

/**
 *
 * @author aladdin
 */
public interface TaskLocalService extends Local{
    
    public int TYPE_SEARCH = 1;
    
    public int TYPE_INFO = 2;
    
    public int TYPE_FOLLOW = 3;
    
    public int STATE_SPIDER = 1;
    
    public int STATE_PARSE = 2;
    
    public int STATE_FINISHED = 0;
    
    public int STATE_EXCEPTION = 99;
    
    public void insertInfoTask(String source, List<String> sourceIdList);
    
    public TaskEntity insertSearchTask(String source, String location, String tag);
    
    public void insertFollowTask(String source, List<String> sourceIdList);
    
    public void updateParseTask(String taskId, List<String> textIdList);
    
    public void updateFinishedTask(String taskId);
    
    public void updateExceptionTask(String taskId);
    
    public JsonNode parseContext(String context);
    
    public InquireResult<TaskEntity> inquireSpiderTask(int pageIndex, int pageSize);
    
    public InquireResult<TaskEntity> inquireParseTask(int pageIndex, int pageSize);
    
    public InquireResult<TaskEntity> inquireSearchTask(int pageIndex, int pageSize);
}
