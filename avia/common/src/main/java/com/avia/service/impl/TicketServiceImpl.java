package com.avia.service.impl;

import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.TicketMapper;
import com.avia.model.request.TicketRequest;
import com.avia.model.entity.Airline;
import com.avia.model.entity.Flight;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketClass;
import com.avia.repository.AirlineRepository;
import com.avia.repository.FlightRepository;
import com.avia.repository.PassengerRepository;
import com.avia.repository.TicketClassRepository;
import com.avia.repository.TicketRepository;
import com.avia.repository.UserRepository;
import com.avia.service.AirportService;
import com.avia.service.EmailService;
import com.avia.service.TicketService;
import com.avia.util.CalculateDistance;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final PassengerRepository passengerRepository;

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    private final FlightRepository flightRepository;

    private final TicketClassRepository ticketClassRepository;

    private final AirlineRepository airlineRepository;

    private final AirportService airportService;

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final CalculateDistance calculateDistance;

    private static final Logger log = Logger.getLogger(TicketServiceImpl.class);

    @Override
    @Transactional
    public Ticket createTicket(TicketRequest ticketRequest) {
        Passenger passenger = passengerRepository.findById(ticketRequest.getIdPass()).orElseThrow(() ->
                new EntityNotFoundException("Passenger with id " + ticketRequest.getIdPass() + " not found"));
        Ticket ticket = ticketMapper.toEntity(ticketRequest);
        ticket.getIdPass().setIdPass(passenger.getIdPass());
        ticket.setIdPass(passenger);

        TicketClass ticketClass = ticketClassRepository.findById(ticketRequest.getIdTicketClass()).orElseThrow(() ->
                new EntityNotFoundException("Ticket class with id " + ticketRequest.getIdTicketClass() + " not found"));
        ticket.getIdTicketClass().setIdTicketClass(ticketClass.getIdTicketClass());
        ticket.setIdTicketClass(ticketClass);

        Flight flight = flightRepository.findById(ticketRequest.getIdFlight()).orElseThrow(() ->
                new EntityNotFoundException("Flight with id " + ticketRequest.getIdFlight() + " not found"));
        ticket.getIdFlight().setIdFlight(flight.getIdFlight());
        ticket.setIdFlight(flight);

        Airline airline = airlineRepository.findById(ticketRequest.getIdAirline()).orElseThrow(() ->
                new EntityNotFoundException("Airline with id " + ticketRequest.getIdAirline() + " not found"));
        ticket.getIdAirline().setIdAirline(airline.getIdAirline());
        ticket.setIdAirline(airline);

        ticket.setPrice(BigDecimal.valueOf(calculateDistance.calculate(flight.getIdDepartureAirport().getLatitude(),
                flight.getIdDepartureAirport().getLongitude(), flight.getIdArrivalAirport().getLatitude(),
                flight.getIdArrivalAirport().getLongitude()) * 0.3).setScale(2, RoundingMode.HALF_UP));

        try {
            String emailTemplate = ResourceBundle.getBundle("email").getString("application.email.buy.ticket");

            String formattedMessage = MessageFormat.format(emailTemplate, ticketClass.getNameClass(),
                    airportService.getAddressFromLatLng(flight.getIdDepartureAirport().getLatitude(), flight.getIdDepartureAirport().getLongitude()),
                    airportService.getAddressFromLatLng(flight.getIdArrivalAirport().getLatitude(), flight.getIdArrivalAirport().getLongitude()),
                    flight.getFlightNumber(), airline.getNameAirline(), flight.getDepartureTime(), ticket.getPrice());


            emailService.sendSimpleEmail(userRepository.findByIdPass(ticketRequest.getIdPass()).getAuthenticationInfo().getEmail(),
                    "Congrats from SweetLine Avia! Your created the ticket!", formattedMessage);
        } catch (MailException mailException) {
            log.error("Error while sending out email.." + mailException);
        } catch (Exception e) {
            throw new EntityNotFoundException("No results found");
        }

        return ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public Ticket updateTicket(Long id, TicketRequest ticketRequest) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ticket with id " + id + " not found"));
        ticketMapper.partialUpdate(ticketRequest, ticket);
        return ticketRepository.save(ticket);
    }
}
