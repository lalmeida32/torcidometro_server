package net.dolphincode.torcidometro_server.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Document(collection = "places")
@Getter
public class Place {

  @Id
  private String id;
  private double latitude;
  private double longitude;
  private String name;
  private String createdAt;

  public Place(double latitude, double longitude, String name) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
  }
}
