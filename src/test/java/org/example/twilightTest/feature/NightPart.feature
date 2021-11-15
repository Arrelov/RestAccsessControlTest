#language:en
Feature: GET check Access control try exit at night the room enter during the day

#      Background: (checked by @BeforeAll function)
#    Given Access control service is available
#    Given it's NOT working time

# Users can't enter rooms if time OUT of working day
# Positive test with table
  Scenario Outline: GET check entrance and exit for users by table
    When I send request mode "<mode>" for keyId <keyId> roomId <roomId>
    Then Access control service response <response>
    Examples:
      | mode  | keyId | roomId  | response  |
      | EXIT  | 1     | 1       | 200       |
      | EXIT  | 2     | 2       | 200       |
      | EXIT  | 3     | 3       | 200       |
      | EXIT  | 4     | 4       | 200       |
      | EXIT  | 5     | 5       | 200       |
      | EXIT  | 6     | 3       | 200       |
      | EXIT  | 7     | 1       | 200       |
      | EXIT  | 8     | 4       | 200       |
      | EXIT  | 9     | 1       | 200       |
      | EXIT  | 10    | 5       | 200       |