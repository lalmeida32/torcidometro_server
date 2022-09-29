package net.dolphincode.torcidometro_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.dolphincode.torcidometro_server.request.places.CreatePlaceRequestBody;
import net.dolphincode.torcidometro_server.response.places.FindPlaceResponse;
import net.dolphincode.torcidometro_server.service.PlaceService;

@RestController
public class PlacesController {

  @Autowired
  PlaceService placeService;

  @GetMapping("/places/{id}")
  FindPlaceResponse findById(@PathVariable String id) {
    return placeService.findById(id);
  }

  @PostMapping("/places")
  FindPlaceResponse create(@RequestBody CreatePlaceRequestBody body) {
    return placeService.create(body);
  }
}
