package com.zlzkj.app.model;

public class Cadre {
    private Integer id;

    private String cadreName;

    private Integer cadreSex;

    private Integer areaId;

    private Integer departmentId;

    private Integer dutiesId;

    private String officeTel;

    private String moblePhone;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCadreName() {
        return cadreName;
    }

    public void setCadreName(String cadreName) {
        this.cadreName = cadreName == null ? null : cadreName.trim();
    }

    public Integer getCadreSex() {
        return cadreSex;
    }

    public void setCadreSex(Integer cadreSex) {
        this.cadreSex = cadreSex;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDutiesId() {
        return dutiesId;
    }

    public void setDutiesId(Integer dutiesId) {
        this.dutiesId = dutiesId;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel == null ? null : officeTel.trim();
    }

    public String getMoblePhone() {
        return moblePhone;
    }

    public void setMoblePhone(String moblePhone) {
        this.moblePhone = moblePhone == null ? null : moblePhone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}