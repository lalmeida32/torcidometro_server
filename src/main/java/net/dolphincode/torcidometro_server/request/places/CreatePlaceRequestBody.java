package net.dolphincode.torcidometro_server.request.places;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dolphincode.torcidometro_server.entity.Place;

@Getter
@AllArgsConstructor
public class CreatePlaceRequestBody {
  private double latitude;
  private double longitude;
  private String name;

  public Place toEntity() {
    return new Place(this.latitude, this.longitude, this.name);
  }
}
