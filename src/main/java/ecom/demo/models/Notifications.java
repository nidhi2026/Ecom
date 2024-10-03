package ecom.demo.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notifications {

    private String notificationsID;

    private String userID;

    private String message;
    
}
