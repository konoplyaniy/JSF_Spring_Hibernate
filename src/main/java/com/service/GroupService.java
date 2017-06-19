package com.service;


import com.dao.GroupDao;
import com.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("groupService")
public class GroupService {

    @Autowired
    private static GroupDao groupDao;

    public static GroupDao getGroupDao() {
        return groupDao;
    }

    public static void setGroupDao(GroupDao groupDao) {
        GroupService.groupDao = groupDao;
    }

    @Transactional
    public boolean exist(String groupName) {
        return groupDao.exists(groupName);
    }

    @Transactional
    public void persist(GroupEntity entity) {
        groupDao.persist(entity);
    }

    @Transactional
    public GroupEntity findById(Integer id) {
        return groupDao.findById(id);
    }

    @Transactional
    public GroupEntity findByName(String name) {
        return groupDao.findByName(name);
    }

}
