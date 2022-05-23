package ch.bzz.applicationmanager.module;

/**
 * model-class for type
 *
 * @author Kevin Stupar
 * @version 1.2
 * @since 23.05.2022
 */
public class Type {
    private String typeUuid;
    private String typeName;
    private String typeDescription;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getTypeUuid() {
        return typeUuid;
    }

    public void setTypeUuid(String typeUuid) {
        this.typeUuid = typeUuid;
    }
}
