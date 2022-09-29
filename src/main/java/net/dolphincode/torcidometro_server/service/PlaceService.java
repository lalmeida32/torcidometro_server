package net.dolphincode.torcidometro_server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    return FindPlaceResponse.fromEntity(found.get());
  }

  @Transactional
  public FindPlaceResponse create(CreatePlaceRequestBody body) {
    if (body.getLongitude() < -180)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Longitude should be greater or equal than -180");
    if (body.getLongitude() > 180)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Longitude should be less or equal than 180");
    if (body.getLatitude() < -90)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitude should be greater or equal than -90");
    if (body.getLatitude() > 90)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitude should be less or equal than 90");
    if (body.getName().length() < 5)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name should have at least 5 characters");

    System.out.println(placeRepository.customFindNear(body.getLongitude(), body.getLatitude(), 111000));

    Place saved = placeRepository
        .save(new Place(new GeoJsonPoint(body.getLongitude(), body.getLatitude()), body.getName()));
    return FindPlaceResponse.fromEntity(saved);
  }
}
