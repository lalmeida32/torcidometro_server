package net.dolphincode.torcidometro_server.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import net.dolphincode.torcidometro_server.entity.Place;

public interface PlaceRepository extends MongoRepository<Place, String> {

  @Query("{ location: { $nearSphere: { $geometry: { type: \"Point\", coordinates: [?0, ?1] }, $maxDistance: ?2 } } }")
  List<Place> customFindNear(double latitude, double longitude, int distance);
}
