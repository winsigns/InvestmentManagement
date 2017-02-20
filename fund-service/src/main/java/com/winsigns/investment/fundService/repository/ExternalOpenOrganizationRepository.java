package com.winsigns.investment.fundService.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.fundService.model.ExternalOpenOrganization;

@Transactional
public interface ExternalOpenOrganizationRepository extends JpaRepository<ExternalOpenOrganization, Long> {

}
