package lk.ijse.orm_coursework.bo.exception;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 7/18/2025 9:53 AM
 * Project: Supermarket-layered
 * --------------------------------------------
 **/

public class InUseException extends RuntimeException {
    public InUseException(String message) {
        super(message);
    }
}
