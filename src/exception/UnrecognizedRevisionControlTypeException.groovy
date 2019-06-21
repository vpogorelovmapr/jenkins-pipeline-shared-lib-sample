package exception

class UnrecognizedRevisionControlTypeException extends RuntimeException {

    UnrecognizedRevisionControlTypeException(String message) {
        super(message)
    }
}
