package com.vyka.service;

import com.vyka.service.dto.CreditCardPaymentDTO;
import java.util.List;

/**
 * Service Interface for managing CreditCardPayment.
 */
public interface CreditCardPaymentService {

    /**
     * Save a creditCardPayment.
     *
     * @param creditCardPaymentDTO the entity to save
     * @return the persisted entity
     */
    CreditCardPaymentDTO save(CreditCardPaymentDTO creditCardPaymentDTO);

    /**
     *  Get all the creditCardPayments.
     *
     *  @return the list of entities
     */
    List<CreditCardPaymentDTO> findAll();

    /**
     *  Get the "id" creditCardPayment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CreditCardPaymentDTO findOne(Long id);

    /**
     *  Delete the "id" creditCardPayment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the creditCardPayment corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<CreditCardPaymentDTO> search(String query);
}
