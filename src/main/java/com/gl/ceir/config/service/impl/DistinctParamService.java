package com.gl.ceir.config.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.*;
import java.util.function.Consumer;

@Service
public  class DistinctParamService {
        private final Logger logger = LogManager.getLogger(this.getClass());
        @PersistenceContext
        private EntityManager em;

        public Map<String, List<?>> distinct(List<String> param, Class<?> entity) {
            Map<String, List<?>> map = new LinkedHashMap<>();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
            Root<?> c = query.from(entity);
            Consumer<String> consumer = (s) -> {
                logger.info("Parameter : [" + s + "]");
                List<Object> resultList = new ArrayList<>();
                try {
                    query.multiselect(c.get(s)).distinct(true);
                    List<Tuple> tuples = em.createQuery(query).getResultList();
                    for (Tuple tuple : tuples) {
                        resultList.add(tuple.get(0, Object.class));
                    }
                    resultList.removeIf(Objects::isNull);
                    map.put(s, resultList);
                } catch (Exception e) {
                    logger.error("no parameter {} found : {}", s, e);
                    map.put(s, resultList);
                }
            };
            param.forEach(consumer);
            return map;
        }

    }
