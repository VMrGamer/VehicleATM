package cybersociety.vehicleatm.fragments.feed24hr;

public class Feed24hrModel {

    private String docID;

    private String title;

    private String message;

    private String snap_link;

    public Feed24hrModel(String docID, String title, String message, String snap_link) {
        this.docID = docID;
        this.title = title;
        this.message = message;
        this.snap_link = snap_link;
    }

    public Feed24hrModel() {
        this.docID = "none";
        this.title = "none";
        this.message = "none";
        this.snap_link = "none";
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

    public String getSnap_link() {
        return snap_link;
    }

    public void setSnap_link(String snap_link) {
        this.snap_link = snap_link;
    }
}
