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
        setProjectList(new ArrayList<>());
        readLanguageJSON();
        setProjectList(new ArrayList<>());
        readProjectJSON();
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
     * reads all books
     * @return list of books
     */
    public List<Language> readAllLanguages() {
        return getLanguageList();
    }

    /**
     * reads a book by its uuid
     * @param languageName
     * @return the Book (null=not found)
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
     * reads a book by its uuid
     * @param languageUuid
     * @return the Book (null=not found)
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
     * reads all Publishers
     * @return list of publishers
     */
    public List<Project> readAllProjects() {

        return getProjectList();
    }

    /**
     * reads a publisher by its uuid
     * @param projectName
     * @return the Publisher (null=not found)
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
     * reads a publisher by its uuid
     * @param projectUuid
     * @return the Publisher (null=not found)
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

    //TODO
    /**
     * reads all books
     * @return list of books
     */
    public List<Type> readAllTypes() {
        return getTypeList();
    }

    /**
     * reads a book by its uuid
     * @param typeName
     * @return the Book (null=not found)
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
     * reads a book by its uuid
     * @param typeUuid
     * @return the Book (null=not found)
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
     * reads the books from the JSON-file
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
     * reads the publishers from the JSON-file
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
     * reads the publishers from the JSON-file
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
     * gets bookList
     *
     * @return value of bookList
     */
    private List<Language> getLanguageList() {
        return languageList;
    }

    /**
     * sets bookList
     *
     * @param languageList the value to set
     */
    private void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }

    /**
     * gets publisherList
     *
     * @return value of publisherList
     */
    private List<Project> getProjectList() {
        return projectList;
    }

    /**
     * sets publisherList
     *
     * @param projectList the value to set
     */
    private void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    /**
     * gets publisherList
     *
     * @return value of publisherList
     */
    private List<Type> getTypeList() {
        return typeList;
    }

    /**
     * sets publisherList
     *
     * @param typeList the value to set
     */
    private void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }


}