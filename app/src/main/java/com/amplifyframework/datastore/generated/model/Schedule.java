package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
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

/** This is an auto generated class representing the Schedule type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Schedules")
public final class Schedule implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField DATE = field("date");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="AWSDate") Temporal.Date date;
  private final @ModelField(targetType="User") @HasMany(associatedWith = "scheduleID", type = User.class) List<User> Users = null;
  public String getId() {
      return id;
  }
  
  public Temporal.Date getDate() {
      return date;
  }
  
  public List<User> getUsers() {
      return Users;
  }
  
  private Schedule(String id, Temporal.Date date) {
    this.id = id;
    this.date = date;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Schedule schedule = (Schedule) obj;
      return ObjectsCompat.equals(getId(), schedule.getId()) &&
              ObjectsCompat.equals(getDate(), schedule.getDate());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDate())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Schedule {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("date=" + String.valueOf(getDate()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static Schedule justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Schedule(
      id,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      date);
  }
  public interface BuildStep {
    Schedule build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep date(Temporal.Date date);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Temporal.Date date;
    @Override
     public Schedule build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Schedule(
          id,
          date);
    }
    
    @Override
     public BuildStep date(Temporal.Date date) {
        this.date = date;
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
    private CopyOfBuilder(String id, Temporal.Date date) {
      super.id(id);
      super.date(date);
    }
    
    @Override
     public CopyOfBuilder date(Temporal.Date date) {
      return (CopyOfBuilder) super.date(date);
    }
  }
  
}
