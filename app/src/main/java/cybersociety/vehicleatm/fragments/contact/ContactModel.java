package cybersociety.vehicleatm.fragments.contact;

public class ContactModel {

    private String title;

    private String message;


    public ContactModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public ContactModel() {

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
