package kickerstats

class MissingPropertyException extends RuntimeException {

    String property

    MissingPropertyException(property) {
        this.property = property
    }
}
