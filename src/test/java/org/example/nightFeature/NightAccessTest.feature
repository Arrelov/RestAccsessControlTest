#language:en
Feature: GET check Access control service OUT of working day test (Night test)

#      Background: (checked by @BeforeAll function)
#    Given Access control service is available
#    Given it's NOT working time


# Users can't enter rooms if time OUT of working day
# Positive test with table
Scenario Outline: GET check entrance and exit for all users by table
        When I send request mode "<mode>" for keyId <keyId> roomId <roomId>
        Then Access control service response <response>
        Examples:
            | mode      | keyId | roomId  | response  |
            | ENTRANCE  | 1     | 1       | 403       |
            | ENTRANCE  | 2     | 1       | 403       |
            | ENTRANCE  | 2     | 2       | 403       |
            | ENTRANCE  | 4     | 2       | 403       |
            | ENTRANCE  | 3     | 3       | 403       |
            | ENTRANCE  | 6     | 3       | 403       |
            | ENTRANCE  | 4     | 4       | 403       |
            | ENTRANCE  | 8     | 4       | 403       |
            | ENTRANCE  | 5     | 5       | 403       |
            | ENTRANCE  | 10    | 5       | 403       |

# Negative test with a table. Users still can't enter not allowed rooms even at night
    Scenario Outline: GET check entrance and exit for all users by table
        When I send request mode "<mode>" for keyId <keyId> roomId <roomId>
        Then Access control service response <response>
        Examples:
            | mode      | keyId | roomId  | response  |
            | ENTRANCE  | 1     | 2       | 403       |
            | ENTRANCE  | 1     | 3       | 403       |
            | ENTRANCE  | 2     | 3       | 403       |
            | ENTRANCE  | 2     | 5       | 403       |
            | ENTRANCE  | 3     | 4       | 403       |
            | ENTRANCE  | 3     | 5       | 403       |
            | ENTRANCE  | 4     | 3       | 403       |
            | ENTRANCE  | 4     | 5       | 403       |
            | ENTRANCE  | 5     | 2       | 403       |
            | ENTRANCE  | 5     | 3       | 403       |
