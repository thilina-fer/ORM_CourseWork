package lk.ijse.orm_coursework.bo;

import lk.ijse.orm_coursework.bo.custom.impl.InstructorBOImpl;
import lk.ijse.orm_coursework.bo.custom.impl.StudentBOImpl;
import lk.ijse.orm_coursework.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getInstance() {
        return boFactory == null ? (boFactory = new BOFactory()) : boFactory;
    }

    @SuppressWarnings("unchecked")
    public <Hello extends SuperBO> Hello getBO(BOTypes boType) {
        return switch (boType) {
            case STUDENT -> (Hello) new StudentBOImpl();
            case  INSTRUCTOR -> (Hello) new InstructorBOImpl();
            case USER -> (Hello) new UserBOImpl();
        };
    }
}
