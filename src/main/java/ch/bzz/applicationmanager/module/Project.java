package ch.bzz.applicationmanager.module;

import java.util.List;

public class Project {
    private String projectUuid;
    private String projectName;
    private String projectVersion;
    private String projectAuthor;
    private List<String > projectLanguages;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getProjectAuthor() {
        return projectAuthor;
    }

    public void setProjectAuthor(String projectAuthor) {
        this.projectAuthor = projectAuthor;
    }

    public List<String > getProjectLanguages() {
        return projectLanguages;
    }

    public void setProjectLanguages(List<String> projectLanguages) {
        this.projectLanguages = projectLanguages;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }
}
