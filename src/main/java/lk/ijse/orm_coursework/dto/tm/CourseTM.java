package lk.ijse.orm_coursework.dto.tm;

import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CourseTM {
    private String courseId;
    private String course_name;
    private String duration;
    private double fee;
    private String description;
    private String instructorId;
    private Pane action;
}
