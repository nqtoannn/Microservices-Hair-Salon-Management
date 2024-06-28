package com.quoctoan.hairservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, String> {
}
