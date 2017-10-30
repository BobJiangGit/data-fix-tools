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
public class BatchTestWorker {

    private static final Logger log = LoggerFactory.getLogger(BatchTestWorker.class);

    @Autowired
    private FixDataService fixDataService;

    public void work() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            BatchThread thread = new BatchThread();
            executor.execute(thread);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        log.info("fix work done");
    }

    public class BatchThread implements Runnable {

        public BatchThread() {
        }

        @Override
        public void run() {
            try {
                fixDataService.save();
                fixDataService.save();
                fixDataService.batch();
                fixDataService.save();
                fixDataService.save();
            } catch (Exception e) {
                log.error("batch worker error! ");
            }
        }
    }
}
