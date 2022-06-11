package ch.bzz.applicationmanager.module;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * model-class for type
 *
 * @author Kevin Stupar
 * @version 1.2
 * @since 23.05.2022
 */
public class Type {
    private String typeUuid;
    @NotBlank
    @FormParam("typeName")
    @Size(min = 2, max = 50)
    private String typeName;
    @NotBlank
    @FormParam("typeDesc")
    @Size(max = 1000)
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
     * @param typeName to be set
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
     * @param typeDescription to be set
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
     * @param typeUuid to be set
     */
    public void setTypeUuid(String typeUuid) {
        this.typeUuid = typeUuid;
    }
}
