package ecom.demo.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCare {
    
    private UUID customerCareID;

    private UUID userID;

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

    public CustomerCare(UUID customerCareID, UUID userID, String issue, String description, LocalDateTime filedTime) {
        this.customerCareID = customerCareID;
        this.userID = userID;
        this.issue = issue;
        this.description = description;
        this.filedTime = filedTime;
        this.priority = Priority.MEDIUM;
        this.status = Status.SUBMITTED;
    }

    

}
