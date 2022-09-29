package net.dolphincode.torcidometro_server.request.places;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dolphincode.torcidometro_server.entity.Place;

@Getter
@AllArgsConstructor
public class CreatePlaceRequestBody {
  private double longitude;
  private double latitude;
  private String name;

  public Place toEntity() {
    return new Place(new GeoJsonPoint(this.longitude, this.latitude), this.name);
  }
}
