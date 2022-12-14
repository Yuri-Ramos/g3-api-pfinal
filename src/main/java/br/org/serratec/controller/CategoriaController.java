package br.org.serratec.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.model.Categoria;
import br.org.serratec.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	public CategoriaController(CategoriaService service) {
		this.service = service;
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria) {
		Categoria categoriaSalva = service.salvarCategoria(categoria);

		return new ResponseEntity<>(categoriaSalva, HttpStatus.CREATED);
	}

	@GetMapping("/listar/{id}")
	public ResponseEntity<Categoria> getCategoria(@PathVariable Long id) {
		Optional<Categoria> categoriaExistente = service.encontrarCategoria(id);

		if (categoriaExistente.isPresent()) {
			return ResponseEntity.ok(categoriaExistente.get());

		} else
			return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
		Optional<Categoria> categoriaExistente = service.encontrarCategoria(id);
		if (categoriaExistente.isPresent()) {
			service.deletarCategoria(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<Categoria>> retornaTodos() {
		List<Categoria> todasCategorias = service.retornaTodasCategorias();
		System.out.println();
		if (!todasCategorias.isEmpty()) {
			return ResponseEntity.ok(todasCategorias);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {

		Optional<Categoria> categoriaExistente = service.encontrarCategoria(id);

		if (!categoriaExistente.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		categoria.setIdCategoria(id);
		service.salvarCategoria(categoria);

		return ResponseEntity.ok(categoriaExistente.get());
	}
}