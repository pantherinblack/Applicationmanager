package ch.bzz.applicationmanager.data;

import ch.bzz.applicationmanager.module.Language;
import ch.bzz.applicationmanager.module.Project;
import ch.bzz.applicationmanager.module.Type;
import ch.bzz.applicationmanager.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
    private static List<Language> languageList = null;
    private static List<Project> projectList = null;
    private static List<Type> typeList = null;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
    }

    /**
     * reads all languages
     *
     * @return list of languages
     */
    public static List<Language> readAllLanguages() {
        return new ArrayList<>(getLanguageList());
    }

    /**
     * reads a language by its name
     *
     * @param languageName to search for
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
     * @param languageUuid to search for
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
        return new ArrayList<>(getProjectList());
    }

    /**
     * reads a project by its name
     *
     * @param projectName to search for
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
     * @param projectUuid to search for
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
        return new ArrayList<>(getTypeList());
    }

    /**
     * reads a type by its name
     *
     * @param typeName to search for.
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
     * @param typeUuid to search for.
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
     * inserts a new language into the languageList
     *
     * @param language the publisher to be saved
     */
    public static void insertLanguage(Language language) {
        getLanguageList().add(language);
        writeLanguageJSON();
    }

    /**
     * updates the languageList
     */
    public static void updateLanguage() {
        writeLanguageJSON();
    }

    /**
     * deletes a language identified by the languageUuid
     *
     * @param languageUuid the key
     * @return success=true/false
     */
    public static boolean deleteLanguage(String languageUuid) {
        Language language = readLanguageByUuid(languageUuid);
        if (language != null) {
            getLanguageList().remove(language);
            writeLanguageJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * inserts a new project into the projectList
     *
     * @param project the project to be saved
     */
    public static void insertProject(Project project) {
        getProjectList().add(project);
        writeProjectJSON();
    }

    /**
     * updates the projectList
     */
    public static void updateProject() {
        writeProjectJSON();
    }

    /**
     * deletes a project identified by the projectUuid
     *
     * @param projectUuid the key
     * @return success=true/false
     */
    public static boolean deleteProject(String projectUuid) {
        Project project = readProjectByUuid(projectUuid);
        if (project != null) {
            getProjectList().remove(project);
            writeProjectJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * inserts a new type into the typeList
     *
     * @param type the project to be saved
     */
    public static void insertType(Type type) {
        getTypeList().add(type);
        writeTypeJSON();
    }

    /**
     * updates the projectList
     */
    public static void updateType() {
        writeTypeJSON();
    }

    /**
     * deletes a type identified by the typeUuid
     *
     * @param typeUuid the key
     * @return success=true/false
     */
    public static boolean deleteType(String typeUuid) {
        Type type = readTypesByUuid(typeUuid);
        if (type != null) {
            getTypeList().remove(type);
            writeProjectJSON();
            return true;
        } else {
            return false;
        }
    }


    /**
     * writes the languageList to the JSON-file
     */
    private static void writeLanguageJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream;
        Writer fileWriter;

        String bookPath = Config.getProperty("languageJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getLanguageList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the projectList to the JSON-file
     */
    private static void writeProjectJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream;
        Writer fileWriter;

        String bookPath = Config.getProperty("languageJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getProjectList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the typeList to the JSON-file
     */
    private static void writeTypeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream;
        Writer fileWriter;

        String bookPath = Config.getProperty("typeJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getTypeList());
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
        if (languageList == null) {
            initLists();
        }
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
        if (projectList == null) {
            initLists();
        }
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
        if (typeList == null) {
            initLists();
        }
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

    /**
     * clears all lists and initializes them in the correct order for reference integrity.
     */
    public static void initLists() {
        DataHandler.setTypeList(new ArrayList<>());
        DataHandler.setProjectList(new ArrayList<>());
        DataHandler.setLanguageList(new ArrayList<>());

        DataHandler.readTypeJSON();
        DataHandler.readLanguageJSON();
        DataHandler.readProjectJSON();
    }

    public static boolean isExistingLanguage(Language language) {
        for (Language language1 : languageList) {
            if (language1.getLanguageName().equals(language.getLanguageName()) &&
                    language1.getLanguageShort().equals(language.getLanguageShort()) &&
                    language1.getLanguageReleaseDate().equals(language.getLanguageReleaseDate()) &&
                    language1.getLanguageType().equals(language.getLanguageType())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExistingProject(Project project) {
        for (Project project1 : projectList) {
            if (project1.getProjectName().equals(project.getProjectName()) &&
                    project1.getProjectAuthor().equals(project.getProjectAuthor()) &&
                    project1.getProjectVersion().equals(project.getProjectVersion()) &&
                    project1.getProjectLanguages().containsAll(project.getProjectLanguages())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExistingType(Type type) {
        for (Type type1 : typeList) {
            if (type1.getTypeName().equals(type.getTypeName()) &&
                    type1.getTypeDescription().equals(type.getTypeDescription())) {
                return true;
            }
        }
        return false;
    }
}