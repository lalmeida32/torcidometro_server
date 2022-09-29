package net.dolphincode.torcidometro_server.response.places;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dolphincode.torcidometro_server.entity.Place;

@Getter
@AllArgsConstructor
public class FindPlaceResponse {
  private String id;
  private double latitude;
  private double longitude;
  private String name;

  public static FindPlaceResponse fromEntity(Place entity) {
    return new FindPlaceResponse(
        entity.getId(),
        entity.getLocation().getX(),
        entity.getLocation().getY(),
        entity.getName());
  }

}
