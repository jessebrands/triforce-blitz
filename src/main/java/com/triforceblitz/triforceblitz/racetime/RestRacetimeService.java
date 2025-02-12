package com.triforceblitz.triforceblitz.racetime;

import com.triforceblitz.triforceblitz.racetime.errors.RaceNotFoundException;
import com.triforceblitz.triforceblitz.racetime.race.Race;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Implements the Racetime service using the REST API.
 */
@Service
public class RestRacetimeService implements RacetimeService {
    private final RacetimeClient client;

    public RestRacetimeService(RacetimeClient client) {
        this.client = client;
    }

    @Override
    public Race getRace(String category, String name) throws RaceNotFoundException {
        try {
            return client.getRace(category, name);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RaceNotFoundException("race not found");
        }
    }
}
