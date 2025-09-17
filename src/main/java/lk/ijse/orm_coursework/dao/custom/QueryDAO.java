package lk.ijse.orm_coursework.dao.custom;

import lk.ijse.orm_coursework.dao.SuperDAO;

public interface QueryDAO extends SuperDAO {
    public int getStudentCountForLesson(String lessonId);
}
