package com.winsigns.investment.inventoryService.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.ResourceApplicationForm;

public interface ResourceApplicationFormRepository
    extends JpaRepository<ResourceApplicationForm, Long> {

}
