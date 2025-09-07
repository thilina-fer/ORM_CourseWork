package lk.ijse.orm_coursework.bo.exception;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 7/18/2025 9:43 AM
 * Project: Supermarket-layered
 * --------------------------------------------
 **/

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}
