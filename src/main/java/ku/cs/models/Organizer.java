package ku.cs.models;

public class Organizer {
    private final String name;
    private final String nickname;
    private final String studentId;
    private final String githubId;
    private final String imagePath;

    public Organizer(String name, String nickname, String studentId, String githubId, String imagePath) {
        this.name = name;
        this.nickname = nickname;
        this.studentId = studentId;
        this.githubId = githubId;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getGithubId() {
        return githubId;
    }

    public String getImagePath() {
        return imagePath;
    }
}
