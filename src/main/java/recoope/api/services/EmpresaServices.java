package recoope.api.services;


import org.springframework.stereotype.Service;
import recoope.api.domain.ApiResponse;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.inputs.EmpresaRegistroParams;
import recoope.api.repository.IEmpresaRepository;

@Service
public class EmpresaServices {
    private final IEmpresaRepository empresaRepository;

    public EmpresaServices(IEmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public ApiResponse<Empresa> cadastrar(EmpresaRegistroParams novaEmpresaParams) {

        Empresa novaEmpresa = new Empresa();

        novaEmpresa.setNomeEmpresa(novaEmpresaParams.nome);
        novaEmpresa.setCnpjEmpresa(novaEmpresaParams.cnpj);
        novaEmpresa.setEmailEmpresa(novaEmpresaParams.email);
        novaEmpresa.setTelefoneEmpresa(novaEmpresaParams.telefone);

        // Senha
        var senha = novaEmpresaParams.senha;
        var confirmacaoSenha = novaEmpresaParams.confirmacaoSenha;
        if (confirmacaoSenha.equals(senha))
            novaEmpresa.setSenhaEmpresa(novaEmpresaParams.senha);
        else return new ApiResponse<>("As senhas não correspondem.");

        empresaRepository.save(novaEmpresa);
        return new ApiResponse<>("Empresa cadastrada com sucesso!", novaEmpresa);
    }
}
