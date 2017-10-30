package com.ik.crm.fix.tools;

import com.ik.crm.commons.dto.ResultResponse;
import com.ik.crm.fix.tools.service.FixDataService;
import com.ik.crm.fix.tools.util.BatchTestWorker;
import com.ik.crm.fix.tools.util.FixWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bob Jiang on 2017/7/19.
 */
@RestController
@SpringBootApplication
public class Application {

    @Autowired
    private FixWorker fixWorker;

    @Autowired
    private BatchTestWorker batchTestWorker;

    @Autowired
    private FixDataService fixDataService;

    @RequestMapping("run")
    public ResultResponse<?> run() {
        batchTestWorker.work();
        return ResultResponse.success();
    }

    @RequestMapping("batch")
    public ResultResponse<?> batch() {
        fixDataService.batch();
        return ResultResponse.success();
    }

//    @RequestMapping("/work")
//    public ResultResponse<?> work() {
//        fixWorker.work();
//        return ResultResponse.success();
//    }

//    @RequestMapping("/one")
//    public ResultResponse<?> one() {
//        fixWorker.one();
//        return ResultResponse.success();
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
