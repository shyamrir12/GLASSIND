package com.example.awizom.glassind.Model;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.Date;

public class DataWorkOrder {
    public Date WorkingDate;
    public String PartyName;
    public String Location;
    public int PINo;
    public int workOrderNo;
    public double GlassSpecificationThick;
    public String GlassSpecificationColor;
    public String GlassSpecificationBTD;
    public String SizeIn;
    public String SizeMm;
    public String ActualSize;
    public String Hole;
    public String Cut;
    public String Qty;
    public double AreaInSQM;
    public Date OrderDate;
    public double GWaight;
    public String Remark;
    public boolean DEPTCUT;
    public boolean DEPTGRIND;
    public boolean DEPTFAB;
    public boolean DEPTTEMP;
    public boolean DEPTDISP;
    public boolean DEPTDREJECT;
    public String Drawing;

    public DataWorkOrder(Date workingDate, String partyName, String location, int PINo, int workOrderNo, double glassSpecificationThick, String glassSpecificationColor, String glassSpecificationBTD, String sizeIn, String sizeMm, String actualSize, String hole, String cut, String qty, double areaInSQM, Date orderDate, double GWaight, String remark, boolean DEPTCUT, boolean DEPTGRIND, boolean DEPTFAB, boolean DEPTTEMP, boolean DEPTDISP, boolean DEPTDREJECT, String drawing) {
        WorkingDate = workingDate;
        PartyName = partyName;
        Location = location;
        this.PINo = PINo;
        this.workOrderNo = workOrderNo;
        GlassSpecificationThick = glassSpecificationThick;
        GlassSpecificationColor = glassSpecificationColor;
        GlassSpecificationBTD = glassSpecificationBTD;
        SizeIn = sizeIn;
        SizeMm = sizeMm;
        ActualSize = actualSize;
        Hole = hole;
        Cut = cut;
        Qty = qty;
        AreaInSQM = areaInSQM;
        OrderDate = orderDate;
        this.GWaight = GWaight;
        Remark = remark;
        this.DEPTCUT = DEPTCUT;
        this.DEPTGRIND = DEPTGRIND;
        this.DEPTFAB = DEPTFAB;
        this.DEPTTEMP = DEPTTEMP;
        this.DEPTDISP = DEPTDISP;
        this.DEPTDREJECT = DEPTDREJECT;
        Drawing = drawing;
    }

    public Date getWorkingDate() {
        return WorkingDate;
    }

    public void setWorkingDate(Date workingDate) {
        WorkingDate = workingDate;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getPINo() {
        return PINo;
    }

    public void setPINo(int PINo) {
        this.PINo = PINo;
    }

    public int getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(int workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public double getGlassSpecificationThick() {
        return GlassSpecificationThick;
    }

    public void setGlassSpecificationThick(double glassSpecificationThick) {
        GlassSpecificationThick = glassSpecificationThick;
    }

    public String getGlassSpecificationColor() {
        return GlassSpecificationColor;
    }

    public void setGlassSpecificationColor(String glassSpecificationColor) {
        GlassSpecificationColor = glassSpecificationColor;
    }

    public String getGlassSpecificationBTD() {
        return GlassSpecificationBTD;
    }

    public void setGlassSpecificationBTD(String glassSpecificationBTD) {
        GlassSpecificationBTD = glassSpecificationBTD;
    }

    public String getSizeIn() {
        return SizeIn;
    }

    public void setSizeIn(String sizeIn) {
        SizeIn = sizeIn;
    }

    public String getSizeMm() {
        return SizeMm;
    }

    public void setSizeMm(String sizeMm) {
        SizeMm = sizeMm;
    }

    public String getActualSize() {
        return ActualSize;
    }

    public void setActualSize(String actualSize) {
        ActualSize = actualSize;
    }

    public String getHole() {
        return Hole;
    }

    public void setHole(String hole) {
        Hole = hole;
    }

    public String getCut() {
        return Cut;
    }

    public void setCut(String cut) {
        Cut = cut;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public double getAreaInSQM() {
        return AreaInSQM;
    }

    public void setAreaInSQM(double areaInSQM) {
        AreaInSQM = areaInSQM;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public double getGWaight() {
        return GWaight;
    }

    public void setGWaight(double GWaight) {
        this.GWaight = GWaight;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public boolean isDEPTCUT() {
        return DEPTCUT;
    }

    public void setDEPTCUT(boolean DEPTCUT) {
        this.DEPTCUT = DEPTCUT;
    }

    public boolean isDEPTGRIND() {
        return DEPTGRIND;
    }

    public void setDEPTGRIND(boolean DEPTGRIND) {
        this.DEPTGRIND = DEPTGRIND;
    }

    public boolean isDEPTFAB() {
        return DEPTFAB;
    }

    public void setDEPTFAB(boolean DEPTFAB) {
        this.DEPTFAB = DEPTFAB;
    }

    public boolean isDEPTTEMP() {
        return DEPTTEMP;
    }

    public void setDEPTTEMP(boolean DEPTTEMP) {
        this.DEPTTEMP = DEPTTEMP;
    }

    public boolean isDEPTDISP() {
        return DEPTDISP;
    }

    public void setDEPTDISP(boolean DEPTDISP) {
        this.DEPTDISP = DEPTDISP;
    }

    public boolean isDEPTDREJECT() {
        return DEPTDREJECT;
    }

    public void setDEPTDREJECT(boolean DEPTDREJECT) {
        this.DEPTDREJECT = DEPTDREJECT;
    }

    public String getDrawing() {
        return Drawing;
    }

    public void setDrawing(String drawing) {
        Drawing = drawing;
    }
}
