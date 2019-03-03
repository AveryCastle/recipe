package practice.springframework.mvc.recipe.exceptions;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NumberFormatException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView handlerBadRequest(Exception e) {
        log.error("handlerBadRequest: ", e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400");
        modelAndView.addObject("error", e);
        return modelAndView;
    }
}
