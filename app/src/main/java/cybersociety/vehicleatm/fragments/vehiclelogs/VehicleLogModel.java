package cybersociety.vehicleatm.fragments.vehiclelogs;

public class VehicleLogModel {
    private static final String TAG = VehicleLogModel.class.getSimpleName();

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
