package ecom.demo.controllers;

import ecom.demo.models.Notification;
import ecom.demo.models.User;
import ecom.demo.service.NotificationService;
import ecom.demo.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // For role-based access control
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService , UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    // Endpoint to view all notifications (accessible to all users)
    @GetMapping("/all")
    public String getAllNotifications(Model model, Authentication authentication) {
        List<Notification> notifications = notificationService.getAllNotifications();
        User currentUser = userService.getUserByEmail(authentication.getName());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("notifications", notifications);  // Add the notifications list to the model
        return "notifications";  // The name of your HTML file (Thymeleaf template)
    }

    // Endpoint to add a new notification (accessible only to admin)
    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addNotification(@RequestParam String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now()); // Set the current time
        notificationService.addNotification(notification);
        return "redirect:/notifications/all";  // Redirect to the 'all notifications' page after adding
    }

    // Endpoint to update an existing notification (accessible only to admin)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{notificationsID}")
    public String updateNotification(@PathVariable String notificationsID, @RequestParam String message) {
        notificationService.updateNotification(notificationsID, message);
        return "redirect:/notifications/all";  // Redirect to the 'all notifications' page after updating
    }

    // Endpoint to delete a notification (accessible only to admin)
    // @PreAuthorize("hasRole('ADMIN')")
    // @PostMapping("/delete/{notificationsID}")
    // public String deleteNotification(@PathVariable String notificationsID) {
    //     notificationService.deleteNotification(notificationsID);
    //     return "redirect:/notifications/all";  // Redirect to the 'all notifications' page after deleting
    // }
    
    @PostMapping("/delete/{notificationsID}")
    public String deleteNotification(@PathVariable UUID notificationsID) {
        notificationService.deleteNotification(notificationsID);
        return "redirect:/notifications/all";
    }

    

}