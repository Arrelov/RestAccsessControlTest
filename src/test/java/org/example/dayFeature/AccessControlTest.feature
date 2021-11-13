#language:en
Feature: GET check test Access control service working day test

#  Background: (checked by @BeforeAll function)
#    Given Access control service is available
#    Given it's working time

  #let's start with smoke test
  Scenario: GET check smoke test entrance
    When I send request mode "ENTRANCE" for keyId 2 roomId 2
    Then Access control service response 200

  Scenario: GET check smoke test exit
    When I send request mode "EXIT" for keyId 2 roomId 2
    Then Access control service response 200

  #Now it's time for positive tests in a larger volume
  Scenario Outline: GET check entrance and exit for all users by table
    When I send request mode "<mode>" for keyId <keyId> roomId <roomId>
    Then Access control service response <response>
    Examples:
      | mode      | keyId | roomId  | response  |
      | ENTRANCE  | 1     | 1       | 200       |
      | EXIT      | 1     | 1       | 200       |
      | ENTRANCE  | 2     | 2       | 200       |
      | EXIT      | 2     | 2       | 200       |
      | ENTRANCE  | 3     | 3       | 200       |
      | EXIT      | 3     | 3       | 200       |
      | ENTRANCE  | 4     | 4       | 200       |
      | EXIT      | 4     | 4       | 200       |
      | ENTRANCE  | 5     | 5       | 200       |
      | EXIT      | 5     | 5       | 200       |

  #Negative test with a table
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

  #Exhaustive testing is rarely possible. It's just our case! Positive test:
  Scenario: GET check in and out for all users POSITIVE test by logic
    When I run positive test by logic keyId % roomId == 0 -> Access granted
    # Then I get service response 200 (checked by REST assurance function)


  #And negative test:
  Scenario: GET check in for all users NEGATIVE test by logic
    When I run negative test by logic keyId % roomId != 0 -> Access DENIED
    # Then I get service response 403 (checked by REST assurance function)



# пользователь зашел в одну комнату  и пытается выйти из другой комнаты
  Scenario Outline: GET check entrance and exit different rooms by table
    When I send request mode "<mode>" for keyId <keyId> roomId <roomId>
    Then Access control service response <response>
    Examples:
      | mode      | keyId | roomId  | response  |
      | ENTRANCE  | 3     | 3       | 200       |
      | EXIT      | 3     | 1       | 500       |
      | EXIT      | 3     | 2       | 500       |
      | EXIT      | 3     | 4       | 500       |
      | EXIT      | 3     | 5       | 500       |
      | EXIT      | 3     | 3       | 200       |

# пользователь зашел в одну комнату и пытается зайти в другую комнату
  Scenario Outline: GET check entrance and exit different rooms by table
    When I send request mode "<mode>" for keyId <keyId> roomId <roomId>
    Then Access control service response <response>
    Examples:
      | mode      | keyId | roomId  | response  |
      | ENTRANCE  | 3     | 3       | 200       |
      | ENTRANCE  | 3     | 1       | 500       |
      | ENTRANCE  | 3     | 2       | 500       |
      | ENTRANCE  | 3     | 4       | 500       |
      | ENTRANCE  | 3     | 5       | 500       |
      | EXIT      | 3     | 3       | 200       |

