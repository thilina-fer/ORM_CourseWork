package lk.ijse.orm_coursework.dao;

import lk.ijse.orm_coursework.dao.custom.StudentDAO;
import lk.ijse.orm_coursework.dao.custom.impl.StudentDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return daoFactory == null ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes) {
        return switch (daoTypes) {
            case STUDENT -> (T) new StudentDAOImpl();
        };
    }
}
