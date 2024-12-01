package io.github.materialapps.notistar.dao;

import java.util.List;

import io.github.materialapps.notistar.entity.AppItem;

//统一封装，但不需要操作数据库
public interface AppDao {
    List<AppItem> getAll();
}
