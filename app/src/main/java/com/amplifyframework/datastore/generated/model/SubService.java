package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the SubService type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "SubServices")
@Index(name = "byService", fields = {"serviceID"})
public final class SubService implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField SUB_SERVICE_TITLE = field("subServiceTitle");
  public static final QueryField SUB_SERVICE_PRICE = field("subServicePrice");
  public static final QueryField SERVICE_ID = field("serviceID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String subServiceTitle;
  private final @ModelField(targetType="Int") Integer subServicePrice;
  private final @ModelField(targetType="ID", isRequired = true) String serviceID;
  public String getId() {
      return id;
  }
  
  public String getSubServiceTitle() {
      return subServiceTitle;
  }
  
  public Integer getSubServicePrice() {
      return subServicePrice;
  }
  
  public String getServiceId() {
      return serviceID;
  }
  
  private SubService(String id, String subServiceTitle, Integer subServicePrice, String serviceID) {
    this.id = id;
    this.subServiceTitle = subServiceTitle;
    this.subServicePrice = subServicePrice;
    this.serviceID = serviceID;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      SubService subService = (SubService) obj;
      return ObjectsCompat.equals(getId(), subService.getId()) &&
              ObjectsCompat.equals(getSubServiceTitle(), subService.getSubServiceTitle()) &&
              ObjectsCompat.equals(getSubServicePrice(), subService.getSubServicePrice()) &&
              ObjectsCompat.equals(getServiceId(), subService.getServiceId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getSubServiceTitle())
      .append(getSubServicePrice())
      .append(getServiceId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("SubService {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("subServiceTitle=" + String.valueOf(getSubServiceTitle()) + ", ")
      .append("subServicePrice=" + String.valueOf(getSubServicePrice()) + ", ")
      .append("serviceID=" + String.valueOf(getServiceId()))
      .append("}")
      .toString();
  }
  
  public static ServiceIdStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static SubService justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new SubService(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      subServiceTitle,
      subServicePrice,
      serviceID);
  }
  public interface ServiceIdStep {
    BuildStep serviceId(String serviceId);
  }
  

  public interface BuildStep {
    SubService build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep subServiceTitle(String subServiceTitle);
    BuildStep subServicePrice(Integer subServicePrice);
  }
  

  public static class Builder implements ServiceIdStep, BuildStep {
    private String id;
    private String serviceID;
    private String subServiceTitle;
    private Integer subServicePrice;
    @Override
     public SubService build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new SubService(
          id,
          subServiceTitle,
          subServicePrice,
          serviceID);
    }
    
    @Override
     public BuildStep serviceId(String serviceId) {
        Objects.requireNonNull(serviceId);
        this.serviceID = serviceId;
        return this;
    }
    
    @Override
     public BuildStep subServiceTitle(String subServiceTitle) {
        this.subServiceTitle = subServiceTitle;
        return this;
    }
    
    @Override
     public BuildStep subServicePrice(Integer subServicePrice) {
        this.subServicePrice = subServicePrice;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String subServiceTitle, Integer subServicePrice, String serviceId) {
      super.id(id);
      super.serviceId(serviceId)
        .subServiceTitle(subServiceTitle)
        .subServicePrice(subServicePrice);
    }
    
    @Override
     public CopyOfBuilder serviceId(String serviceId) {
      return (CopyOfBuilder) super.serviceId(serviceId);
    }
    
    @Override
     public CopyOfBuilder subServiceTitle(String subServiceTitle) {
      return (CopyOfBuilder) super.subServiceTitle(subServiceTitle);
    }
    
    @Override
     public CopyOfBuilder subServicePrice(Integer subServicePrice) {
      return (CopyOfBuilder) super.subServicePrice(subServicePrice);
    }
  }
  
}
