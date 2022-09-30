package net.dolphincode.torcidometro_server.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

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
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
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

      place = new Place(new GeoJsonPoint(latitude, longitude), name);
      ReflectionTestUtils.setField(place, "id", "63b0cd000000000000000000");

      request = new CreatePlaceRequestBody(latitude, longitude, name);
    }

    private void falseTrueTest(String name, @Nullable Object valueFalse, @Nullable Object valueTrue) {
      ReflectionTestUtils.setField(request, name, valueFalse);
      var exception = assertThrows(ResponseStatusException.class, () -> placeService.create(request));
      assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);

      ReflectionTestUtils.setField(request, name, valueTrue);
      assertDoesNotThrow(() -> placeService.create(request));
    }

    @BeforeEach()
    public void BeforeEach() {
      Mockito.when(placeRepository.save(ArgumentMatchers.any())).thenReturn(place);
      Mockito.when(placeRepository.exists(ArgumentMatchers.any())).thenReturn(false);
    }

    @Test
    @DisplayName("It should throw a bad request exception when longitude is less than -180")
    public void longitudeLessThanZero() {
      falseTrueTest("longitude", -180.01, -180.0);
    }

    @Test
    @DisplayName("It should throw a bad request exception when longitude is greater than 180")
    public void longitudeGreaterThan180() {
      falseTrueTest("longitude", 180.01, 180.0);
    }

    @Test
    @DisplayName("It should throw a bad request exception when latidude is less than -90")
    public void latitudeLessThanZero() {
      falseTrueTest("latitude", -90.01, -90.0);
    }

    @Test
    @DisplayName("It should throw a bad request exception when latidude is greater than 90")
    public void latitudeGreaterThan90() {
      falseTrueTest("latitude", 90.01, 90.0);
    }

    @Test
    @DisplayName("It should throw a bad request exception when name is less than 5 characters")
    public void nameLessThan5Characters() {
      falseTrueTest("name", "Plac", "Place");
    }

    @Test
    @DisplayName("It should throw a bad request exception when place is less than 5 meters distance of another saved place")
    public void fiveMetersAwayOfAnotherPlace() {

      var places = Arrays.asList(place);

      Mockito.when(placeRepository.customFindNear(
      ArgumentMatchers.eq(request.getLongitude()),
      ArgumentMatchers.eq(request.getLatitude()),
      ArgumentMatchers.anyInt(),
      ArgumentMatchers.anyInt())).thenReturn(places);
      var exception = assertThrows(ResponseStatusException.class, () ->
      placeService.create(request));
      assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("It should return the place when request body is valid")
    public void createPlace() {
      var response = placeService.create(request);
      var expected = new FindPlaceResponse(
          place.getId(),
          place.getLocation().getX(),
          place.getLocation().getY(),
          place.getName());

      assertTrue(new ReflectionEquals(expected).matches(response));
    }
  }

}
