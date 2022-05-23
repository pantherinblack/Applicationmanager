package ch.bzz.applicationmanager.module;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

/**
 * model-class for languages
 *
 * @author Kevin Stupar
 * @version 1.2
 * @since 23.05.2022
 */
public class Language {
    private String languageUuid;
    private String languageName;
    private String languageShort;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate languageReleaseDate;
    private String languageType;
    @JsonIgnore
    private Type languageTypeRef;

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageShort() {
        return languageShort;
    }

    public void setLanguageShort(String languageShort) {
        this.languageShort = languageShort;
    }

    public LocalDate getLanguageReleaseDate() {
        return languageReleaseDate;
    }

    public void setLanguageReleaseDate(LocalDate languageReleaseDate) {
        this.languageReleaseDate = languageReleaseDate;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public String getLanguageUuid() {
        return languageUuid;
    }

    public void setLanguageUuid(String languageUuid) {
        this.languageUuid = languageUuid;
    }

    public Type getLanguageTypeRef() {
        return languageTypeRef;
    }

    public void setLanguageTypeRef(Type languageTypeRef) {
        this.languageTypeRef = languageTypeRef;
    }
}
