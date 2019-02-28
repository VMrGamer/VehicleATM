package cybersociety.vehicleatm.fragments.feed24hr;

import java.util.ArrayList;

public class Feed24hrModel {

    private String docID;

    private String title;

    private String message;


    public Feed24hrModel(String docID, String title, String message) {
        this.docID = docID;
        this.title = title;
        this.message = message;
    }

    public Feed24hrModel() {
        this.docID = "none";
        this.title = "none";
        this.message = "none";
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
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
