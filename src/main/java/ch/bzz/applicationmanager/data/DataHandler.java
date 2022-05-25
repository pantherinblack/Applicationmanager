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
    private static List<Language> languageList;
    private static List<Project> projectList;
    private static List<Type> typeList;

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
     * reads all languages
     *
     * @return list of languages
     */
    public static List<Language> readAllLanguages() {
        return new ArrayList<Language>(getLanguageList());
    }

    /**
     * reads a language by its name
     *
     * @param languageName
     * @return the language (null=not found)
     */
    public static Language readLanguageByName(String languageName) {
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
     *
     * @param languageUuid
     * @return the language (null=not found)
     */
    public static Language readLanguageByUuid(String languageUuid) {
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
     *
     * @return list of projects
     */
    public static List<Project> readAllProjects() {
        return new ArrayList<Project>(getProjectList());
    }

    /**
     * reads a project by its name
     *
     * @param projectName
     * @return the project (null=not found)
     */
    public static Project readProjectByName(String projectName) {
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
     *
     * @param projectUuid
     * @return the project (null=not found)
     */
    public static Project readProjectByUuid(String projectUuid) {
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
     *
     * @return list of types
     */
    public static List<Type> readAllTypes() {
        return new ArrayList<Type>(getTypeList());
    }

    /**
     * reads a type by its name
     *
     * @param typeName
     * @return the type (null=not found)
     */
    public static Type readTypesByName(String typeName) {
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
     *
     * @param typeUuid
     * @return the type (null=not found)
     */
    public static Type readTypesByUuid(String typeUuid) {
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
    private static void readLanguageJSON() {
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
    private static void readProjectJSON() {
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
    private static void readTypeJSON() {
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
    private static List<Language> getLanguageList() {
        return languageList;
    }

    /**
     * sets languageList
     *
     * @param languageList the value to set
     */
    private static void setLanguageList(List<Language> languageList) {
        DataHandler.languageList = languageList;
    }

    /**
     * gets projectList
     *
     * @return value of projectList
     */
    private static List<Project> getProjectList() {
        return projectList;
    }

    /**
     * sets projectList
     *
     * @param projectList the value to set
     */
    private static void setProjectList(List<Project> projectList) {
        DataHandler.projectList = projectList;
    }

    /**
     * gets typeList
     *
     * @return value of typeList
     */
    private static List<Type> getTypeList() {
        return typeList;
    }

    /**
     * sets typeList
     *
     * @param typeList the value to set
     */
    private static void setTypeList(List<Type> typeList) {
        DataHandler.typeList = typeList;
    }
}