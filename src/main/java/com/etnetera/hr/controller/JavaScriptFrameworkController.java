package com.etnetera.hr.controller;


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
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

/**
 * Simple REST controller for accessing application logic.
 *  
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController extends EtnRestController {
	
	private final JavaScriptFrameworkRepository repository;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/frameworks")
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}
/* vytvareni noveho frameworku*/
	
	 @PostMapping("/add")
	 public ResponseEntity<JavaScriptFramework> createFramework(@Valid @RequestBody JavaScriptFramework framework) {
		 return new ResponseEntity<JavaScriptFramework>(framework, HttpStatus.CREATED);
	 }
/* uprava frameworku*/
	 
	 @PutMapping("/frameworks/{id}")
	  public ResponseEntity<JavaScriptFramework> editFramework(
	      @PathVariable(value = "id") Long id, @Valid @RequestBody JavaScriptFramework framework)
	      throws RuntimeException {
	    JavaScriptFramework newFramework =
	        repository
	            .findById(id)
	            .orElseThrow(() -> new RuntimeException("Framework not found on :: " + id));
	    newFramework.setName(framework.getName());
	    newFramework.setVersion(framework.getVersion());
	    newFramework.setDeprecationDate(framework.getDeprecationDate());
	    newFramework.setHypeLevel(framework.getHypeLevel());
	    final JavaScriptFramework updatedFramework = repository.save(newFramework);
	    return new ResponseEntity<JavaScriptFramework>(updatedFramework, HttpStatus.OK);
	  }
	  
	 
/* mazani */
	  @DeleteMapping("/frameworks/{id}")
	  public ResponseEntity<JavaScriptFramework> deleteFramework(@PathVariable Long id) {
	    repository.deleteById(id);
	    return new ResponseEntity<JavaScriptFramework>(HttpStatus.ACCEPTED);
	  }
	  
/* vyhledavani */
	 
	  @GetMapping("/frameworks/{id}")
	  public JavaScriptFramework findFrameworkById(@PathVariable(value = "id") Long id) {
	
	    return repository.findById(id)
	    		 .orElseThrow(() -> new RuntimeException("Framework not found on :: " + id));
	    }
}
