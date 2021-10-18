package com.prashanth.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prashanth.entity.DatabaseSequence;

@Repository
public interface DatabaseSeqRepository extends MongoRepository<DatabaseSequence, Integer> {
	public DatabaseSequence findBySequenceName(String name);
}
