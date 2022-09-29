package net.dolphincode.torcidometro_server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import net.dolphincode.torcidometro_server.entity.Place;
import net.dolphincode.torcidometro_server.repository.PlaceRepository;
import net.dolphincode.torcidometro_server.request.places.CreatePlaceRequestBody;
import net.dolphincode.torcidometro_server.response.places.FindPlaceResponse;

@Service
public class PlaceService {

  @Autowired
  private PlaceRepository placeRepository;

  public FindPlaceResponse findById(String id) {
    Optional<Place> found = placeRepository.findById(id);

    if (!found.isPresent()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Place was not found");
    }

    return new FindPlaceResponse(
        found.get().getId(),
        found.get().getLatitude(),
        found.get().getLongitude(),
        found.get().getName());
  }

  public FindPlaceResponse create(CreatePlaceRequestBody body) {
    Place saved = placeRepository.save(new Place(body.getLatitude(), body.getLongitude(), body.getName()));
    return new FindPlaceResponse(saved.getId(), saved.getLatitude(), saved.getLongitude(), saved.getName());
  }
}
