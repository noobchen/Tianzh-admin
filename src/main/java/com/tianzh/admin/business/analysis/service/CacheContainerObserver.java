package com.tianzh.admin.business.analysis.service;

/**
 * Created by pig on 2015-06-07.
 */
public interface CacheContainerObserver {

    void onAdd(Object object);

    void onEdit(Object object);

    void onDel(Object object);
}
