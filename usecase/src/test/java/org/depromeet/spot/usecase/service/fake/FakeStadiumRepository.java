package org.depromeet.spot.usecase.service.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.out.stadium.StadiumRepository;

public class FakeStadiumRepository implements StadiumRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Stadium> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Stadium findById(Long id) {
        return getById(id).orElseThrow(StadiumNotFoundException::new);
    }

    private Optional<Stadium> getById(Long id) {
        return data.stream().filter(stadium -> stadium.getId().equals(id)).findAny();
    }

    @Override
    public List<Stadium> findAll() {
        return data;
    }

    @Override
    public Stadium save(Stadium stadium) {
        if (stadium.getId() == null || stadium.getId() == 0) {
            Stadium newStadium =
                    Stadium.builder()
                            .id(autoGeneratedId.incrementAndGet())
                            .name(stadium.getName())
                            .mainImage(stadium.getMainImage())
                            .seatingChartImage(stadium.getSeatingChartImage())
                            .labeledSeatingChartImage(stadium.getLabeledSeatingChartImage())
                            .isActive(stadium.isActive())
                            .build();
            data.add(newStadium);
            return newStadium;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), stadium.getId()));
            data.add(stadium);
            return stadium;
        }
    }
}
