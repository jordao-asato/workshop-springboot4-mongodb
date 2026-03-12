package com.jordao.workshopmongo.resources;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jordao.workshopmongo.domain.Post;
import com.jordao.workshopmongo.resources.util.URL;
import com.jordao.workshopmongo.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private PostService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post obj = service.findById(id);
		return ResponseEntity.ok().body(obj); // resposta da requisição
	}

	@GetMapping(value = "/titlesearch")
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
		try {
			text = URL.decodeParam(text);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} // decodifica o texto
		List<Post> list = service.findByTitle(text);

		return ResponseEntity.ok().body(list); // resposta da requisição
	}

	@GetMapping(value = "/fullsearch")
	public ResponseEntity<List<Post>> fullSearch(@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) throws UnsupportedEncodingException {

		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());

		List<Post> list = service.fullSearch(text, min, max);
		return ResponseEntity.ok().body(list); // resposta da requisição
	}

}
