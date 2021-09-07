package ku.cs.models;

import java.util.ArrayList;
import java.util.Collection;

public class ReportList {
    private ArrayList<Report> reportArrayList;
    private Report currReport;

    public ReportList() {
       reportArrayList = new ArrayList<>();
    }

    public void addReport(Report report) {
        reportArrayList.add(report);
    }

    public void removeReport(Report report){
        int i = 0;
        for(Report reportTemp : reportArrayList){
            if(reportTemp.checkReport(report)){
                reportArrayList.remove(i);
                return;
            }
            ++i;
        }
    }

    public Report getCurrReport() {
        return currReport;
    }

    public void setCurrReport(Report currReport) {
        this.currReport = currReport;
    }

    public Collection<Report> toList() {
        return reportArrayList;
    }

    public String toCsv(){
        StringBuilder stringBuilder = new StringBuilder("type_report,suspected_person,reporter,report_time,id_review,id_product,detail");
        stringBuilder.append("\n");
        for(Report report : reportArrayList){
            stringBuilder.append(report.toCSV());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
