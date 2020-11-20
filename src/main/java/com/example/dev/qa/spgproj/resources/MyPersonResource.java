package com.example.dev.qa.spgproj.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev.qa.spgproj.models.MyPerson;
import com.example.dev.qa.spgproj.repositories.MyPersonRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
public class MyPersonResource {

	@Autowired
	private MyPersonRepository myPersonRepository;

	@ApiOperation(value = "Retorna uma lista de pessoas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a lista de pessoa"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
			}
	)
	@RequestMapping(value = "/my_persons", method = RequestMethod.GET, produces = "application/json")
	public List<MyPerson> get() {
		return myPersonRepository.findAll();
	}

	@ApiOperation(value = "Consulta pessoa por ID")
	@RequestMapping(value = "/my_person/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<MyPerson> getById(@PathVariable(value = "id") Integer id) {
		//
		Optional<MyPerson> optlPerson = myPersonRepository.findById(id);
		//
		if (optlPerson.isPresent())
			return new ResponseEntity<MyPerson>(optlPerson.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation("Cadastra pessoas, uma por vez...")
	@RequestMapping(value = "/my_person", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public MyPerson post(@Validated @RequestBody MyPerson myPerson) {
		return myPersonRepository.save(myPerson);
	}

	@ApiOperation("Atualiza os dados de uma pessoa")
	@RequestMapping(value = "/my_person/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<MyPerson> put(@PathVariable(value = "id") Integer id, @Validated @RequestBody MyPerson MyPersonNew) {
		//
		Optional<MyPerson> myPersonOld = myPersonRepository.findById(id);
		//
		if (myPersonOld.isPresent()) {
			MyPerson myPerson = myPersonOld.get();
			myPerson.setName(MyPersonNew.getName());
			myPersonRepository.save(myPerson);
			return new ResponseEntity<MyPerson>(myPerson, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation("Exclui pessoa por ID")
	@RequestMapping(value = "/my_person/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> delete(@PathVariable(value = "id") Integer id) {
		//
		Optional<MyPerson> optlPerson = myPersonRepository.findById(id);
		//
		if (optlPerson.isPresent()) {
			myPersonRepository.delete(optlPerson.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}