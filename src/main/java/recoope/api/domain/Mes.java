package recoope.api.domain;

public enum Mes {
    JANEIRO(1),
    FEVEREIRO(2),
    MARCO(3),
    ABRIL(4),
    MAIO(5),
    JUNHO(6),
    JULHO(7),
    AGOSTO(8),
    SETEMBRO(9),
    OUTUBRO(10),
    NOVEMBRO(11),
    DEZEMBRO(12);

    private final int numero;

    Mes(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public static int fromString(String nomeMes) {
        try {
            return Mes.valueOf(nomeMes.toUpperCase()).getNumero();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Mês inválido: " + nomeMes);
        }
    }
}
