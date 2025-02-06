package com.gl.ceir.config.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.config.model.app.Rules;
import com.gl.ceir.config.service.RulesService;

@RestController
public class RulesController {

	@Autowired
	private RulesService rulesService;

	@Tag(name = "Rule List Management", description = "System Configuration Module API")
	@Operation(
			summary = "Fetch all record",
			description = "Fetches all rule entities and their data from data source")
	@RequestMapping(path = "/Rules/", method = RequestMethod.GET)
	public MappingJacksonValue getAll() {
		List<Rules> allRules = rulesService.getAll();
		MappingJacksonValue mapping = new MappingJacksonValue(allRules);
		return mapping;
	}

	@Tag(name = "Rule List Management", description = "System Configuration Module API")
	@Operation(
			summary = "Fetch single record based on Id",
			description = "Fetches record based on Id from data source")
	@RequestMapping(path = "/Rules/{id}", method = RequestMethod.GET)
	public MappingJacksonValue get(@PathVariable(value = "id") Long id) {
		Rules rules = rulesService.get(id);
		MappingJacksonValue mapping = new MappingJacksonValue(rules);
		return mapping;
	}

	@Tag(name = "Rule List Management", description = "System Configuration Module API")
	@Operation(
			summary = "Save record to the data source",
			description = "Save the record based on the received request")
	@RequestMapping(path = "/Rules/", method = RequestMethod.POST)
	public MappingJacksonValue save(@RequestBody Rules rules) {
		Rules savedRules = rulesService.save(rules);
		MappingJacksonValue mapping = new MappingJacksonValue(savedRules);
		return mapping;
	}

	@Tag(name = "Rule List Management", description = "System Configuration Module API")
	@Operation(
			summary = "Delete record from the data source",
			description = "Delete the record based on the received request")
	@RequestMapping(path = "/Rules/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") Long id) {
		rulesService.delete(id);
	}

	@Tag(name = "Rule List Management", description = "System Configuration Module API")
	@Operation(
			summary = "Update record to the data source",
			description = "Update the record based on the received request")
	@RequestMapping(path = "/Rules/{id}", method = RequestMethod.PUT)
	public MappingJacksonValue update(@PathVariable(value = "id") Long id, @RequestBody Rules rules) {
		Rules updateRules = rulesService.update(rules);
		MappingJacksonValue mapping = new MappingJacksonValue(updateRules);
		return mapping;
	}
}
