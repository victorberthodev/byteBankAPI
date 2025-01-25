package com.api.bytebank.exception;

/**
 * Exceção personalizada para representar erros na camada de serviço da aplicação.
 * Esta exceção é lançada quando uma regra de negócio é violada ou ocorre um erro na lógica de serviço.
 */
public class ServiceException extends RuntimeException {

    /**
     * Constrói uma ServiceException com a mensagem de erro específica.
     *
     * @param message Mensagem descritiva do erro ocorrido.
     */
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
