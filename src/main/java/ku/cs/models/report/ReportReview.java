package ku.cs.models.report;

public class ReportReview extends Report{
    public enum Type {ANIMAL_ABUSE, HATE_SPEECH, VIOLENCE}
    private Type type;

    public ReportReview(String detail, Type type){
        this.type = type;
        setDetail(detail);
    }

    public Type getType() {
        return type;
    }
}
