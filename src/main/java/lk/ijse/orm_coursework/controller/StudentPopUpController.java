package lk.ijse.orm_coursework.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.orm_coursework.bo.BOFactory;
import lk.ijse.orm_coursework.bo.BOTypes;
import lk.ijse.orm_coursework.bo.custom.CourseBO;
import lk.ijse.orm_coursework.bo.custom.StudentBO;
import lk.ijse.orm_coursework.dto.CourseDTO;
import lk.ijse.orm_coursework.dto.StudentDTO;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentPopUpController implements Initializable {

    private final StudentBO studentsBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

    private final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String phoneRegex = "^07\\d{8}$";

    @FXML
    public TextField txtFirstName;
    @FXML
    public TextField txtLastName;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtContact;
    @FXML
    public TextField txtAddress;
    @FXML
    public TextField txtDOB;
    @FXML
    public TextField txtRegDate;
    @FXML
    public Button btnSave;
    @FXML
    public Button btnUpdate;
    @FXML
    public Label lblStudentId;
    @FXML
    public ListView<String> listView;   // show course IDs

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblStudentId.setText(studentsBO.generateNewStudentId());

            // Enable multiple selection in listView
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            // Load courses into ListView
            List<CourseDTO> allCourses = courseBO.getAllCourses();
            for (CourseDTO course : allCourses) {
                listView.getItems().add(course.getCourseId()); // show course IDs
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnSaveOncAction(ActionEvent actionEvent) {
        StudentDTO studentDTO = buildStudentDTOFromForm();
        if (studentDTO == null) return; // validation failed

        try {
            boolean isSaved = studentsBO.saveStudents(studentDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Student saved successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save student", ButtonType.OK).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        StudentDTO studentDTO = buildStudentDTOFromForm();
        if (studentDTO == null) return;

        try {
            boolean isUpdated = studentsBO.updateStudents(studentDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Student updated successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update student", ButtonType.OK).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData(StudentDTO dto) {
        lblStudentId.setText(dto.getStudentId());
        txtFirstName.setText(dto.getFirstName());
        txtLastName.setText(dto.getLastName());
        txtEmail.setText(dto.getEmail());
        txtContact.setText(dto.getPhone());
        txtAddress.setText(dto.getAddress());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtDOB.setText(sdf.format(dto.getDob()));
        txtRegDate.setText(sdf.format(dto.getRegistrationDate()));

        // Pre-select courses in ListView
        if (dto.getCourseIds() != null) {
            for (String courseId : dto.getCourseIds()) {
                listView.getSelectionModel().select(courseId);
            }
        }

        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
    }

    // 🔹 Helper method to collect student data from form
    private StudentDTO buildStudentDTOFromForm() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String dob = txtDOB.getText();
        String regDate = txtRegDate.getText();

        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contact.isEmpty()
                || address.isEmpty() || dob.isEmpty() || regDate.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK).show();
            return null;
        }

        if (!email.matches(emailRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid email format", ButtonType.OK).show();
            return null;
        }

        if (!contact.matches(phoneRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid phone number format", ButtonType.OK).show();
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dobDate;
        Date regDateDate;
        try {
            dobDate = sdf.parse(dob);
            regDateDate = sdf.parse(regDate);
        } catch (ParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid date format (use yyyy-MM-dd)", ButtonType.OK).show();
            return null;
        }

        // Get selected courses
        ObservableList<String> selectedCourses = listView.getSelectionModel().getSelectedItems();

        return StudentDTO.builder()
                .studentId(lblStudentId.getText())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(contact)
                .address(address)
                .dob(dobDate)
                .registrationDate(regDateDate)
                .courseIds(new ArrayList<>(selectedCourses)) // add selected course IDs
                .build();
    }
}
