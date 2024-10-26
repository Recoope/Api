package recoope.api.domain;

public class Mensagens {

    // Cooperativa.
    public static final String COOPERATIVA_ENCONTRADA = "Cooperativa encontrada com sucesso!";
    public static final String COOPERATIVA_NAO_ENCONTRADA = "Cooperativa não encontrada!";
    public static final String NENHUMA_COOPERATIVA_ENCONTRADA = "Nenhuma cooperativa encontrada.";
    public static final String COOPERATIVA_NAO_EXISTE = "Cooperativa não existe.";
    // Empresa.
    public static final String EMPRESA_ENCONTRADA = "Empresa encontrada com sucesso!";
    public static final String EMPRESA_CADASTRADA = "Empresa cadastrada com sucesso!";
    public static final String EMPRESA_ATUALIZADA = "Empresa atualizada com sucesso!";
    public static final String EMPRESA_REMOVIDA = "Empresa removida com sucesso!";
    public static final String EMPRESA_NAO_ENCONTRADA = "Empresa não encontrada!";
    public static final String EMAIL_CNPJ_INVALIDO = "Parâmetro fornecido não é um E-mail ou CNPJ.";
    // Leilão.
    public static final String LEILAO_ENCONTRADO = "Leilão encontrado com sucesso!";
    public static final String LEILAO_NAO_ENCONTRADO = "Leilão não encontrado!";
    public static final String NENHUM_LEILAO_ENCONTRADO = "Não foi encontrado nenhum leilão.";
    // Lance
    public static final String LANCE_ATRIBUIDO = "Lance atribuido com sucesso!";
    public static final String LANCE_IGUAL = "Esse lance é igual ao maior lance do leilão, ele deve ser maior.";
    public static final String LANCE_MENOR = "Esse lance é menor que o maior lance do leilão.";
    public static final String LANCE_NAO_EXISTE = "O leilão não possui lances dessa empresa!";
    public static final String LANCE_CANCELADO = "Lance(s) cancelado com sucesso!";
    public static final String LANCE_PARAM_INVALIDOS = "CNPJ e valor devem ser informados.";
    // Outros.
    public static final String NAO_EXISTE_CNPJ_CORRESPONDENTE_OU_SENHA_INCORRETA = "O CNPJ fornecido não possui uma correspondência ou a senha está incorreta.";
    public static final String NAO_EXISTE_EMAIL_CORRESPONDENTE_OU_SENHA_INCORRETA = "O E-mail fornecido não possui uma correspondência ou a senha está incorreta.";
    public static final String PARAMETROS_VAZIOS = "Não devem ser enviados parametros nulos.";
    public static final String LOGIN_SUCESSO = "Login realizado com sucesso!";
    public static final String SENHA_NAO_CORREPONDENTE = "As senhas não correspondem.";
    public static final String CNPJ_INVALIDO = "CNPJ inválido.";
    public static final String CNPJ_EXISTENTE = "CNPJ já existente.";
    public static final String NOME_INVALIDO = "O nome da empresa deve conter pelo menos 3 caracteres e no máximo 255 caracteres.";
    public static final String EMAIL_EXISTENTE = "Email já existente.";
    public static final String EMAIL_INVALIDO = "Email inválido.";
    public static final String TELEFONE_EXISTENTE = "Telefone já existente.";
    public static final String TELEFONE_INVALIDO = "Telefone inválido.";
    public static final String SENHA_INVALIDA = "A senha deve ter pelo menos 8 caracteres, um número e um caractere especial.";
    public static final String NENHUM_RECIBO_ENCONTRADO = "Nenhum recibo encontrado";
    public static final String CODIGO_ENVIADO = "Código de recuperação gerado com sucesso!";
    public static final String CODIGO_VALIDO = "Código de recuperação confere.";
    public static final String CODIGO_INVALIDO = "Código de recuperação inválido.";
    public static final String ERRO_REDIS = "Não foi possível gerar o código de recuperação, tente novamente mais tarde.";
}
