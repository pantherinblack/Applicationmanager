package ch.bzz.applicationmanager.module;

import ch.bzz.applicationmanager.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @FormParam("uuid")
    @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")
    private String languageUuid;
    @FormParam("name")
    @Size(min = 2, max = 50)
    private String languageName;
    @FormParam("short")
    @Size(min = 1, max = 10)
    private String languageShort;
    @FormParam("relDate")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate languageReleaseDate;
    private String languageType;
    @JsonIgnore
    private Type languageTypeRef;

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
     * @param languageName
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
     * sets the langauge short
     *
     * @param languageShort
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
     * @param languageReleaseDate
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
     * @param languageType
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
     * @param languageUuid
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
     * @param languageTypeRef
     */
    public void setLanguageTypeRef(Type languageTypeRef) {
        this.languageTypeRef = languageTypeRef;
    }
}
