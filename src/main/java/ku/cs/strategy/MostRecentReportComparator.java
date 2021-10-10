package ku.cs.strategy;

import ku.cs.models.Report;

import java.util.Comparator;

public class MostRecentReportComparator implements Comparator<Report> {
    @Override
    public int compare(Report o1, Report o2) {
        return o1.getReportDateTime().compareTo(o2.getReportDateTime()) * -1;
    }
}
