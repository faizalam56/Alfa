package com.senzec.alfa.to_json.academic;

import java.util.List;

public class AcademicWithMenu {

    String _id;
    List<AcademicMenuItem> academic_info;
    List<JobMenuItem> job_info;
    String current_company_name;

    public AcademicWithMenu(String _id, String current_company_name, List<AcademicMenuItem> academic_info, List<JobMenuItem> job_info){
        this._id = _id;
        this.current_company_name = current_company_name;
        this.academic_info = academic_info;
        this.job_info = job_info;
    }

}
