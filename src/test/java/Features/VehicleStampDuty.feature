Feature: Service NSW Vehicle stamp duty

  @StampDuty
  Scenario Outline: Vehicle Stamp duty
    Given The user launches "Check motor vehicle stamp duty"
    Then Click the Check Online button
    Then Complete the "Motor vehicle registration duty calculator" screen with options "<passengerVehicle>" and "<purchasePrice>"
    And Validate the calculator returned content
      | Key              | Value              |
      | passengerVehicle | <passengerVehicle> |
      | purchasePrice    | <purchasePrice>    |

    Examples:
      | passengerVehicle | purchasePrice |
      | Yes              | 20000         |