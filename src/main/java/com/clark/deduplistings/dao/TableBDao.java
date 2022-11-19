package com.clark.deduplistings.dao;

import com.clark.deduplistings.domain.TableB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableBDao extends JpaRepository<TableB, String> {
}
