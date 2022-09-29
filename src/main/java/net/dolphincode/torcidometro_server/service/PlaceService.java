package net.dolphincode.torcidometro_server.service;

import net.dolphincode.torcidometro_server.request.places.CreatePlaceRequestBody;
import net.dolphincode.torcidometro_server.response.places.FindPlaceResponse;

public interface PlaceService {
  FindPlaceResponse findById(String id);

  FindPlaceResponse create(CreatePlaceRequestBody body);
}
