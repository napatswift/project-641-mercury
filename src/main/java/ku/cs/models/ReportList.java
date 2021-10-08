package ku.cs.models;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class ReportList {
    private final Set<Report> reports;

    public ReportList() {
       reports = new TreeSet<>();
    }

    public boolean addReport(Report report) {
        return reports.add(report);
    }

    public void removeReport(Report report){
        reports.removeIf(report1 -> report1 == report);
    }

    public ArrayList<Report> toList() {
        return new ArrayList<>(this.reports);
    }

    public String toCsv(){
        StringBuilder stringBuilder = new StringBuilder("id,type_report,suspected_person,report_time,id_review,id_product,detail");
        stringBuilder.append("\n");
        for(Report report : reports){
            stringBuilder.append(report.toCSV());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
