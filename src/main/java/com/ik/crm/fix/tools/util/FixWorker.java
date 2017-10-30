package com.ik.crm.fix.tools.util;

import com.ik.crm.commons.util.JsonLoader;
import com.ik.crm.fix.tools.service.FixDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bob Jiang on 2017/10/26.
 */
@Component
public class FixWorker {

    private static final Logger log = LoggerFactory.getLogger(FixWorker.class);

    @Autowired
    private FixDataService fixDataService;

    public void work() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        //import_list.txt
        String list = JsonLoader.loadFile("import_source.txt");
        String[] array = list.split("\n");

        for (String value : array) {
            String[] arr = value.split(",");
            Integer orgId = Integer.valueOf(arr[0]);
            String path = arr[1];
            FixThread thread = new FixThread(orgId, path);
            executor.execute(thread);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        log.info("fix work done");
    }

    public void one() {
//        Integer orgId = 111050;
//        String path = "https://o5v2o194t.qnssl.com/72a3b512-9e64-4efa-8718-dfd9676f32bf/import_records_20171025.xlsx";
//        fixDataService.work(orgId, path);
    }

    public class FixThread implements Runnable {

        private Integer orgId;
        private String path;

        public FixThread(Integer orgId, String path) {
            this.orgId = orgId;
            this.path = path;
        }

        @Override
        public void run() {
            try {
                fixDataService.fix(orgId, path);
            } catch (Exception e) {
                log.error("fix worker error! orgId: " + orgId, e);
            }
        }
    }
}
