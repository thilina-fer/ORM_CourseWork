package lk.ijse.orm_coursework.bo;

import lk.ijse.orm_coursework.bo.custom.impl.StudentBOImpl;

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
        };
    }

}
