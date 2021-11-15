#language:en
Feature: GET info rooms test Access control service

  #let's start with smoke test
  Scenario: Run request get/info/rooms and get response 200
    When I run get info rooms request
#    Then I get response 200 (checked by REST assurance function)

  Scenario: Check info rooms by one keyId and one room
    When User keyId 8 enters roomId 4
#    Then I get response 200 (checked by REST assurance function)

  Scenario Outline: Check info rooms by table
    When User keyId <keyId> enters roomId <roomId>
#    Then I get response 200 (checked by REST assurance function)
      Examples:
        | keyId | roomId |
        | 1     | 1      |
        | 2     | 2      |
        | 2     | 1      |
        | 10    | 5      |
        | 8     | 4      |

  Scenario: Check every keyId enter room 1
    When All keyId enters first room
    Then All keyId displayed in room request
