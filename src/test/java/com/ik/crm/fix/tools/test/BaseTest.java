package com.ik.crm.fix.tools.test;

import com.ik.crm.commons.util.HttpUtil;
import com.ik.crm.fix.tools.service.FixDataService;
import com.ik.crm.fix.tools.util.FixWorker;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Bob Jiang on 2017/10/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

    @Autowired
    private FixDataService fixDataService;

    @Autowired
    private FixWorker workThread;

    @Test
    public void work() {
//        String path = "C:\\Users\\Administrator\\Documents\\Tencent Files\\244849875\\FileRecv\\import_records_20171025.xlsx";
//        fixDataService.work(1223, path);
    }

    @Test
    public void read() {

    }

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(19072, 65024, 61910, 110214, 97408, 109142, 32428, 111101, 104875, 59937, 51350, 68111);

        list.stream().distinct().forEach(c -> System.out.print(c + ", "));
    }
}
