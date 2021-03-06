package br.com.thiago.retry.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.thiago.retry.dto.CategoriaDTO;
import br.com.thiago.retry.model.Categoria;
import br.com.thiago.retry.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> list() {
		List<Categoria> list = this.categoriaService.list();
		List<CategoriaDTO> listDTO = list.stream().map(categoria -> new CategoriaDTO(categoria))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findCategoria(@PathVariable Integer id) {
		Categoria categoria = this.categoriaService.find(id);
		return ResponseEntity.ok().body(categoria);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> insertCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria newCategoria = this.categoriaService.fromDTO(categoriaDTO);
		newCategoria = this.categoriaService.insert(newCategoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCategoria.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO,
			@PathVariable Integer id) {
		categoriaDTO.setId(id);
		Categoria categoria = this.categoriaService.fromDTO(categoriaDTO);
		categoria = this.categoriaService.update(categoria);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
		this.categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/*
	 * parâmetros opcionais para paginação O Objeto do tipo Page é J8 compliance, ou
	 * seja, ele aceita findByPage.map(obj -> new CategoriaDTO(obj)); sem precisar
	 * usar stream e outros
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> FindPageable(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "4") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<Categoria> findByPage = this.categoriaService.findByPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> pageList = findByPage.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(pageList);

	}

}
