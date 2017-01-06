package athrow.rocks.android_drivers_log.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by joselopez on 10/19/16.
 */

public class Sites extends RealmObject {
    @PrimaryKey
    private
    String id;
    private int org;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrg() {
        return org;
    }

    public void setOrg(int org) {
        this.org = org;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
