package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the Service type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Services")
@Index(name = "byUser", fields = {"userID"})
public final class Service implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField SERVICE_TITLE = field("serviceTitle");
  public static final QueryField SERVICE_BASE_PRICE = field("serviceBasePrice");
  public static final QueryField TOTAL = field("total");
  public static final QueryField USER_ID = field("userID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String serviceTitle;
  private final @ModelField(targetType="String") String serviceBasePrice;
  private final @ModelField(targetType="Int") Integer total;
  private final @ModelField(targetType="SubService") @HasMany(associatedWith = "serviceID", type = SubService.class) List<SubService> SubServices = null;
  private final @ModelField(targetType="ID", isRequired = true) String userID;
  public String getId() {
      return id;
  }
  
  public String getServiceTitle() {
      return serviceTitle;
  }
  
  public String getServiceBasePrice() {
      return serviceBasePrice;
  }
  
  public Integer getTotal() {
      return total;
  }
  
  public List<SubService> getSubServices() {
      return SubServices;
  }
  
  public String getUserId() {
      return userID;
  }
  
  private Service(String id, String serviceTitle, String serviceBasePrice, Integer total, String userID) {
    this.id = id;
    this.serviceTitle = serviceTitle;
    this.serviceBasePrice = serviceBasePrice;
    this.total = total;
    this.userID = userID;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Service service = (Service) obj;
      return ObjectsCompat.equals(getId(), service.getId()) &&
              ObjectsCompat.equals(getServiceTitle(), service.getServiceTitle()) &&
              ObjectsCompat.equals(getServiceBasePrice(), service.getServiceBasePrice()) &&
              ObjectsCompat.equals(getTotal(), service.getTotal()) &&
              ObjectsCompat.equals(getUserId(), service.getUserId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getServiceTitle())
      .append(getServiceBasePrice())
      .append(getTotal())
      .append(getUserId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Service {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("serviceTitle=" + String.valueOf(getServiceTitle()) + ", ")
      .append("serviceBasePrice=" + String.valueOf(getServiceBasePrice()) + ", ")
      .append("total=" + String.valueOf(getTotal()) + ", ")
      .append("userID=" + String.valueOf(getUserId()))
      .append("}")
      .toString();
  }
  
  public static UserIdStep builder() {
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
  public static Service justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Service(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      serviceTitle,
      serviceBasePrice,
      total,
      userID);
  }
  public interface UserIdStep {
    BuildStep userId(String userId);
  }
  

  public interface BuildStep {
    Service build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep serviceTitle(String serviceTitle);
    BuildStep serviceBasePrice(String serviceBasePrice);
    BuildStep total(Integer total);
  }
  

  public static class Builder implements UserIdStep, BuildStep {
    private String id;
    private String userID;
    private String serviceTitle;
    private String serviceBasePrice;
    private Integer total;
    @Override
     public Service build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Service(
          id,
          serviceTitle,
          serviceBasePrice,
          total,
          userID);
    }
    
    @Override
     public BuildStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userID = userId;
        return this;
    }
    
    @Override
     public BuildStep serviceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
        return this;
    }
    
    @Override
     public BuildStep serviceBasePrice(String serviceBasePrice) {
        this.serviceBasePrice = serviceBasePrice;
        return this;
    }
    
    @Override
     public BuildStep total(Integer total) {
        this.total = total;
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
    private CopyOfBuilder(String id, String serviceTitle, String serviceBasePrice, Integer total, String userId) {
      super.id(id);
      super.userId(userId)
        .serviceTitle(serviceTitle)
        .serviceBasePrice(serviceBasePrice)
        .total(total);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder serviceTitle(String serviceTitle) {
      return (CopyOfBuilder) super.serviceTitle(serviceTitle);
    }
    
    @Override
     public CopyOfBuilder serviceBasePrice(String serviceBasePrice) {
      return (CopyOfBuilder) super.serviceBasePrice(serviceBasePrice);
    }
    
    @Override
     public CopyOfBuilder total(Integer total) {
      return (CopyOfBuilder) super.total(total);
    }
  }
  
}
