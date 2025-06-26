package com.facu.backbus.repository;

import com.facu.backbus.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Encontra eventos conflitantes para um determinado motorista dentro de um intervalo de datas, excluindo um evento específico.
     * Útil para verificar conflitos ao ATUALIZAR um evento existente.
     *
     * @param driverId O ID do motorista a ser verificado.
     * @param startDate A data de início do novo evento.
     * @param endDate A data de fim do novo evento.
     * @param excludeEventId O ID do evento que deve ser ignorado na verificação (o próprio evento sendo atualizado).
     * @return Uma lista de eventos conflitantes.
     */
    @Query("SELECT e FROM Event e WHERE e.driver.id = :driverId " +
           "AND e.id <> :excludeEventId " +
           "AND ((e.eventDepartureDate <= :endDate AND e.eventReturnDate >= :startDate) OR " +
           "(e.eventDepartureDate BETWEEN :startDate AND :endDate) OR " +
           "(e.eventReturnDate BETWEEN :startDate AND :endDate))")
    List<Event> findConflictingEvents(
        @Param("driverId") Long driverId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("excludeEventId") Long excludeEventId
    );

    /**
     * Encontra eventos conflitantes para um determinado motorista dentro de um intervalo de datas.
     * Útil para verificar conflitos ao CRIAR um novo evento.
     *
     * @param driverId O ID do motorista a ser verificado.
     * @param startDate A data de início do novo evento.
     * @param endDate A data de fim do novo evento.
     * @return Uma lista de eventos conflitantes.
     */
    @Query("SELECT e FROM Event e WHERE e.driver.id = :driverId " +
           "AND ((e.eventDepartureDate <= :endDate AND e.eventReturnDate >= :startDate) OR " +
           "(e.eventDepartureDate BETWEEN :startDate AND :endDate) OR " +
           "(e.eventReturnDate BETWEEN :startDate AND :endDate))")
    List<Event> findConflictingEvents(
        @Param("driverId") Long driverId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    /**
     * Encontra eventos conflitantes para um determinado ônibus dentro de um intervalo de datas, excluindo um evento específico.
     * Útil para verificar conflitos ao ATUALIZAR um evento existente.
     *
     * @param busId O ID do ônibus a ser verificado.
     * @param startDate A data de início do novo evento.
     * @param endDate A data de fim do novo evento.
     * @param excludeEventId O ID do evento que deve ser ignorado na verificação (o próprio evento sendo atualizado).
     * @return Uma lista de eventos conflitantes.
     */
    @Query("SELECT e FROM Event e WHERE e.bus.id = :busId " +
           "AND e.id <> :excludeEventId " +
           "AND ((e.eventDepartureDate <= :endDate AND e.eventReturnDate >= :startDate) OR " +
           "(e.eventDepartureDate BETWEEN :startDate AND :endDate) OR " +
           "(e.eventReturnDate BETWEEN :startDate AND :endDate))")
    List<Event> findConflictingEventsByBus(
        @Param("busId") Long busId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("excludeEventId") Long excludeEventId
    );

    /**
     * Encontra eventos conflitantes para um determinado ônibus dentro de um intervalo de datas.
     * Útil para verificar conflitos ao CRIAR um novo evento.
     *
     * @param busId O ID do ônibus a ser verificado.
     * @param startDate A data de início do novo evento.
     * @param endDate A data de fim do novo evento.
     * @return Uma lista de eventos conflitantes.
     */
    @Query("SELECT e FROM Event e WHERE e.bus.id = :busId " +
           "AND ((e.eventDepartureDate <= :endDate AND e.eventReturnDate >= :startDate) OR " +
           "(e.eventDepartureDate BETWEEN :startDate AND :endDate) OR " +
           "(e.eventReturnDate BETWEEN :startDate AND :endDate))")
    List<Event> findConflictingEventsByBus(
        @Param("busId") Long busId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
