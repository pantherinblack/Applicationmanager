package ch.bzz.applicationmanager.module;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * model-class for projects
 *
 * @author Kevin Stupar
 * @version 1.2
 * @since 23.05.2022
 */
public class Project {
    private String projectUuid;
    private String projectName;
    private String projectVersion;
    private String projectAuthor;
    private List<String> projectLanguages;
    @JsonIgnore
    private List<Language> projectLanguagesRef;

    /**
     * gets the project name.
     *
     * @return name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * sets the project name
     *
     * @param projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * gets the project version.
     *
     * @return version
     */
    public String getProjectVersion() {
        return projectVersion;
    }

    /**
     * sets the project version.
     *
     * @param projectVersion
     */
    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    /**
     * gets the name of the project author.
     *
     * @return author name
     */
    public String getProjectAuthor() {
        return projectAuthor;
    }

    /**
     * sets the name of the project author.
     *
     * @param projectAuthor
     */
    public void setProjectAuthor(String projectAuthor) {
        this.projectAuthor = projectAuthor;
    }

    /**
     * gets project languages using a uuid.
     *
     * @return languages
     */
    public List<String> getProjectLanguages() {
        return projectLanguages;
    }

    /**
     * sets the project languages using a uuid.
     *
     * @param projectLanguages
     */
    public void setProjectLanguages(List<String> projectLanguages) {
        this.projectLanguages = projectLanguages;
    }

    /**
     * gets the project uuid.
     *
     * @return uuid
     */
    public String getProjectUuid() {
        return projectUuid;
    }

    /**
     * sets the project uuid.
     *
     * @param projectUuid
     */
    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    /**
     * gets the project languages using a object reference.
     *
     * @return languages
     */
    public List<Language> getProjectLanguagesRef() {
        return projectLanguagesRef;
    }

    /**
     * sets the project languages using a object reference.
     *
     * @param projectLanguagesRef
     */
    public void setProjectLanguagesRef(List<Language> projectLanguagesRef) {
        this.projectLanguagesRef = projectLanguagesRef;
    }
}
