package com.gl.ceir.config.specificationsbuilder;

import jakarta.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.gl.ceir.config.model.app.SearchCriteria;
import com.gl.ceir.config.model.app.User;

public class Joiner<T> {

	/*public Specification<T> joinToUsersAndUsertype(SearchCriteria searchCriteria){
		return (root, query, cb) -> {
			Join<T, User> user = root.join("user".intern());
			Join<User, Usertype> usertype = user.join("usertype".intern());
			return cb.like(usertype.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
		}; 
	}*/
	
}
