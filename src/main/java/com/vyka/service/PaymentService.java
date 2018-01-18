package com.vyka.service;

import com.vyka.service.dto.PaymentDTO;
import java.util.List;

/**
 * Service Interface for managing Payment.
 */
public interface PaymentService {

    /**
     * Save a payment.
     *
     * @param paymentDTO the entity to save
     * @return the persisted entity
     */
    PaymentDTO save(PaymentDTO paymentDTO);

    /**
     *  Get all the payments.
     *
     *  @return the list of entities
     */
    List<PaymentDTO> findAll();

    /**
     *  Get the "id" payment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaymentDTO findOne(Long id);

    /**
     *  Delete the "id" payment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the payment corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<PaymentDTO> search(String query);
}
