package ch.bzz.applicationmanager.module;

import ch.bzz.applicationmanager.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
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
    private String projectUuid;
    @FormParam("projectName")
    @Size(min = 1, max = 50)
    @NotBlank
    private String projectName;
    @FormParam("projectVersion")
    @Pattern(regexp = "[V][ ]?[0-9]+([.-][0-9]+)*")
    @NotBlank
    private String projectVersion;
    @FormParam("projectAuthor")
    @NotBlank
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
     * @param projectName to be set
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
     * @param projectVersion to be set
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
     * @param projectAuthor to be set
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
     * @param projectLanguages to be set
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
     * @param projectUuid to be set
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
     * @param projectLanguagesRef to be set
     */
    public void setProjectLanguagesRef(List<Language> projectLanguagesRef) {
        this.projectLanguagesRef = projectLanguagesRef;
    }

    /**
     * adds a language using a uuid
     *
     * @param uuid to be set
     */
    public void addLanguage(String uuid) {
        projectLanguages.add(uuid);
        projectLanguagesRef.add(DataHandler.readLanguageByUuid(uuid));
    }

    /**
     * clears all languages
     */
    public void clearLanguages() {
        projectLanguages = new ArrayList<>();
        projectLanguagesRef = new ArrayList<>();
    }
}
