package com.example.administrator.prenewproject.bean;

import java.util.List;

public class DataBean1 {


    /**
     * Success : true
     * ErrorCode : 200
     * ErrorMsg : 成功
     * data : [{"ID":"c0049469-1f96-42b6-b640-3b2b5e502087","EmpName":"6002399 赵磊","StartTime":"/Date(1536924499000)/","EndTime":"/Date(1536944280000)/","ProblemNumber":"0","DepartmentName":"嘉西车间","LineName":"柳二","GroupName":"赵磊","DriverUserName":"王建东","LearnUserName":"张鹏","LocomotiveModel":"HXD1C-0691","Trips":"Y66","StartSectionName":"柳园车站","EndSectionName":"嘉整备场","LaborHours":"5.49","NightHours":"5.49","MidNightHours":"0","AddGuideProblem":[]},{"ID":"79b42106-b006-4060-bc26-a4ccdbad2747","EmpName":"6002399 赵磊","StartTime":"/Date(1540386148000)/","EndTime":"/Date(1540415589000)/","ProblemNumber":"1","DepartmentName":"嘉西车间","LineName":"柳二","GroupName":"赵磊","DriverUserName":"王建东","LearnUserName":"张鹏","LocomotiveModel":"HXD1C-0317","Trips":"86021","StartSectionName":"嘉站三场","EndSectionName":"柳园车站","LaborHours":"8.18","NightHours":"8.18","MidNightHours":"4.22","AddGuideProblem":[{"ProblemDutyUserName":"6000800 白彦红","WorkDescription":"2018年10月25日05时05分,2018年10月25日，司机白彦红柳园出勤过线路未拿手电。,\u201c作业过程中未按规定穿戴劳动防护用品\u201d,核减积分-20分经济考核0元,C类","Points":"-20","ProblemAddress":"嘉柳区段","OccurTime":"/Date(1540415100000)/"}]},{"ID":"05fe8a77-63d3-400c-8463-b9215d126949","EmpName":"6001276 张宗龙","StartTime":"/Date(1541560245000)/","EndTime":"/Date(1541580459000)/","ProblemNumber":"0","DepartmentName":"嘉西车间","LineName":"柳二","GroupName":"","DriverUserName":"张鹏","LearnUserName":"柴高力","LocomotiveModel":"HXD1C-0685","Trips":"11047","StartSectionName":"嘉站三场","EndSectionName":"疏勒河站","LaborHours":"5.62","NightHours":"0","MidNightHours":"0","AddGuideProblem":[]},{"ID":"75276d8d-7948-4d67-9404-2a28bf9bb625","EmpName":"6001276 张宗龙","StartTime":"/Date(1541580476000)/","EndTime":"/Date(1541591589000)/","ProblemNumber":"0","DepartmentName":"嘉西车间","LineName":"柳二","GroupName":"","DriverUserName":"张鹏","LearnUserName":"柴高力","LocomotiveModel":"HXD1C-6322","Trips":"10130","StartSectionName":"疏勒河站","EndSectionName":"嘉站一场","LaborHours":"3.09","NightHours":"0.89","MidNightHours":"0","AddGuideProblem":[]}]
     */

    private boolean Success;
    private String ErrorCode;
    private String ErrorMsg;
    private List<DataBean> data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : c0049469-1f96-42b6-b640-3b2b5e502087
         * EmpName : 6002399 赵磊
         * StartTime : /Date(1536924499000)/
         * EndTime : /Date(1536944280000)/
         * ProblemNumber : 0
         * DepartmentName : 嘉西车间
         * LineName : 柳二
         * GroupName : 赵磊
         * DriverUserName : 王建东
         * LearnUserName : 张鹏
         * LocomotiveModel : HXD1C-0691
         * Trips : Y66
         * StartSectionName : 柳园车站
         * EndSectionName : 嘉整备场
         * LaborHours : 5.49
         * NightHours : 5.49
         * MidNightHours : 0
         * AddGuideProblem : []
         */

        private String ID;
        private String EmpName;
        private String StartTime;
        private String EndTime;
        private String ProblemNumber;
        private String DepartmentName;
        private String LineName;
        private String GroupName;
        private String DriverUserName;
        private String LearnUserName;
        private String LocomotiveModel;
        private String Trips;
        private String StartSectionName;
        private String EndSectionName;
        private String LaborHours;
        private String NightHours;
        private String MidNightHours;
        private List<?> AddGuideProblem;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getEmpName() {
            return EmpName;
        }

        public void setEmpName(String EmpName) {
            this.EmpName = EmpName;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public String getProblemNumber() {
            return ProblemNumber;
        }

        public void setProblemNumber(String ProblemNumber) {
            this.ProblemNumber = ProblemNumber;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public String getLineName() {
            return LineName;
        }

        public void setLineName(String LineName) {
            this.LineName = LineName;
        }

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String GroupName) {
            this.GroupName = GroupName;
        }

        public String getDriverUserName() {
            return DriverUserName;
        }

        public void setDriverUserName(String DriverUserName) {
            this.DriverUserName = DriverUserName;
        }

        public String getLearnUserName() {
            return LearnUserName;
        }

        public void setLearnUserName(String LearnUserName) {
            this.LearnUserName = LearnUserName;
        }

        public String getLocomotiveModel() {
            return LocomotiveModel;
        }

        public void setLocomotiveModel(String LocomotiveModel) {
            this.LocomotiveModel = LocomotiveModel;
        }

        public String getTrips() {
            return Trips;
        }

        public void setTrips(String Trips) {
            this.Trips = Trips;
        }

        public String getStartSectionName() {
            return StartSectionName;
        }

        public void setStartSectionName(String StartSectionName) {
            this.StartSectionName = StartSectionName;
        }

        public String getEndSectionName() {
            return EndSectionName;
        }

        public void setEndSectionName(String EndSectionName) {
            this.EndSectionName = EndSectionName;
        }

        public String getLaborHours() {
            return LaborHours;
        }

        public void setLaborHours(String LaborHours) {
            this.LaborHours = LaborHours;
        }

        public String getNightHours() {
            return NightHours;
        }

        public void setNightHours(String NightHours) {
            this.NightHours = NightHours;
        }

        public String getMidNightHours() {
            return MidNightHours;
        }

        public void setMidNightHours(String MidNightHours) {
            this.MidNightHours = MidNightHours;
        }

        public List<?> getAddGuideProblem() {
            return AddGuideProblem;
        }

        public void setAddGuideProblem(List<?> AddGuideProblem) {
            this.AddGuideProblem = AddGuideProblem;
        }
    }
}
