package com.ik.crm.fix.tools.service.impl;

import com.github.sd4324530.fastexcel.FastExcel;
import com.ik.crm.commons.util.DateUtils;
import com.ik.crm.commons.util.HttpUtil;
import com.ik.crm.fix.tools.mapper.ContactsMapper;
import com.ik.crm.fix.tools.mapper.CustomerAddressesMapper;
import com.ik.crm.fix.tools.mapper.CustomerAssetsMapper;
import com.ik.crm.fix.tools.model.Contacts;
import com.ik.crm.fix.tools.model.CustomerAddresses;
import com.ik.crm.fix.tools.model.CustomerAssets;
import com.ik.crm.fix.tools.model.ExcelModel;
import com.ik.crm.fix.tools.service.FixDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
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

    @Override
    public void work(Integer orgId, String path) {
        try {
            String savePath = File.separator + "var" + File.separator + "files" + File.separator;
            String fileName = UUID.randomUUID().toString().replace("-", "") + ".xlsx";
            HttpUtil.downLoad(path, fileName, savePath);

            Date now = DateUtils.getCurrentDate();
            FastExcel fastExcel = new FastExcel(savePath + fileName);
            fastExcel.setSheetName("导入文件结果记录");

            List<ExcelModel> tests = fastExcel.parse(ExcelModel.class);
            if (null != tests && !tests.isEmpty()) {
                for (ExcelModel model : tests) {
                    List<Integer> ids = customerAddressesMapper.getCustomerId(orgId, model.getName());
                    if (ids != null && ids.size() == 1) {
                        Integer custId = ids.get(0);
                        fixAddress(orgId, model, custId, now);
                        fixContact(orgId, model, custId, now);
                        fixCustomAssets(orgId, model, custId, now);
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

    public void fixAddress(Integer orgId, ExcelModel model, Integer id, Date now) {
        Integer addressId = customerAddressesMapper.getCustomrAddressId(model.getWrongId(), orgId);
        if (addressId != null) {
            CustomerAddresses addresses = new CustomerAddresses();
            addresses.setId(addressId);
            addresses.setAddressable_id(id);
            customerAddressesMapper.updateByPrimaryKeySelective(addresses);
            log.info("customer_addresses, fix wrongId: {} to rightId: {}, name: {}, orgId: {}", model.getWrongId(), id, model.getName(), orgId);
        }
    }

    public void fixContact(Integer orgId, ExcelModel model, Integer id, Date now) {
        Integer contactId = contactsMapper.getContactsId(model.getWrongId(), orgId);
        if (contactId != null) {
            Contacts contacts = new Contacts();
            contacts.setId(contactId);
            contacts.setCustomer_id(id);
            contactsMapper.updateByPrimaryKeySelective(contacts);
            log.info("contacts, fix wrongId: {} to rightId: {}, name: {}, orgId: {}", model.getWrongId(), id, model.getName(), orgId);
        }
    }

    public void fixCustomAssets(Integer orgId, ExcelModel model, Integer id, Date now) {
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

}
