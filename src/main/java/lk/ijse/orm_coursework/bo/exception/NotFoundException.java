package lk.ijse.orm_coursework.bo.exception;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 7/18/2025 11:07 AM
 * Project: Supermarket-layered
 * --------------------------------------------
 **/

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
