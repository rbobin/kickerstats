package kickerstats

import grails.converters.JSON
import org.springframework.validation.ObjectError

class BaseController {

    def messageSource

    final static int SUCCESS = 200,
                     CREATED = 201,
                     BAD_REQUEST = 400,
                     NOT_FOUND = 404,
                     CONFlICT = 409

    protected renderMissingParameter(parameter) {
        response.setStatus BAD_REQUEST
        render([success: false, errors: ["missing $parameter parameter"]] as JSON)
    }

    protected renderWrongType(parameter, type) {
        response.setStatus BAD_REQUEST
        render([success: false, errors: ["$parameter parameter must be of type $type"]] as JSON)
    }

    protected renderNotFound(entity) {
        response.setStatus NOT_FOUND
        render([sucess: false, errors: ["$entity not found"]] as JSON)
    }

    protected renderConflict(message) {
        response.setStatus CONFlICT
        render([success: false, errors: [message]] as JSON)
    }

    protected renderValidationErrors(entity) {
        response.setStatus BAD_REQUEST
        def responseMap = [success: false]
        responseMap.errors = entity.getErrors().allErrors.collect { ObjectError error ->
            messageSource.getMessage(error, Locale.ENGLISH) // TODO unhardcode locale
        }
        render(responseMap as JSON)
    }
}
