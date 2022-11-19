package com.clark.deduplistings.dao;

import com.clark.deduplistings.domain.TableA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableADao extends JpaRepository<TableA, String> {
}
