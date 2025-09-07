package lk.ijse.orm_coursework.bo.custom.impl;

import lk.ijse.orm_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_coursework.bo.exception.DuplicateException;
import lk.ijse.orm_coursework.bo.exception.InUseException;
import lk.ijse.orm_coursework.bo.util.EntityDTOConvertor;
import lk.ijse.orm_coursework.dao.DAOFactory;
import lk.ijse.orm_coursework.dao.DAOTypes;
import lk.ijse.orm_coursework.dao.custom.InstructorDAO;
import lk.ijse.orm_coursework.dto.InstructorDTO;
import lk.ijse.orm_coursework.entity.Instructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorBOImpl implements InstructorBO {

    private final InstructorDAO instructorDAO = DAOFactory.getInstance().getDAO(DAOTypes.INSTRUCTOR);
    private final EntityDTOConvertor convertor = new EntityDTOConvertor();


    @Override
    public void saveInstructor(InstructorDTO dto) throws DuplicateException, Exception {
        Instructor instructor = convertor.getInstructor(dto);

        instructorDAO.save(instructor);
    }
    @Override
    public List<InstructorDTO> getAllInstructor() throws SQLException {
        List<Instructor> instructors = instructorDAO.getAll();
        List<InstructorDTO> dtos = new ArrayList<>();
        for (Instructor instructor : instructors) {
            dtos.add(convertor.getInstructorDTO(instructor));
        }
        return dtos;
    }



    @Override
    public void updateInstructor(InstructorDTO dto) throws SQLException {
        Instructor instructor = convertor.getInstructor(dto);
        instructorDAO.update(instructor);
    }

    @Override
    public boolean deleteInstructor(String id) throws InUseException, Exception {
        return instructorDAO.delete(id);
    }

    @Override
    public String getNextId() throws SQLException {
        String lastId = instructorDAO.getLastId();
        char tableChar = 'I';
        if (lastId != null){
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }
}
