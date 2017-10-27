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
        String path = "C:\\Users\\Administrator\\Documents\\Tencent Files\\244849875\\FileRecv\\import_records_20171025.xlsx";
        fixDataService.work(1223, path);
    }

    @Test
    public void read() {

    }

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(65024,
                65024,
                108032,
                108032,
                111050,
                19072,
                19072,
                109093,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                111101,
                68111,
                109142,
                109142,
                109142,
                16154,
                16154,
                110214,
                110214,
                110214,
                98286,
                16311,
                16311,
                51350,
                51350,
                51350,
                51350,
                51350,
                80377,
                97408,
                32428,
                32428,
                93477,
                93477,
                93477,
                93477,
                93477,
                93477,
                93477,
                97483,
                105489,
                105489,
                50543,
                69563,
                69563,
                69563,
                69563,
                69587,
                69587,
                69587,
                70633,
                13662,
                13662,
                53779,
                53779,
                108815,
                104875,
                104875,
                104875,
                104875,
                104882,
                50886,
                61910,
                61910,
                61910,
                61910,
                52929,
                108929,
                59937,
                59937,
                59937,
                59937,
                59937,
                59937,
                59937,
                59937,
                59937,
                59937,
                59937,
                59937,
                59937,
                59937,
                55958,
                55958,
                55958,
                102968,
                102968,
                109970,
                95974);

        list.stream().distinct().forEach(c -> System.out.print(c + ", "));
    }
}
