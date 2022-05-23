package ch.bzz.applicationmanager.data;

import ch.bzz.applicationmanager.module.Language;
import ch.bzz.applicationmanager.module.Project;
import ch.bzz.applicationmanager.module.Type;
import ch.bzz.applicationmanager.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 *
 * @author Kevin Stupar
 * @version 1.5
 * @since 23.05.2022
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Language> languageList;
    private List<Project> projectList;
    private List<Type> typeList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setLanguageList(new ArrayList<>());
        readLanguageJSON();
        setProjectList(new ArrayList<>());
        readProjectJSON();
        setTypeList(new ArrayList<>());
        readTypeJSON();
    }

    /**
     * gets the only instance of this class
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all languages
     * @return list of languages
     */
    public List<Language> readAllLanguages() {
        return new ArrayList<Language>(getLanguageList());
    }

    /**
     * reads a language by its name
     * @param languageName
     * @return the language (null=not found)
     */
    public Language readLanguageByName(String languageName) {
        Language language = null;
        for (Language entry : getLanguageList()) {
            if (entry.getLanguageName().equals(languageName)) {
                language = entry;
            }
        }
        return language;
    }

    /**
     * reads a language by its uuid
     * @param languageUuid
     * @return the language (null=not found)
     */
    public Language readLanguageByUuid(String languageUuid) {
        Language language = null;
        for (Language entry : getLanguageList()) {
            if (entry.getLanguageUuid().equals(languageUuid)) {
                language = entry;
            }
        }
        return language;
    }

    /**
     * reads all projects
     * @return list of projects
     */
    public List<Project> readAllProjects() {


        return new ArrayList<Project>(getProjectList());
    }

    /**
     * reads a project by its name
     * @param projectName
     * @return the project (null=not found)
     */
    public Project readProjectByName(String projectName) {
        Project project = null;
        for (Project entry : getProjectList()) {
            if (entry.getProjectName().equals(projectName)) {
                project = entry;
            }
        }
        return project;
    }

    /**
     * reads a project by its uuid
     * @param projectUuid
     * @return the project (null=not found)
     */
    public Project readProjectByUuid(String projectUuid) {
        Project project = null;
        for (Project entry : getProjectList()) {
            if (entry.getProjectUuid().equals(projectUuid)) {
                project = entry;
            }
        }
        return project;
    }

    /**
     * reads all types
     * @return list of types
     */
    public List<Type> readAllTypes() {
        return new ArrayList<Type>(getTypeList());
    }

    /**
     * reads a type by its name
     * @param typeName
     * @return the type (null=not found)
     */
    public Type readTypesByName(String typeName) {
        Type type = null;
        for (Type entry : getTypeList()) {
            if (entry.getTypeName().equals(typeName)) {
                type = entry;
            }
        }
        return type;
    }

    /**
     * reads a type by its uuid
     * @param typeUuid
     * @return the type (null=not found)
     */
    public Type readTypesByUuid(String typeUuid) {
        Type type = null;
        for (Type entry : getTypeList()) {
            if (entry.getTypeUuid().equals(typeUuid)) {
                type = entry;
            }
        }
        return type;
    }

    /**
     * reads the languages from the JSON-file
     */
    private void readLanguageJSON() {
        try {
            String path = Config.getProperty("languageJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Language[] languages = objectMapper.readValue(jsonData, Language[].class);
            for (Language language : languages) {
                getLanguageList().add(language);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the projects from the JSON-file
     */
    private void readProjectJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("projectJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Project[] projects = objectMapper.readValue(jsonData, Project[].class);
            for (Project project : projects) {
                getProjectList().add(project);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the types from the JSON-file
     */
    private void readTypeJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("typeJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Type[] types = objectMapper.readValue(jsonData, Type[].class);
            for (Type type : types) {
                getTypeList().add(type);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets languageList
     *
     * @return value of languageList
     */
    private List<Language> getLanguageList() {
        return languageList;
    }

    /**
     * sets languageList
     *
     * @param languageList the value to set
     */
    private void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }

    /**
     * gets projectList
     *
     * @return value of projectList
     */
    private List<Project> getProjectList() {
        return projectList;
    }

    /**
     * sets projectList
     *
     * @param projectList the value to set
     */
    private void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    /**
     * gets typeList
     *
     * @return value of typeList
     */
    private List<Type> getTypeList() {
        return typeList;
    }

    /**
     * sets typeList
     *
     * @param typeList the value to set
     */
    private void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }
}