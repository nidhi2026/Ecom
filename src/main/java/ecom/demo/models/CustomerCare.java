package ecom.demo.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCare {
    
    private String customerCareID;

    private String userID;

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    private Priority priority;

    private String issue; 

    private String description;

    public enum Status {
        SUBMITTED, IN_PROGRESS, RESOLVED
    }

    public Status status;

    private LocalDateTime filedTime;

}
