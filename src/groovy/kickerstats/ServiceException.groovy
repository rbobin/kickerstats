package kickerstats

import org.springframework.context.MessageSourceResolvable

class ServiceException extends RuntimeException {

    List<MessageSourceResolvable> errors
}
