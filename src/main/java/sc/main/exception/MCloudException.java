/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sc.main.exception;

/**
 *
 * @author bor
 */
public class MCloudException extends RuntimeException {

    public MCloudException() {
    }

    public MCloudException(String message) {
        super(message);
    }

    public MCloudException(String message, Throwable cause) {
        super(message, cause);
    }

    public MCloudException(Throwable cause) {
        super(cause);
    }

    public MCloudException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
