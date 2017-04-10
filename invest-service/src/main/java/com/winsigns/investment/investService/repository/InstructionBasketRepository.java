package com.winsigns.investment.investService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.investService.model.InstructionBasket;

public interface InstructionBasketRepository extends JpaRepository<InstructionBasket, Long> {


}
