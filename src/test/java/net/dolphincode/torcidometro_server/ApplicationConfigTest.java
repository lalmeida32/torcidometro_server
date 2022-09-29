package net.dolphincode.torcidometro_server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import net.dolphincode.torcidometro_server.entity.Place;
import net.dolphincode.torcidometro_server.repository.PlaceRepository;
import net.dolphincode.torcidometro_server.request.places.CreatePlaceRequestBody;
import net.dolphincode.torcidometro_server.response.places.FindPlaceResponse;
import net.dolphincode.torcidometro_server.service.PlaceService;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class ApplicationConfigTest {
  @MockBean
  private PlaceRepository placeRepository;

  @Autowired
  private PlaceService placeService;

  @Test
  public void shouldCreatePlace() {
    var place = new Place(1.0, 2.0, "Place Name");
    Mockito.when(placeRepository.save(ArgumentMatchers.any())).thenReturn(place);

    var request = new CreatePlaceRequestBody(1.0, 2.0, "Place Name");
    var response = placeService.create(request);

    var expected = new FindPlaceResponse(place.getId(), place.getLatitude(), place.getLongitude(), place.getName());
    assertEquals(expected.getId(), response.getId());
    assertEquals(expected.getLatitude(), response.getLatitude());
    assertEquals(expected.getLongitude(), response.getLongitude());
    assertEquals(expected.getName(), response.getName());

  }
}
