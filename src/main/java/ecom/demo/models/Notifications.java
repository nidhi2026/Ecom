package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notifications {

    private UUID notificationsID;

    private UUID userID;

    private String message;

    public Notifications(UUID notificationsID, UUID userID, String message) {
        this.notificationsID = notificationsID;
        this.userID = userID;
        this.message = message;
    }

    
    
}
