package com.senzec.alfa.to_json.academic;

public class JobMenuItem {
    String job_designation;
    int joining_year;
    String company_name;
    int final_year;
    public JobMenuItem(String job_designation, int joining_year, String company_name, int final_year){
        this.job_designation = job_designation;
        this.joining_year = joining_year;
        this.company_name = company_name;
        this.final_year = final_year;
    }
}
