package ch.bzz.applicationmanager.module;

import java.util.List;

public class Project {
    private String projectName;
    private String projectVersion;
    private String  projectAuthor;
    private List<Language> projectLanguages;

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

    public List<Language> getProjectLanguages() {
        return projectLanguages;
    }

    public void setProjectLanguages(List<Language> projectLanguages) {
        this.projectLanguages = projectLanguages;
    }
}
