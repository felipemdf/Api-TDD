package localhost.gestor_eventos_back_end.exception;

public class ObjectNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException (String message) {
		super(message);
	}
}
