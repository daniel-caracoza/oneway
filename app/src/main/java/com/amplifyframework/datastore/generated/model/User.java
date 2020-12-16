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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users")
@Index(name = "bySchedule", fields = {"scheduleID"})
public final class User implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField NAME = field("name");
  public static final QueryField ADDRESS = field("address");
  public static final QueryField PHONE = field("phone");
  public static final QueryField SCHEDULE_ID = field("scheduleID");
  public static final QueryField SERVICES_TOTAL = field("servicesTotal");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String address;
  private final @ModelField(targetType="String") String phone;
  private final @ModelField(targetType="ID", isRequired = true) String scheduleID;
  private final @ModelField(targetType="Service") @HasMany(associatedWith = "userID", type = Service.class) List<Service> Services = null;
  private final @ModelField(targetType="Int") Integer servicesTotal;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getAddress() {
      return address;
  }
  
  public String getPhone() {
      return phone;
  }
  
  public String getScheduleId() {
      return scheduleID;
  }
  
  public List<Service> getServices() {
      return Services;
  }
  
  public Integer getServicesTotal() {
      return servicesTotal;
  }
  
  private User(String id, String name, String address, String phone, String scheduleID, Integer servicesTotal) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.scheduleID = scheduleID;
    this.servicesTotal = servicesTotal;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getName(), user.getName()) &&
              ObjectsCompat.equals(getAddress(), user.getAddress()) &&
              ObjectsCompat.equals(getPhone(), user.getPhone()) &&
              ObjectsCompat.equals(getScheduleId(), user.getScheduleId()) &&
              ObjectsCompat.equals(getServicesTotal(), user.getServicesTotal());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getAddress())
      .append(getPhone())
      .append(getScheduleId())
      .append(getServicesTotal())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("address=" + String.valueOf(getAddress()) + ", ")
      .append("phone=" + String.valueOf(getPhone()) + ", ")
      .append("scheduleID=" + String.valueOf(getScheduleId()) + ", ")
      .append("servicesTotal=" + String.valueOf(getServicesTotal()))
      .append("}")
      .toString();
  }
  
  public static ScheduleIdStep builder() {
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
  public static User justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new User(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      address,
      phone,
      scheduleID,
      servicesTotal);
  }
  public interface ScheduleIdStep {
    BuildStep scheduleId(String scheduleId);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep name(String name);
    BuildStep address(String address);
    BuildStep phone(String phone);
    BuildStep servicesTotal(Integer servicesTotal);
  }
  

  public static class Builder implements ScheduleIdStep, BuildStep {
    private String id;
    private String scheduleID;
    private String name;
    private String address;
    private String phone;
    private Integer servicesTotal;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          name,
          address,
          phone,
          scheduleID,
          servicesTotal);
    }
    
    @Override
     public BuildStep scheduleId(String scheduleId) {
        Objects.requireNonNull(scheduleId);
        this.scheduleID = scheduleId;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep address(String address) {
        this.address = address;
        return this;
    }
    
    @Override
     public BuildStep phone(String phone) {
        this.phone = phone;
        return this;
    }
    
    @Override
     public BuildStep servicesTotal(Integer servicesTotal) {
        this.servicesTotal = servicesTotal;
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
    private CopyOfBuilder(String id, String name, String address, String phone, String scheduleId, Integer servicesTotal) {
      super.id(id);
      super.scheduleId(scheduleId)
        .name(name)
        .address(address)
        .phone(phone)
        .servicesTotal(servicesTotal);
    }
    
    @Override
     public CopyOfBuilder scheduleId(String scheduleId) {
      return (CopyOfBuilder) super.scheduleId(scheduleId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder address(String address) {
      return (CopyOfBuilder) super.address(address);
    }
    
    @Override
     public CopyOfBuilder phone(String phone) {
      return (CopyOfBuilder) super.phone(phone);
    }
    
    @Override
     public CopyOfBuilder servicesTotal(Integer servicesTotal) {
      return (CopyOfBuilder) super.servicesTotal(servicesTotal);
    }
  }
  
}
