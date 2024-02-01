package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.dto.AtualizacaoDadosPaciente;
import med.voll.api.domain.paciente.dto.DadosPaciente;
import med.voll.api.domain.paciente.dto.DetalhamentoDadosPaciente;
import med.voll.api.domain.paciente.dto.ListagemDadosPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    PacienteRepository pacienteRepository;

    @Autowired
    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<?> cadastrarPaciente(@RequestBody @Valid DadosPaciente dadosPaciente, UriComponentsBuilder uriComponentsBuilder) {
        var paciente = new Paciente(dadosPaciente);
        pacienteRepository.save(paciente);

        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhamentoDadosPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemDadosPaciente>> listarPacientes(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        //return pacienteRepository.findAll().stream().map(ListagemDadosPaciente::new).toList();

        var paciente = pacienteRepository.findAllByAtivoTrue(paginacao).map(ListagemDadosPaciente::new);
        return ResponseEntity.ok(paciente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizarPacientes(@RequestBody @Valid AtualizacaoDadosPaciente dadosPaciente) {
        var paciente = pacienteRepository.getReferenceById(dadosPaciente.id());
        paciente.atualizarPaciente(dadosPaciente);

        return ResponseEntity.ok(new DetalhamentoDadosPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarPaciente(@PathVariable Long id) {
        //pacienteRepository.deleteById(id);
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalharPaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoDadosPaciente(paciente));
    }

}
