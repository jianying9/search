package com.search.localservice;

import com.search.entity.TagEntity;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface TagLocalService extends Local{
    
    public int STATE_OLD = 0;
    
    public int STATE_NEW = 1;
    
    public TagEntity inquireByTag(String tag);
    
    public void batchInsert(List<String> tagList);
    
    public void batchUpdateNewState(List<String> tagList);
    
    public void batchUpdate(List<Map<String, String>> updateMapList);
    
    public InquireResult<TagEntity> inquireTag(InquireContext inquireContext);
}
