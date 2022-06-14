package ch.bzz.applicationmanager.module;

import ch.bzz.applicationmanager.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import java.time.LocalDate;

/**
 * model-class for languages
 *
 * @author Kevin Stupar
 * @version 1.2
 * @since 23.05.2022
 */
public class Language {
    private String languageName;
    private String languageShort;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FormParam("languageRelDate")
    @NotNull
    private LocalDate languageReleaseDate;
    private String languageType;
    @JsonIgnore
    private Type languageTypeRef;
    private String languageUuid;

    /**
     * get the language name
     *
     * @return name
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     * sets te language name
     *
     * @param languageName to be set
     */
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    /**
     * get the language short
     *
     * @return short
     */
    public String getLanguageShort() {
        return languageShort;
    }

    /**
     * sets the language short
     *
     * @param languageShort to be set
     */
    public void setLanguageShort(String languageShort) {
        this.languageShort = languageShort;
    }

    /**
     * gets the release date of the language
     *
     * @return release date
     */
    public LocalDate getLanguageReleaseDate() {
        return languageReleaseDate;
    }

    /**
     * sets the release date of the language
     *
     * @param languageReleaseDate to be set
     */
    public void setLanguageReleaseDate(LocalDate languageReleaseDate) {
        this.languageReleaseDate = languageReleaseDate;
    }

    /**
     * gets the type of the language using a uuid reference
     *
     * @return type
     */
    public String getLanguageType() {
        return languageType;
    }

    /**
     * sets the type of the language using a uuid reference
     *
     * @param languageType to be set
     */
    public void setLanguageType(String languageType) {
        setLanguageTypeRef(DataHandler.readTypesByUuid(languageType));
        this.languageType = languageType;
    }

    /**
     * gets the language uuid
     *
     * @return uuid
     */
    public String getLanguageUuid() {
        return languageUuid;
    }

    /**
     * sets the language uuid
     *
     * @param languageUuid to be set
     */
    public void setLanguageUuid(String languageUuid) {
        this.languageUuid = languageUuid;
    }

    /**
     * gets the type of the language using a object reference
     *
     * @return type
     */
    public Type getLanguageTypeRef() {
        return languageTypeRef;
    }

    /**
     * sets the type of the language using a object reference
     *
     * @param languageTypeRef to be set
     */
    public void setLanguageTypeRef(Type languageTypeRef) {
        this.languageTypeRef = languageTypeRef;
    }
}
