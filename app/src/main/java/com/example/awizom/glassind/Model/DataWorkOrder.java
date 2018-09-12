package com.example.awizom.glassind.Model;
import com.google.firebase.database.IgnoreExtraProperties;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import java.util.Date;

public  class DataWorkOrder {
    private String Id;
    private String WorkingDate;
    private String PartyName;
    private String Location;
    private int PINo;
    private int workOrderNo;
    private double GlassSpecificationThick;
    private String GlassSpecificationColor;
    private String GlassSpecificationBTD;
    private String SizeIn;
    private String SizeMm;
    private String ActualSize;
    private String Hole;
    private String Cut;
    private int Qty;
    private double AreaInSQM;
    private String OrderDate;
    private double GWaight;
    private String Remark;
    private boolean DEPTCUT;
    private boolean DEPTGRIND;
    private boolean DEPTFAB;
    private boolean DEPTTEMP;
    private boolean DEPTDISP;
    private boolean DEPTDREJECT;
    private String Drawing;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public DataWorkOrder() {
    }

    public DataWorkOrder(String workingDate, String partyName, String location, int PINo, int workOrderNo, double glassSpecificationThick, String glassSpecificationColor, String glassSpecificationBTD, String sizeIn, String sizeMm, String actualSize, String hole, String cut, int qty, double areaInSQM, String orderDate, double GWaight, String remark, boolean DEPTCUT, boolean DEPTGRIND, boolean DEPTFAB, boolean DEPTTEMP, boolean DEPTDISP, boolean DEPTDREJECT, String drawing) {
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

    public DataWorkOrder(String workingDate, String partyName, String location, int PINo, int workOrderNo, double glassSpecificationThick, String glassSpecificationColor, String glassSpecificationBTD, String sizeIn, String sizeMm, String actualSize, String hole, String cut, int qty, double areaInSQM, String orderDate, double gWaight, String remark,String drawing) {
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

    public String getWorkingDate() {
        return WorkingDate;
    }

    public void setWorkingDate(String workingDate) {
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

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getAreaInSQM() {
        return AreaInSQM;
    }

    public void setAreaInSQM(double areaInSQM) {
        AreaInSQM = areaInSQM;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
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
