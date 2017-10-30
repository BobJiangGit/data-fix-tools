package com.ik.crm.fix.tools.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.github.sd4324530.fastexcel.FastExcel;
import com.google.common.collect.Lists;
import com.ik.crm.commons.constant.GlobalConstants;
import com.ik.crm.commons.util.DateUtils;
import com.ik.crm.commons.util.HttpUtil;
import com.ik.crm.fix.tools.mapper.ContactsMapper;
import com.ik.crm.fix.tools.mapper.CustomerAddressesMapper;
import com.ik.crm.fix.tools.mapper.CustomerAssetsMapper;
import com.ik.crm.fix.tools.mapper.cust.CustomersMapper;
import com.ik.crm.fix.tools.model.Contacts;
import com.ik.crm.fix.tools.model.CustomerAddresses;
import com.ik.crm.fix.tools.model.CustomerAssets;
import com.ik.crm.fix.tools.model.ExcelModel;
import com.ik.crm.fix.tools.model.cust.Customers;
import com.ik.crm.fix.tools.service.FixDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by Bob Jiang on 2017/10/26.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class FixDataServiceImpl implements FixDataService {

    private static final Logger log = LoggerFactory.getLogger(FixDataServiceImpl.class);

    @Autowired
    private CustomerAddressesMapper customerAddressesMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private CustomerAssetsMapper customerAssetsMapper;

    @Autowired
    private CustomersMapper customersMapper;

    static SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Customers.class, new String [] {"id", "name"});

    @Override
    public void fix(Integer orgId, String path) {
        try {
            String savePath = File.separator + "var" + File.separator + "files" + File.separator;
            String fileName = UUID.randomUUID().toString().replace("-", "") + ".xlsx";
            HttpUtil.downLoad(path, fileName, savePath);

            FastExcel fastExcel = new FastExcel(savePath + fileName);

            String sheet = "客户";
            if (orgId == 111101) {
                sheet = "学员";
            } else if (orgId == 104875) {
                sheet = "推荐客户";
            }
            fastExcel.setSheetName(sheet);

            List<ExcelModel> tests = fastExcel.parse(ExcelModel.class);
            if (null != tests && !tests.isEmpty()) {
                for (ExcelModel model : tests) {
                    String realName = model.getRealName();
                    String phone = model.getRealPhone();

                    if (realName != null && !"".equals(realName)) {
                        List<Integer> ids = customerAddressesMapper.getSourceCustomer(orgId, realName);
                        if (ids != null && ids.size() == 1) {
                            Integer custId = ids.get(0);
                            fixAddress(orgId, realName, phone, custId);
                        } else {
                            log.info("customer should not be fix, orgId: {}, name: {}, phone: {}",
                                    orgId, realName, phone);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("fix error", e);
        }
    }

    @Override
    public void batch() {
        List<Customers> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Customers cust = new Customers();
            cust.setName("test_batch_" + UUID.randomUUID().toString().replaceAll("-",""));
            cust.setOrganization_id(12004);
            list.add(cust);
        }

        customersMapper.batchInsertSelective(list);
        log.info("batch insert customer, {}", JSON.toJSONString(list, filter));
    }

    @Override
    public void save() {
        Customers cust = new Customers();
        cust.setOrganization_id(12004);
        cust.setName("test_save_" + UUID.randomUUID().toString().replaceAll("-",""));
        customersMapper.insertSelective(cust);
        log.info("save customer, {}", JSON.toJSONString(cust, filter));
    }

    @Override
    public void work(Integer orgId, String path) {
        try {
            String savePath = File.separator + "var" + File.separator + "files" + File.separator;
            String fileName = UUID.randomUUID().toString().replace("-", "") + ".xlsx";
            HttpUtil.downLoad(path, fileName, savePath);

            FastExcel fastExcel = new FastExcel(savePath + fileName);
            fastExcel.setSheetName("导入文件结果记录");

            List<ExcelModel> tests = fastExcel.parse(ExcelModel.class);
            if (null != tests && !tests.isEmpty()) {
                for (ExcelModel model : tests) {
                    List<Integer> ids = customerAddressesMapper.getCustomerId(orgId, model.getName());
                    if (ids != null && ids.size() == 1) {
                        Integer custId = ids.get(0);
                        fixAddress(orgId, model, custId);
                        fixContact(orgId, model, custId);
                        fixCustomAssets(orgId, model, custId);
                    } else {
                        log.info("customer is not exist or updated or multiple, can't fix, name: {}, wrongId:{}, orgId: {}, id: {}",
                                model.getName(), model.getWrongId(), orgId, ids);
                    }
                }
            }
        } catch (Exception e) {
            log.error("fix error", e);
        }
    }

    public void fixAddress(Integer orgId, ExcelModel model, Integer id) {
        Integer addressId = customerAddressesMapper.getCustomrAddressId(model.getWrongId(), orgId);
        if (addressId != null) {
            CustomerAddresses addresses = new CustomerAddresses();
            addresses.setId(addressId);
            addresses.setAddressable_id(id);
            customerAddressesMapper.updateByPrimaryKeySelective(addresses);
            log.info("customer_addresses, fix wrongId: {} to rightId: {}, name: {}, orgId: {}", model.getWrongId(), id, model.getName(), orgId);
        }
    }

    public void fixContact(Integer orgId, ExcelModel model, Integer id) {
        Integer contactId = contactsMapper.getContactsId(model.getWrongId(), orgId);
        if (contactId != null) {
            Contacts contacts = new Contacts();
            contacts.setId(contactId);
            contacts.setCustomer_id(id);
            contactsMapper.updateByPrimaryKeySelective(contacts);
            log.info("contacts, fix wrongId: {} to rightId: {}, name: {}, orgId: {}", model.getWrongId(), id, model.getName(), orgId);
        }
    }

    public void fixCustomAssets(Integer orgId, ExcelModel model, Integer id) {
        List<Integer> ids = customerAssetsMapper.getCustomerAssetsList(model.getWrongId(), orgId);
        if (!ids.isEmpty()) {
            for (Integer assetId : ids) {
                CustomerAssets customerAssets = new CustomerAssets();
                customerAssets.setId(assetId);
                customerAssets.setEntity_id(id);
                customerAssetsMapper.updateByPrimaryKeySelective(customerAssets);
                log.info("customer_assets, fix wrongId: {} to rightId: {}, name: {}, orgId: {}", model.getWrongId(), id, model.getName(), orgId);
            }
        }
    }

    public void fixAddress(Integer orgId, String name, String phone, Integer custId) {
        if (phone != null && !"".equals(phone)) {
            List<Integer> list = customerAddressesMapper.getAddressIds(orgId, phone);
            if (list != null && list.size() == 1) {
                Integer addrId = list.get(0);
                CustomerAddresses addresses = new CustomerAddresses();
                addresses.setId(addrId);
                addresses.setAddressable_id(custId);
                customerAddressesMapper.updateByPrimaryKeySelective(addresses);
                log.info("customer_addresses, fix address addrId: {} to customer: {}, phone: {}, orgId: {}, name: {}",
                        addrId, custId, phone, orgId, name);
            }
        }
    }

}
