package ku.cs.models;

import javafx.scene.image.Image;

public class Organizer {
    private String name;
    private String nickname;
    private String studentId;
    private String githubId;
    private String imagePath;

    public Organizer(String name, String nickname, String studentId, String githubId) {
        this.name = name;
        this.nickname = nickname;
        this.studentId = studentId;
        this.githubId = githubId;
    }

    public Organizer() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
