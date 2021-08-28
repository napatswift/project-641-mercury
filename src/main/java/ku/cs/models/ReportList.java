package ku.cs.models;

import java.util.ArrayList;
import java.util.Collection;

public class ReportList {
    private ArrayList<Report> reportArrayList;
    private Report report;

    public ReportList() {
       reportArrayList = new ArrayList<>();
    }

    public void addReport(Report report) {
        reportArrayList.add(report);
    }

    public Collection<Report> toList() {
        return reportArrayList;
    }
}
