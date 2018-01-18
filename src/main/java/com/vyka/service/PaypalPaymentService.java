package com.vyka.service;

import com.vyka.service.dto.PaypalPaymentDTO;
import java.util.List;

/**
 * Service Interface for managing PaypalPayment.
 */
public interface PaypalPaymentService {

    /**
     * Save a paypalPayment.
     *
     * @param paypalPaymentDTO the entity to save
     * @return the persisted entity
     */
    PaypalPaymentDTO save(PaypalPaymentDTO paypalPaymentDTO);

    /**
     *  Get all the paypalPayments.
     *
     *  @return the list of entities
     */
    List<PaypalPaymentDTO> findAll();

    /**
     *  Get the "id" paypalPayment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaypalPaymentDTO findOne(Long id);

    /**
     *  Delete the "id" paypalPayment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paypalPayment corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<PaypalPaymentDTO> search(String query);
}
