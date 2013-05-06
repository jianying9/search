package com.spider.localservice;

import com.spider.hentity.HSpiderFollowEntity;
import com.spider.hentity.HSpiderInfoEntity;
import com.spider.hentity.HSpiderSearchEntity;
import com.wolf.framework.dao.HEntityDao;
import com.wolf.framework.dao.annotation.InjectHDao;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = SpiderDataLocalService.class,
description = "爬虫数据操作接口")
public class SpiderDataLocalServiceImpl implements SpiderDataLocalService{
    
    @InjectHDao(clazz = HSpiderSearchEntity.class)
    private HEntityDao<HSpiderSearchEntity> hSpiderSearchEntityDao;
    
    @InjectHDao(clazz = HSpiderInfoEntity.class)
    private HEntityDao<HSpiderInfoEntity> hSpiderInfoEntityDao;
    
    @InjectHDao(clazz = HSpiderFollowEntity.class)
    private HEntityDao<HSpiderFollowEntity> hSpiderFollowEntityDao;

    @Override
    public void insertSearchData(Map<String, String> entityMap) {
        this.hSpiderSearchEntityDao.insert(entityMap);
    }

    @Override
    public void batchInsertSearchData(List<Map<String, String>> entityMapList) {
        this.hSpiderSearchEntityDao.batchInsert(entityMapList);
    }
    
    @Override
    public HSpiderSearchEntity inquireSearchData(String id) {
        return this.hSpiderSearchEntityDao.inquireByKey(id);
    }

    @Override
    public void insertInfoData(Map<String, String> entityMap) {
        this.hSpiderInfoEntityDao.insert(entityMap);
    }

    @Override
    public void batchInsertInfoData(List<Map<String, String>> entityMapList) {
        this.hSpiderInfoEntityDao.batchInsert(entityMapList);
    }
    
    @Override
    public HSpiderInfoEntity inquireInfoData(String id) {
        return this.hSpiderInfoEntityDao.inquireByKey(id);
    }

    @Override
    public void insertFollowData(Map<String, String> entityMap) {
        this.hSpiderFollowEntityDao.insert(entityMap);
    }

    @Override
    public void batchInsertFollowData(List<Map<String, String>> entityMapList) {
        this.hSpiderFollowEntityDao.batchInsert(entityMapList);
    }

    @Override
    public HSpiderFollowEntity inquireFollowData(String id) {
        return this.hSpiderFollowEntityDao.inquireByKey(id);
    }
}
