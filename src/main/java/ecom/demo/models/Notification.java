package ecom.demo.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notification {

    private UUID notificationsID;

    // private String userID;
    private LocalDateTime timestamp;

    private String message;
    
}