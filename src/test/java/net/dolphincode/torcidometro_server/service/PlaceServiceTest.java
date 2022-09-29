package net.dolphincode.torcidometro_server.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import net.dolphincode.torcidometro_server.ApplicationConfigTest;
import net.dolphincode.torcidometro_server.entity.Place;
import net.dolphincode.torcidometro_server.repository.PlaceRepository;
import net.dolphincode.torcidometro_server.request.places.CreatePlaceRequestBody;
import net.dolphincode.torcidometro_server.response.places.FindPlaceResponse;

@DisplayName("Unit test PlaceService class")
public class PlaceServiceTest extends ApplicationConfigTest {
  @MockBean
  private PlaceRepository placeRepository;

  @Autowired
  private PlaceService placeService;

  @Nested
  @DisplayName("Create tests")
  @TestInstance(Lifecycle.PER_CLASS)
  class Create {

    private Place place;
    private CreatePlaceRequestBody request;

    @BeforeAll()
    public void beforeAll() {
      double latitude = 1.0;
      double longitude = 1.0;
      String name = "Place Name";

      place = new Place(latitude, longitude, name);
      ReflectionTestUtils.setField(place, "id", "63b0cd000000000000000000");

      request = new CreatePlaceRequestBody(latitude, longitude, name);
    }

    @BeforeEach()
    public void BeforeEach() {
      Mockito.when(placeRepository.save(ArgumentMatchers.any())).thenReturn(place);
    }

    @Test
    @DisplayName("It should throw an error when latidude is less than 0")
    public void latitudeLessThanZero() {
      ReflectionTestUtils.setField(request, "latitude", -0.01);
      assertThrows(ResponseStatusException.class, () -> placeService.create(request));

      ReflectionTestUtils.setField(request, "latitude", 0);
      assertDoesNotThrow(() -> placeService.create(request));
    }

    @Test
    @DisplayName("It should throw an error when latidude is greater than 90")
    public void latitudeGreaterThanNinety() {
      ReflectionTestUtils.setField(request, "latitude", 90.01);
      assertThrows(ResponseStatusException.class, () -> placeService.create(request));

      ReflectionTestUtils.setField(request, "latitude", 90.0);
      assertDoesNotThrow(() -> placeService.create(request));
    }

    @Test
    @DisplayName("It should return the place when request body is valid")
    public void createPlace() {
      var response = placeService.create(request);
      var expected = new FindPlaceResponse(place.getId(), place.getLatitude(), place.getLongitude(), place.getName());

      assertTrue(new ReflectionEquals(expected).matches(response));
    }
  }

}
