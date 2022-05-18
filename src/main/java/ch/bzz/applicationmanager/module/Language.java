package ch.bzz.applicationmanager.module;

import java.util.Date;

public class Language {
    private String languageUuid;
    private String languageName;
    private String languageShort;
    private Date languageReleaseDate;
    private String languageType;

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

    public Date getLanguageReleaseDate() {
        return languageReleaseDate;
    }

    public void setLanguageReleaseDate(Date languageReleaseDate) {
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
}
