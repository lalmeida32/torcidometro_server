package net.dolphincode.torcidometro_server.request.places;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePlaceRequestBody {
  private double latitude;
  private double longitude;
  private String name;
}
