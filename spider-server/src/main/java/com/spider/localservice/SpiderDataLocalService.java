package com.spider.localservice;

import com.spider.hentity.HSpiderFollowEntity;
import com.spider.hentity.HSpiderInfoEntity;
import com.spider.hentity.HSpiderSearchEntity;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface SpiderDataLocalService extends Local{
    
    public void insertSearchData(Map<String, String> entityMap);
    
    public void batchInsertSearchData(List<Map<String, String>> entityMapList);
    
    public HSpiderSearchEntity inquireSearchData(String id);
    
    public void insertInfoData(Map<String, String> entityMap);
    
    public void batchInsertInfoData(List<Map<String, String>> entityMapList);
    
    public HSpiderInfoEntity inquireInfoData(String id);
    
    public void insertFollowData(Map<String, String> entityMap);
    
    public void batchInsertFollowData(List<Map<String, String>> entityMapList);
    
    public HSpiderFollowEntity inquireFollowData(String id);
}
