package util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-20
 **/
public class NotificationMessageUtil {

    public void successMassage(String text) {
        Image image = new Image(("/assests/images/pass.png"));
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text(text);
        notifications.title("Confirmation Massage");
        notifications.hideAfter(Duration.seconds(3));
        notifications.position(Pos.TOP_CENTER);
        notifications.darkStyle();
        notifications.show();
    }

    public void errorMassage(String text) {
        Image image = new Image(("/assests/images/fail.png"));
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text(text);
        notifications.title("Error Massage");
        notifications.hideAfter(Duration.seconds(3));
        notifications.position(Pos.TOP_CENTER);
        notifications.darkStyle();
        notifications.show();
    }
}
