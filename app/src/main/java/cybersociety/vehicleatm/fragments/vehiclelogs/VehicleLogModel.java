package cybersociety.vehicleatm.fragments.vehiclelogs;

import java.util.ArrayList;

public class VehicleLogModel {

    private String title;

    private String message;


    public VehicleLogModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public VehicleLogModel() {

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
