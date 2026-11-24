package br.com.bingo.exception;

public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
