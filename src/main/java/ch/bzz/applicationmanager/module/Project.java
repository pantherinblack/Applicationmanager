package ch.bzz.applicationmanager.module;

import ch.bzz.applicationmanager.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.ArrayList;
import java.util.List;

/**
 * model-class for projects
 *
 * @author Kevin Stupar
 * @version 1.2
 * @since 23.05.2022
 */
public class Project {
    @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")
    private String projectUuid;
    @FormParam("name")
    @Size(min = 1, max = 50)
    private String projectName;
    @FormParam("version")
    @Pattern(regexp = "[V]{1}[ ]{0,1}[0-9]{1,}([\\.-]{1}[0-9]{1,}){0,}")
    private String projectVersion;
    @FormParam("author")
    @Size(min = 1, max = 100)
    private String projectAuthor;
    private List<String> projectLanguages = new ArrayList<>();
    @JsonIgnore
    private List<Language> projectLanguagesRef = new ArrayList<>();

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
        List<Language> projectLanguageRefs = new ArrayList<>();
        for (String language : projectLanguages) {
            projectLanguageRefs.add(DataHandler.readLanguageByUuid(language));
        }
        setProjectLanguagesRef(projectLanguageRefs);
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

    public void addLanguage(String uuid) {
        projectLanguages.add(uuid);
        projectLanguagesRef.add(DataHandler.readLanguageByUuid(uuid));
    }

    public void clearLanguages() {
        projectLanguages = new ArrayList<>();
        projectLanguagesRef = new ArrayList<>();
    }
}
