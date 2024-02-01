package med.voll.api.domain.paciente.dto;

import med.voll.api.domain.paciente.Paciente;

public record ListagemDadosPaciente(

        Long id,
        String nome,
        String email,
        String cpf
) {
    public ListagemDadosPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }

}
