package edu.sjsu.cmpe275.cartpool.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex) {
        logger.info("SQLException Occured:: URL=" + request.getRequestURL());
        return "database_error";
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not valid data")
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(Exception ex) {
        logger.error(ex.getLocalizedMessage());
        ex.printStackTrace();
        return ex.getMessage();
        //returning 404 error code
    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Entity Not Found")
    @ExceptionHandler(IOException.class)
    public void handleAllException(Exception ex) {
        logger.error("IO Exception occurred!!");
        logger.error(ex.getStackTrace().toString());
        ex.printStackTrace();
        //returning 404 error code
    }


    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Entity Already Exists")
    @ExceptionHandler({org.springframework.orm.jpa.JpaSystemException.class,
            DataIntegrityViolationException.class,
            SQLIntegrityConstraintViolationException.class,
            org.hibernate.exception.ConstraintViolationException.class})
    public void handleDuplicateEntity(Exception ex) {
        logger.error("Duplicate Entry!!");
        logger.error(ex.getStackTrace().toString());
        ex.printStackTrace();
        //returning 409 error code
    }
}
