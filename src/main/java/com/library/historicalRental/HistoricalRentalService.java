package com.library.historicalRental;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class HistoricalRentalService {

    private final HistoricalRentalRepository historicalRentalRepository;

    public HistoricalRentalService(HistoricalRentalRepository historicalRentalRepository) {
        this.historicalRentalRepository = historicalRentalRepository;
    }

    private HistoricalRentalDTO convertHistoricalRentalToDTO(HistoricalRental historicalRental) {
        return new HistoricalRentalDTO(
                historicalRental.getHistoricalRentalId(),
                historicalRental.getDateCreated(),
                historicalRental.getDateEnded(),
                historicalRental.getCustomerId(),
                historicalRental.getFirstName(),
                historicalRental.getLastName(),
                historicalRental.getBookId(),
                historicalRental.getTitle(),
                historicalRental.getAuthor(),
                historicalRental.getIsbn()
        );
    }

    private HistoricalRental convertDTOToHistoricalRental(HistoricalRentalDTO historicalRentalDTO) {
        return new HistoricalRental(
                historicalRentalDTO.getHistoricalRentalId(),
                historicalRentalDTO.getDateCreated(),
                historicalRentalDTO.getDateEnded(),
                historicalRentalDTO.getCustomerId(),
                historicalRentalDTO.getFirstName(),
                historicalRentalDTO.getLastName(),
                historicalRentalDTO.getBookId(),
                historicalRentalDTO.getTitle(),
                historicalRentalDTO.getAuthor(),
                historicalRentalDTO.getIsbn()
        );
    }

    public HistoricalRentalDTO createHistoricalRental(HistoricalRentalDTO historicalRentalDTO) {
        historicalRentalRepository.save(convertDTOToHistoricalRental(historicalRentalDTO));
        return historicalRentalDTO;
    }

    public List<HistoricalRentalDTO> findAllHistoricalRentals() {
        return historicalRentalRepository.findAll()
                .stream()
                .map(this::convertHistoricalRentalToDTO)
                .collect(toList());
    }

}
