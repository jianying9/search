package com.search.localservice;

import com.search.entity.TaskEntity;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.local.Local;
import org.codehaus.jackson.JsonNode;

/**
 *
 * @author aladdin
 */
public interface TaskLocalService extends Local{
    
    public int TYPE_SEARCH = 1;
    
    public int STATE_SPIDER = 1;
    
    public int STATE_FINISHED = 0;
    
    public int STATE_SPIDER_EXCEPTION = 199;
    
    public TaskEntity insertSearchTask(String source, String location, String tag);
    
    public void updateFinishedTask(String taskId);
    
    public void updateSpiderExceptionTask(String taskId);
    
    public JsonNode parseContext(String context);
    
    public InquireResult<TaskEntity> inquireSearchTask(int pageIndex, int pageSize);
}
