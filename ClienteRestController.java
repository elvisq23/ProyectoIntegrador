package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.ClienteRequestDTO;
import com.utp.integradorspringboot.dto.ClienteResponseDTO;
import com.utp.integradorspringboot.models.Cliente;
import com.utp.integradorspringboot.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes(@RequestParam(required = false) String search) {
        List<Cliente> clientes = clienteService.getAllClientes(search);
        List<ClienteResponseDTO> dtos = clientes.stream()
                                                .map(ClienteResponseDTO::new)
                                                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id) {
        return clienteService.getClienteById(id)
                .map(ClienteResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@Valid @RequestBody ClienteRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            Cliente newCliente = clienteService.saveCliente(request);
            return new ResponseEntity<>(new ClienteResponseDTO(newCliente), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            if (request.getId() != null && !request.getId().equals(id)) {
                return ResponseEntity.badRequest().body("El ID del cliente en la URL no coincide con el ID en el cuerpo de la solicitud.");
            }
            request.setId(id); // Asegura que el ID del DTO sea el del path

            Cliente updatedCliente = clienteService.updateCliente(id, request);
            return ResponseEntity.ok(new ClienteResponseDTO(updatedCliente));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}