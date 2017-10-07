package com.senzec.alfa.to_json;

import java.util.List;

public class AcademicWithMenu {

    String _id;
    List<AcademicMenuItem> academic_info;
    List<JobMenuItem> job_info;


    public AcademicWithMenu(String _id, List<AcademicMenuItem> academic_info, List<JobMenuItem> job_info){
        this._id = _id;
        this.academic_info = academic_info;
        this.job_info = job_info;
    }

}
