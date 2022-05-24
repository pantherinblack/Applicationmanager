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

    /**
     * gets the type name.
     *
     * @return name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * sets the type name.
     *
     * @param typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * gets the type description.
     *
     * @return description
     */
    public String getTypeDescription() {
        return typeDescription;
    }

    /**
     * sets the type description.
     *
     * @param typeDescription
     */
    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    /**
     * gets the type uuid.
     *
     * @return uuid
     */
    public String getTypeUuid() {
        return typeUuid;
    }

    /**
     * sets the type uuid.
     *
     * @param typeUuid
     */
    public void setTypeUuid(String typeUuid) {
        this.typeUuid = typeUuid;
    }
}
