package athrow.rocks.android_drivers_log.interfaces;

import athrow.rocks.android_drivers_log.data.APIResponse;

/**
 * Created by jose on 12/29/16.
 */

public interface OnTaskComplete {
    @SuppressWarnings("MethodNameSameAsClassName")
    void OnTaskComplete(APIResponse apiResponses);
}
