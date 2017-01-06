package athrow.rocks.android_drivers_log.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * TravelLog
 * Created by jose on 10/15/16.
 */
public class TravelLog extends RealmObject {
    @PrimaryKey
    private
    String id;
    private String date;
    private String time;
    private String time_of_day;
    private int employee_id;
    private String employee_name;
    private int from_site_id;
    private String from_site_name;
    private int to_site_id;
    private String to_site_name;
    private String reason;
    private int odometer_start;
    private int odometer_end;
    private int miles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime_of_day() {
        return time_of_day;
    }

    public void setTime_of_day(String time_of_day) {
        this.time_of_day = time_of_day;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public int getFrom_site_id() {
        return from_site_id;
    }

    public void setFrom_site_id(int from_site_id) {
        this.from_site_id = from_site_id;
    }

    public String getFrom_site_name() {
        return from_site_name;
    }

    public void setFrom_site_name(String from_site_name) {
        this.from_site_name = from_site_name;
    }

    public int getTo_site_id() {
        return to_site_id;
    }

    public void setTo_site_id(int to_site_id) {
        this.to_site_id = to_site_id;
    }

    public String getTo_site_name() {
        return to_site_name;
    }

    public void setTo_site_name(String to_site_name) {
        this.to_site_name = to_site_name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getOdometer_start() {
        return odometer_start;
    }

    public void setOdometer_start(int odometer_start) {
        this.odometer_start = odometer_start;
    }

    public int getOdometer_end() {
        return odometer_end;
    }

    public void setOdometer_end(int odometer_end) {
        this.odometer_end = odometer_end;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }
}
