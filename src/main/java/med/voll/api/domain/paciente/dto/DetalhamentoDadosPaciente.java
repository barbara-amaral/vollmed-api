package med.voll.api.domain.paciente.dto;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.Paciente;

public record DetalhamentoDadosPaciente(

        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {

    public DetalhamentoDadosPaciente(Paciente paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(),
                paciente.getCpf(), paciente.getEndereco());
    }

}
