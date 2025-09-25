package lk.ijse.orm_coursework.dto.tm;

import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class LessonsTM {
    private String lessonId;
    private String studentId;
    private String courseId;
    private String instructorId;
    private Date lessonDate;
    private String startTime;
    private String endTime;
    private String status;
    private Pane action;


}
