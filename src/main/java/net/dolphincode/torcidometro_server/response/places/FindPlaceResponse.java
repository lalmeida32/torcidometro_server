package net.dolphincode.torcidometro_server.response.places;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindPlaceResponse {
  private String id;
  private double latitude;
  private double longitude;
  private String name;
}
