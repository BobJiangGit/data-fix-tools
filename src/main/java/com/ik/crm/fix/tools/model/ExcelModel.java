package com.ik.crm.fix.tools.model;

import com.github.sd4324530.fastexcel.annotation.MapperCell;

/**
 * Created by Bob Jiang on 2017/10/26.
 */
public class ExcelModel {

    private Integer orgId;

    @MapperCell(cellName = "名字")
    private String name;

    @MapperCell(cellName = "链接")
    private String url;

    @MapperCell(cellName = "客户名称(必填)")
    private String name1;

    @MapperCell(cellName = "客户名店铺名(必填)")
    private String name2;

    @MapperCell(cellName = "姓名(必填)")
    private String name3;

    @MapperCell(cellName = "手机")
    private String phone;

    @MapperCell(cellName = "手机(必填)")
    private String phone2;

    private String realName;

    private String realPhone;

    private Integer wrongId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getWrongId() {
        if (url != null && !"".equals(url)) {
            String id = url.substring(url.lastIndexOf("/") + 1, url.length());
            return Integer.valueOf(id);
        }
        return null;
    }

    public void setWrongId(Integer wrongId) {
        this.wrongId = wrongId;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getRealName() {
        if (name1 != null && !"".equals(name1)) {
            return name1;
        } else if (name2 != null && !"".equals(name2)) {
            return name2;
        } else if (name3 != null && !"".equals(name3)) {
            return name3;
        }
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealPhone() {
        if (phone != null && !"".equals(phone) && !"null".equals(phone)) {
            return phone;
        } else if (phone2 != null && !"".equals(phone2) && !"null".equals(phone2)) {
            return phone2;
        }
        return realPhone;
    }

    public void setRealPhone(String realPhone) {
        this.realPhone = realPhone;
    }
}
