package com.senzec.alfa.to_json;

public class AcademicMenuItem {
    String qualification;
    String college_name;
    int joining_year;
    int final_year;
    public AcademicMenuItem(String qualification, String college_name, int joining_year, int final_year){
        this.qualification = qualification;
        this.college_name = college_name;
        this.joining_year = joining_year;
        this.final_year = final_year;
    }
}
