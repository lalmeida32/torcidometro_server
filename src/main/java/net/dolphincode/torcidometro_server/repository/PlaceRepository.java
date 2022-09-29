package net.dolphincode.torcidometro_server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.dolphincode.torcidometro_server.entity.Place;

public interface PlaceRepository extends MongoRepository<Place, String> {

}
