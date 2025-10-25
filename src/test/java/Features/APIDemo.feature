Feature: API Related Feature

  @AuthorsAPI
  Scenario Outline: API Get Call
    Given The user requests a get operation for "<uri>" and "<path>"
    Then Deserialize the response and compare the values returned
      | Key             | Value                      |
      | personal_name   | Sachi Rautroy              |
      | alternate_names | Yugashrashta Sachi Routray |


    Examples:
      | uri                     | path               |
      | https://openlibrary.org | /authors/OL1A.json |