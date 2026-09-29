/**
 * Exceção base do sistema de estoque.
 * Todas as exceções específicas do domínio herdam desta classe.
 */
public class EstoqueException extends Exception {
    public EstoqueException(String mensagem) {
        super(mensagem);
    }
}
