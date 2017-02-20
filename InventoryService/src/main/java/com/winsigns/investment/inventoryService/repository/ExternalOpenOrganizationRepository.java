package com.winsigns.investment.inventoryService.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.ExternalOpenOrganization;

@Transactional
public interface ExternalOpenOrganizationRepository extends JpaRepository<ExternalOpenOrganization, Long> {

}
