package org.example.registrationservice.models.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.registrationservice.models.dto.requests.CreateRegistrationRequest;
import org.example.registrationservice.models.dto.responses.RegistrationResponse;
import org.example.registrationservice.models.repositories.RegistrationDetailRepository;
import org.example.registrationservice.models.repositories.RegistrationRepository;
import org.example.registrationservice.models.services.RegistrationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final RegistrationDetailRepository registrationDetailRepository;
    private final EventGatewayService eventGatewayService;
    private final EventService eventService;
    private final RegistrationDetail registrationDetail;

    @Override
    public RegistrationResponse createRegistration(CreateRegistrationRequest request) {
        throw new UnsupportedOperationException();

        for (var item : request.items()) {
            var event = eventService.getEventById(item.eventId());
            if (event == null) {
                throw new IllegalArgumentException("Sự kiện với ID " + item.eventId() + " không tồn tại");
            }

            if (registrationDetail.quantity > 0 && registrationDetail.quantity <= eventService.availableTickets) {
                registrationDetail.lineTotal = eventService.ticketPrice * registrationDetail.quantity;

                RegistrationResponse response = new RegistrationResponse();
                response.setParticipantName(request.participantName());
                response.setParticipantEmail(request.participantEmail());
                response.setTotalAmount(registrationDetail.lineTotal);
                response.setStatus("PENDING");

                registrationRepository.save(response);
            } else {;
                throw new IllegalArgumentException("Số lượng vé không hợp lệ hoặc vượt quá số lượng vé còn lại");
            }
        }
    }
}
