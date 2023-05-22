package com.avia.service.impl;

import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.TicketMapper;
import com.avia.model.entity.Airline;
import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketClass;
import com.avia.model.request.TicketRequest;
import com.avia.repository.TicketRepository;
import com.avia.repository.UserRepository;
import com.avia.service.AirlineService;
import com.avia.service.AirportService;
import com.avia.service.EmailService;
import com.avia.service.FlightService;
import com.avia.service.PassengerService;
import com.avia.service.TicketClassService;
import com.avia.service.TicketService;
import com.avia.util.TicketPriceCalculator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private static final Logger log = Logger.getLogger(TicketServiceImpl.class);
    private final PassengerService passengerService;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final FlightService flightService;
    private final TicketClassService ticketClassService;
    private final AirlineService airlineService;
    private final AirportService airportService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final TicketPriceCalculator ticketPriceCalculator;

    @Override
    @Transactional
    public Ticket createTicket(TicketRequest ticketRequest) {
        Passenger passenger = passengerService.findById(ticketRequest.getIdPass());
        Ticket ticket = ticketMapper.toEntity(ticketRequest);
        ticket.getPassenger().setIdPass(passenger.getIdPass());
        ticket.setPassenger(passenger);

        TicketClass ticketClass = ticketClassService.findById(ticketRequest.getIdTicketClass());
        ticket.getTicketClass().setIdTicketClass(ticketClass.getIdTicketClass());
        ticket.setTicketClass(ticketClass);

        Flight flight = flightService.findById(ticketRequest.getIdFlight());
        ticket.getFlight().setIdFlight(flight.getIdFlight());
        ticket.setFlight(flight);

        Airline airline = airlineService.findById(ticketRequest.getIdAirline());
        ticket.getAirline().setIdAirline(airline.getIdAirline());
        ticket.setAirline(airline);

        Airport departureAirport = airportService.findById(flight.getDepartureAirport().getIdAirport());
        Airport arrivalAirport = airportService.findById(flight.getArrivalAirport().getIdAirport());

        double latitudeDeparture = departureAirport.getLatitude();
        double latitudeArrival = arrivalAirport.getLatitude();
        double longitudeDeparture = departureAirport.getLongitude();
        double longitudeArrival = arrivalAirport.getLongitude();

        BigDecimal ticketPrice = ticketPriceCalculator.calculateTicketPrice(latitudeDeparture, longitudeDeparture, latitudeArrival, longitudeArrival);
        ticket.setPrice(ticketPrice);

        ticketRepository.applyDiscount(ticket.getIdTicket(), 0.1F);

        sendEmail(ticket, ticketRequest);

        return ticketRepository.save(ticket);
    }

    private void sendEmail(Ticket ticket, TicketRequest ticketRequest) {
        try {
            String emailTemplate = ResourceBundle.getBundle("email").getString("application.email.buy.ticket");

            String formattedMessage =
                    MessageFormat.format(emailTemplate, ticket.getTicketClass().getNameClass(),
                            airportService.getAddressFromLatLng(ticket.getFlight().getDepartureAirport().getLatitude(),
                                    ticket.getFlight().getDepartureAirport().getLongitude()),
                            airportService.getAddressFromLatLng(ticket.getFlight().getArrivalAirport().getLatitude(),
                                    ticket.getFlight().getArrivalAirport().getLongitude()),
                            ticket.getFlight().getFlightNumber(), ticket.getAirline().getNameAirline(),
                            ticket.getFlight().getDepartureTime(), ticket.getPrice());

            emailService.sendSimpleEmail(userRepository.findByIdPass(ticketRequest.getIdPass()).getAuthenticationInfo().getEmail(),
                    "Congrats from SweetLine Avia! Your created the ticket!", formattedMessage);
        } catch (MailException mailException) {
            log.error("Error while sending out email: " + mailException.getMessage(), mailException);
        } catch (Exception e) {
            log.error("Error occurred: " + e.getMessage(), e);
            throw new EntityNotFoundException("No results found");
        }
    }

    @Override
    @Transactional
    public Ticket updateTicket(Long id, TicketRequest ticketRequest) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ticket with id " + id + " not found"));
        ticketMapper.partialUpdate(ticketRequest, ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public BigDecimal findMostExpensiveTicketPrice(Long id) {
        return ticketRepository.findMostExpensiveTicketPrice(id);
    }
}
