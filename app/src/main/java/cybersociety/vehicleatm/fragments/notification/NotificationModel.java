package cybersociety.vehicleatm.fragments.notification;

public class NotificationModel {
    private static final String TAG = NotificationModel.class.getSimpleName();

    private String title;

    private String message;


    public NotificationModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public NotificationModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
