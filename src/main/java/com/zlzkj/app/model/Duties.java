package com.zlzkj.app.model;

public class Duties {
    private Integer id;

    private String dutiesName;

    private Integer addTime;

    private Integer sortId;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDutiesName() {
        return dutiesName;
    }

    public void setDutiesName(String dutiesName) {
        this.dutiesName = dutiesName == null ? null : dutiesName.trim();
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}