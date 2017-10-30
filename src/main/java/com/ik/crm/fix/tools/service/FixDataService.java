package com.ik.crm.fix.tools.service;

/**
 * Created by Bob Jiang on 2017/10/26.
 */
public interface FixDataService {

    void work(Integer orgId, String filePath);

    void fix(Integer orgId, String path);

    void batch();

    void save();

}
