package cybersociety.vehicleatm.fragments.viewvehicle;

public class ViewVehicleModel {
    private static final String TAG = ViewVehicleModel.class.getSimpleName();

    private String title;

    private String message;


    public ViewVehicleModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public ViewVehicleModel() {

    }

    public ViewVehicleModel(Object vehicle_no, String message) {
        this.title = title;
        this.message = message;
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
