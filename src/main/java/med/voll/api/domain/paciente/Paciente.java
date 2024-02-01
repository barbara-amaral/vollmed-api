package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.paciente.dto.AtualizacaoDadosPaciente;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.dto.DadosPaciente;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente(DadosPaciente dadosPaciente) {
        this.nome = dadosPaciente.nome();
        this.cpf = dadosPaciente.cpf();
        this.email = dadosPaciente.email();
        this.telefone = dadosPaciente.telefone();
        this.endereco = new Endereco(dadosPaciente.endereco());
        this.ativo = true;
    }

    public void atualizarPaciente(AtualizacaoDadosPaciente dadosPaciente) {
        if (dadosPaciente.nome() != null) {
            this.nome = dadosPaciente.nome();
        }
        if (dadosPaciente.telefone() != null) {
            this.telefone = dadosPaciente.telefone();
        }
        if (dadosPaciente.dadosEndereco() != null) {
            this.endereco.atualizarEndereco(dadosPaciente.dadosEndereco());
        }
    }
    public void excluir() {
        this.ativo = false;
    }
}
