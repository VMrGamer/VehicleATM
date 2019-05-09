package cybersociety.vehicleatm.fragments.userprofile;

public class UserProfileModel {
    private static final String TAG = UserProfileModel.class.getSimpleName();

    private String title;

    private String message;


    public UserProfileModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public UserProfileModel() {

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
