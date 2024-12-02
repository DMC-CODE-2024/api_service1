package com.gl.ceir.config.controller;

import java.util.List;

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

	@RequestMapping(path = "/Rules/", method = RequestMethod.GET)
	public MappingJacksonValue getAll() {
		List<Rules> allRules = rulesService.getAll();
		MappingJacksonValue mapping = new MappingJacksonValue(allRules);
		return mapping;
	}

	@RequestMapping(path = "/Rules/{id}", method = RequestMethod.GET)
	public MappingJacksonValue get(@PathVariable(value = "id") Long id) {
		Rules rules = rulesService.get(id);
		MappingJacksonValue mapping = new MappingJacksonValue(rules);
		return mapping;
	}

	@RequestMapping(path = "/Rules/", method = RequestMethod.POST)
	public MappingJacksonValue save(@RequestBody Rules rules) {
		Rules savedRules = rulesService.save(rules);
		MappingJacksonValue mapping = new MappingJacksonValue(savedRules);
		return mapping;
	}

	@RequestMapping(path = "/Rules/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") Long id) {
		rulesService.delete(id);
	}

	@RequestMapping(path = "/Rules/{id}", method = RequestMethod.PUT)
	public MappingJacksonValue update(@PathVariable(value = "id") Long id, @RequestBody Rules rules) {
		Rules updateRules = rulesService.update(rules);
		MappingJacksonValue mapping = new MappingJacksonValue(updateRules);
		return mapping;
	}
}
