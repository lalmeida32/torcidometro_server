package net.dolphincode.torcidometro_server.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.dolphincode.torcidometro_server.entity.Place;

public interface PlaceRepository extends MongoRepository<Place, String> {

  @Aggregation(pipeline = {
      "{ $geoNear: { near: { type: 'Point', coordinates: [ ?0, ?1 ] }, distanceField: \"distance\", maxDistance: ?2, spherical: true } }",
      "{ $limit: ?3 }"
  })
  List<Place> customFindNear(double longitude, double latitude, int distance, int limit);
}
