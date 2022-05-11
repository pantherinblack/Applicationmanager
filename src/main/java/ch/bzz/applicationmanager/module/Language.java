package ch.bzz.applicationmanager.module;

import java.time.LocalDate;

public class Language {
    private String languageName;
    private String languageShort;
    private LocalDate languageReleaseDate;
    private Type languageType;

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

    public Type getLanguageType() {
        return languageType;
    }

    public void setLanguageType(Type languageType) {
        this.languageType = languageType;
    }
}
