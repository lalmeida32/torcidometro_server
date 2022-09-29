package net.dolphincode.torcidometro_server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Document(collection = "places")
@Getter
public class Place {

  @Id
  private String id;

  @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
  private GeoJsonPoint location;

  private String name;

  public Place(GeoJsonPoint location, String name) {
    this.name = name;
    this.location = new GeoJsonPoint(location.getX(), location.getY());
  }
}
