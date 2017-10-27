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

    @Override
    public String toString() {
        return "{" +
                "orgId=" + orgId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", wrongId=" + getWrongId() +
                '}';
    }
}
